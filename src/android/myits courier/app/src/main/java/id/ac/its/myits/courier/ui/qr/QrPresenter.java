package id.ac.its.myits.courier.ui.qr;

import android.Manifest;
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
import id.ac.its.myits.courier.utils.AppLogger;
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

//        scannerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                scanner.startPreview();
//            }
//        });
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

                    getStatusLists(paket);
                    AppLogger.d("QR: First, the internal package we get is this. %s is the package ID and status is %s.",
                            paket.getKodeInternal(),
                            paket.getStatus());
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
                    AppLogger.d("QR: Once we throw it into new function, the internal package becomes:\n\tInternal ID: %s\n\tStatus: %s",
                            paketInternal.getKodeInternal(),
                            paketInternal.getStatus());

                    ArrayList<String> statuses = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        String status = jsonArray.getJSONObject(i).getString("status");
                        statuses.add(status);
                    }

                    changeStatus(paketInternal, statuses);
                    AppLogger.d("QR: Then we pass the internal status lists and the internal package into next stage.\n" +
                            "Checking internal statuses...\n%s", statuses.toString());
                }, throwable -> {
                    if (getMvpView().isNetworkConnected()) {
                        AppLogger.e(throwable, "Error yang diberikan adalah:");
                    }
                });
    }

    void changeStatus(PaketInternal paketInternal, ArrayList<String> statuses) {
        AppLogger.d("QR: %s is here? Oh, yeah. Right...", paketInternal.getKodeInternal());

        String statusPosted = "nothing, because the internal status is invalid";
        boolean needToChange = true;
        if (!statuses.isEmpty()) {
            if (paketInternal.getStatus().equals(statuses.get(0))) statusPosted = statuses.get(1);

                // Seharusnya ini pilihan, jika paket internal memiliki tempat asal dan tujuan yang
                // zonanya sama, atau dalam Java,  (getZona() tidak ada)
                //          if (paketInternal.getStatus().equals(statuses.get(1))
                //                  && paketInternal.getZona() == MainActivity.userZone)
                // pilih status.get(2), jika zonanya berbeda, pilih status.get(3)
            else if (paketInternal.getStatus().equals(statuses.get(1)))
                statusPosted = statuses.get(2);
            else if (paketInternal.getStatus().equals(statuses.get(2)))
                statusPosted = statuses.get(3);

            else if (paketInternal.getStatus().equals(statuses.get(3)))
                statusPosted = statuses.get(4);
            else if (paketInternal.getStatus().equals(statuses.get(4))) needToChange = false;
            else needToChange = false;
        }

        AppLogger.d("QR: So for now, needToChange is set to %b, \nand the status will be changed into %s.", needToChange, statusPosted);

        if (needToChange && !statusPosted.equals("nothing, because the internal status is invalid")) {
            String finalStatusPosted = statusPosted;
            getDataManager().postInternalJobEdit(paketInternal.getKodeInternal(), finalStatusPosted, new OkHttpResponseListener() {
                @Override
                public void onResponse(Response response) {
                    if (response.code() == 200) {
                        AppLogger.d("%d says: Success, Returning to the main activity.", response.code());
                        getMvpView().showMessage("Status paket berhasil diubah menjadi: " + finalStatusPosted);
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
