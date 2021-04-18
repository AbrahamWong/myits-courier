package id.ac.its.myits.courier.ui.qr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

import id.ac.its.myits.courier.data.DataManager;
import id.ac.its.myits.courier.data.db.model.PaketInternal;
import id.ac.its.myits.courier.ui.base.BasePresenter;
import id.ac.its.myits.courier.ui.proof.ProofActivity;
import id.ac.its.myits.courier.utils.AppLogger;
import id.ac.its.myits.courier.utils.Statics;
import id.ac.its.myits.courier.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Response;
import timber.log.Timber;


public class QrPresenter<V extends QrMvpView> extends BasePresenter<V>
        implements QrMvpPresenter<V> {

    QrMvpView view;
    private CodeScanner scanner;
    private CodeScannerView scannerView;

    // const in Java
    private static final int CAMERA_REQUEST_CODE = 101;

    @Inject
    public QrPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void setMvpView(QrMvpView v) {
        view = v;
    }

    @Override
    public void requestPermissions() {
        int permission = ContextCompat.checkSelfPermission(view.getQrActivity(), Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            AppLogger.d("Requesting permission for camera");
            ActivityCompat.requestPermissions(view.getQrActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE :
                AppLogger.d("Throwing all the data here %d", grantResults[0]);
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(view.getQrActivity(), "You need the camera to use this app.", Toast.LENGTH_SHORT).show();
                } else {
                    // Success
                }
                break;
        }
    }

    boolean scan = true;
    @Override
    public void scanQrCode(CodeScannerView scannerView) {
        scanner = new CodeScanner(view.getQrActivity(), scannerView);

        scanner.setCamera(CodeScanner.CAMERA_BACK);
        scanner.setFormats(CodeScanner.ALL_FORMATS);

        scanner.setAutoFocusMode(AutoFocusMode.SAFE);
        scanner.setScanMode(ScanMode.CONTINUOUS);
        scanner.setAutoFocusEnabled(true);
        scanner.setFlashEnabled(false);

        scanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                if (scan) view.getQrActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Kode utama untuk QR scanner ada di sini.
                        getInternalJobAndChangeStatus(result.getText());
                        scan = false;

                        getMvpView().getQrActivity().finish();
                    }
                });
            }
        });

        scanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(@NonNull final Exception error) {
                view.getQrActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Timber.e("Camera init error: %s", error.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public void startQrPreview() {
        scanner.startPreview();
    }

    @Override
    public void releaseResource() {
        scanner.releaseResources();
    }

    void getInternalJobAndChangeStatus(String kodeInternal) {
        getDataManager().getInternalJobDetail(kodeInternal)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(jsonArray -> {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    PaketInternal paket = new PaketInternal();
                    paket.setIdPaket(jsonObject.getInt("id_paket"));
                    paket.setKodeInternal(kodeInternal);
                    paket.setStatus(jsonObject.getString("STATUS"));
                    paket.setBedaZona(jsonObject.getInt("is_beda_zona") == 1);

                    Statics.packageCode = paket.getKodeInternal();

                    getStatusLists(paket);
                }, throwable -> {
                    if (getMvpView().isNetworkConnected()) {
                        AppLogger.e(throwable, "Error yang diberikan adalah:");
                    }
                });
    }

    void getStatusLists(PaketInternal paketInternal) {
        getDataManager().getInternalStatuses()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(jsonArray -> {
                    ArrayList<String> statuses = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        String status = jsonArray.getJSONObject(i).getString("status");
                        statuses.add(status);
                    }

                    changeStatus(paketInternal, statuses);

                }, throwable -> {
                    if (getMvpView().isNetworkConnected()) {
                        AppLogger.e(throwable, "Error yang diberikan adalah:");
                    }
                });
    }

    void changeStatus(PaketInternal paketInternal, ArrayList<String> statuses) {

        // Ganti status berdasarkan status sebelumnya dan apakah asal dan
        // tujuan paket berada di zona yang sama
        String statusPosted = null;
        if (!statuses.isEmpty()) {
            // Jika status paket adalah "Paket Internal Siap Dijemput"
            if (paketInternal.getStatus().equals(statuses.get(0)))
                statusPosted = "2";

                // Jika status paket adalah "Paket Internal Sedang Menunggu Dijemput Caraka"
            else if (paketInternal.getStatus().equals(statuses.get(1)))
                // Kode yang dikirimkan adalah:
                // Jika zona unit asal dan unit tujuan berbeda  = 3
                // Jika zona unit asal dan unit tujuan sama     = 4
                statusPosted = paketInternal.isBedaZona() ? "3" : "4";

                // Jika status paket antara "Paket Internal Sedang Dikirim ke TU Tujuan"
                // atau "Paket Internal Sedang Dikirim ke TU Pusat", status berikutnya adalah
                // "Paket Internal Sudah Diterima TU Tujuan"
            else if (paketInternal.getStatus().equals(statuses.get(2)) || paketInternal.getStatus().equals(statuses.get(3)))
                statusPosted = "5";
            else statusPosted = null;
        }

        if (statusPosted != null) {
            String finalStatusPosted = statusPosted;
            getDataManager().postInternalJobEdit(paketInternal.getKodeInternal(), finalStatusPosted, new OkHttpResponseListener() {
                @Override
                public void onResponse(Response response) {
                    if (response.code() == 200) {
                        AppLogger.d("%d says: Success, Returning to the main activity.", response.code());
                        getMvpView().showMessage("Status paket berhasil diubah menjadi: " + finalStatusPosted);

                        if (finalStatusPosted.equals("5"))
                            getMvpView().getQrActivity().startActivity(
                                    new Intent(getMvpView().getQrActivity(),
                                            ProofActivity.class)
                            );
                    }
                }

                @Override
                public void onError(ANError anError) {
                    AppLogger.e("The error is " + anError.toString());
                }
            });
        } else {
            getMvpView().showMessage("Paket tidak ditemukan, pastikan kode QR yang anda masukkan adalah kode yang benar.");
        }
    }
}
