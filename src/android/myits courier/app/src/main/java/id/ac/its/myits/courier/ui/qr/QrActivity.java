package id.ac.its.myits.courier.ui.qr;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.budiyev.android.codescanner.CodeScannerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.data.db.model.Unit;
import id.ac.its.myits.courier.ui.base.BaseActivity;
import id.ac.its.myits.courier.ui.detail.DetailActivity;
import id.ac.its.myits.courier.ui.login.LoginActivity;
import id.ac.its.myits.courier.utils.AuthStateManager;


public class QrActivity extends BaseActivity implements QrMvpView {

    @Inject
    QrMvpPresenter<QrMvpView> mPresenter;

    // Based on https://www.youtube.com/watch?v=drH63NpSWyk
    @BindView(R.id.scanner_view)
    CodeScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        setUp();
        mPresenter.scanQrCode(scannerView);
    }

    @Override
    protected void setUp() {
        getActivityComponent().inject(QrActivity.this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.setMvpView(this);
        mPresenter.onAttach(QrActivity.this);
        onQrInitiated();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.startQrPreview();
    }

    @Override
    protected void onPause() {
        mPresenter.releaseResource();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mPresenter.releaseResource();
        super.onDestroy();
    }

    @Override
    public Activity getQrActivity() {
        return this;
    }

    @Override
    public void onQrInitiated() {
        mPresenter.requestPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPresenter.onPermissionsResult(requestCode, permissions, grantResults);
    }
}