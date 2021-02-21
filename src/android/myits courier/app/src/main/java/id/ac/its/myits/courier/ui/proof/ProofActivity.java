package id.ac.its.myits.courier.ui.proof;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;

import javax.inject.Inject;

import butterknife.ButterKnife;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.ui.base.BaseActivity;
import id.ac.its.myits.courier.utils.Statics;

import static id.ac.its.myits.courier.ui.proof.ProofPresenter.PERMISSION_STORAGE;
import static id.ac.its.myits.courier.ui.proof.ProofPresenter.REQUEST_TAKE_PHOTO;


public class ProofActivity extends BaseActivity implements ProofMvpView {

    @Inject
    ProofMvpPresenter<ProofMvpView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proof);

        setUp();
    }

    @Override
    protected void setUp() {
        getActivityComponent().inject(ProofActivity.this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(ProofActivity.this);
        mPresenter.getProof(Statics.packageCode);
    }

    @Override
    public Activity getProofActivity() {
        return this;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPresenter.checkStoragePermission();
            } else Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            // Upload to storage
            File f = new File(mPresenter.getCurrentPhotoPath());
            Uri contentUri = Uri.fromFile(f);

            Toast.makeText(this, "Uri " + contentUri.toString(), Toast.LENGTH_SHORT).show();

            // show base64 version of img
            mPresenter.convertImgToBase64();
        }
    }
}