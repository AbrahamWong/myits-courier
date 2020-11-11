package id.ac.its.myits.courier.ui.qr;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.budiyev.android.codescanner.CodeScannerView;

import java.util.ArrayList;

import id.ac.its.myits.courier.data.db.model.Unit;
import id.ac.its.myits.courier.ui.base.MvpPresenter;
import id.ac.its.myits.courier.ui.base.MvpView;
import id.ac.its.myits.courier.utils.AuthStateManager;

public interface QrMvpPresenter<V extends QrMvpView & MvpView> extends MvpPresenter<V> {
    void setMvpView(QrMvpView v);
    void requestPermissions();
    void onPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

    void scanQrCode(CodeScannerView scannerView);
    void startQrPreview();
    void releaseResource();
}
