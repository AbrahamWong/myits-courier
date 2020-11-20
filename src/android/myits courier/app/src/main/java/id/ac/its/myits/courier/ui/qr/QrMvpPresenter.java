package id.ac.its.myits.courier.ui.qr;

import androidx.annotation.NonNull;

import com.budiyev.android.codescanner.CodeScannerView;

import id.ac.its.myits.courier.ui.base.MvpPresenter;
import id.ac.its.myits.courier.ui.base.MvpView;

public interface QrMvpPresenter<V extends QrMvpView & MvpView> extends MvpPresenter<V> {
    void setMvpView(QrMvpView v);
    void requestPermissions();
    void onPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

    void scanQrCode(CodeScannerView scannerView);
    void startQrPreview();
    void releaseResource();
}
