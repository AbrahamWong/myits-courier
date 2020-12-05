package id.ac.its.myits.courier.ui.qr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

import javax.inject.Inject;

import id.ac.its.myits.courier.data.DataManager;
import id.ac.its.myits.courier.ui.base.BasePresenter;
import id.ac.its.myits.courier.ui.job.JobActivity;
import id.ac.its.myits.courier.utils.AppLogger;
import id.ac.its.myits.courier.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
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
                view.getQrActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent packageIntent = new Intent(view.getQrActivity(), JobActivity.class);
                        packageIntent.putExtra("KODE_INTERNAL", result.getText());
                        packageIntent.putExtra("TIPE_PAKET", "Internal");
                        packageIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        view.getQrActivity().startActivity(packageIntent);
                        view.getQrActivity().finish();
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
}
