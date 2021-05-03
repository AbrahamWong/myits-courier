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
                    paket.setStatus(jsonObject.getString("status"));
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

        // Ganti status berdasarkan status sebelumnya dan apakah asal dan tujuan paket berada di zona yang sama
        // Daftar status pengantaran internal yang ada adalah:
        // "id": 1, "status": "Paket Internal Siap Dijemput"                    --> statuses.get(0)
        // "id": 2, "status": "Paket Internal Sedang Menunggu Dijemput Caraka"  --> statuses.get(1)
        // "id": 3, "status": "Paket Internal Sedang Dikirim ke TU Pusat"       --> statuses.get(2)
        // "id": 4, "status": "Paket Internal Sudah Diterima TU Pusat"          --> statuses.get(3)
        // "id": 5, "status": "Paket Internal Sedang Dikirim ke TU Tujuan"      --> statuses.get(4)
        // "id": 6, "status": "Paket Internal Sudah Diterima TU Tujuan"         --> statuses.get(5)
        // Untuk pengantaran internal sama zona, urutan status paket yang benar adalah: 1, 2, 5, 6
        // Untuk pengantaran internal beda zona, urutan status paket yang benar adalah: 1, 2, 3, 4, 5, 6
        // Jika mengubah status berdasarkan kode QR, secara otomatis status naik 1 tingkat.

        String statusPosted = null;
        String currentStatus = paketInternal.getStatus();

        for (int i = 0; i < 5; i++) {
            // Jika status paket adalah "Paket Internal Sedang Menunggu Dijemput Caraka" dan zona
            // asal paket sama dengan zona tujuan paket, maka status paket lompat ke id nomor 5.
            if (i == 1 && currentStatus.equals(statuses.get(i)) && !paketInternal.isBedaZona()) {
                statusPosted = "5";
            } else if (currentStatus.equals(statuses.get(i))) statusPosted = String.valueOf(i + 2);
        }

        if (statusPosted != null) {
            String finalStatusPosted = statusPosted;
            getDataManager().postInternalJobEdit(paketInternal.getKodeInternal(), finalStatusPosted, new OkHttpResponseListener() {
                @Override
                public void onResponse(Response response) {
                    if (response.code() == 200) {
                        AppLogger.d("%d says: Success, Returning to the main activity.", response.code());
                        // getMvpView().showMessage("Status paket berhasil diubah menjadi: " + finalStatusPosted);

                        // Ambil foto hanya jika status paket berubah menjadi nomor 6, "Paket Internal Sudah Diterima TU Tujuan"
                        if (finalStatusPosted.equals("6"))
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
