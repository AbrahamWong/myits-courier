package id.ac.its.myits.courier.ui.jobstatus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.ui.base.BaseActivity;
import id.ac.its.myits.courier.ui.job.JobActivity;
import id.ac.its.myits.courier.ui.main.MainActivity;
import id.ac.its.myits.courier.ui.proof.ProofActivity;
import id.ac.its.myits.courier.utils.AppLogger;

public class JobStatusActivity extends BaseActivity implements JobStatusMvpView {

    @Inject
    JobStatusMvpPresenter<JobStatusMvpView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.statusRadioGroup)
    RadioGroup statusRadioGroup;

    @BindView(R.id.changeStatusLabel)
    TextView changeStatusLabel;

    @BindView(R.id.confirmStatusButton)
    Button confirmStatusButton;

    private String deliveryType;
    private ArrayList<RadioButton> radioButtons;
    private boolean isBedaZona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_status_change);

        setUp();
    }

    @Override
    protected void setUp() {
        getActivityComponent().inject(JobStatusActivity.this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(JobStatusActivity.this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        deliveryType = getIntent().getStringExtra("TIPE_PAKET");
        isBedaZona = getIntent().getBooleanExtra("BEDA_ZONA", false);
        AppLogger.d("%s adalah tipe paket", deliveryType);

        String statusLabel = String.format(Locale.getDefault(),
                "%s %s",
                getResources().getText(R.string.change_package_status),
                deliveryType);
        changeStatusLabel.setText(statusLabel);
        if (deliveryType.equals("Eksternal")) {
            mPresenter.getExternalStatuses();
        } else if (deliveryType.split("\\s+")[0].equals("Internal")) {
            mPresenter.getInternalStatuses();
        }
    }

    // force back button navigation
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish(); // or your code
    }

    @OnClick(R.id.confirmStatusButton)
    public void confirmStatus() {
        RadioButton option = findViewById(statusRadioGroup.getCheckedRadioButtonId());

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            changeStatus(deliveryType, option);
            JobActivity.fromStatus = true;
            finish();
        }, 800);

    }

    @Override
    public void onExternalListGet(ArrayList<String> status) {
        radioButtons = mPresenter.createRadioButtons(status, statusRadioGroup);

        for (RadioButton rb : radioButtons) {
            String radioText = rb.getText().toString();
            String intentStatus = getIntent().getStringExtra("STATUS");

            // Lakukan karena semua status berformat "Paket Eksternal....", ada "l" sebelum status asli.
            rb.setChecked(radioText.equals(intentStatus.substring(intentStatus.lastIndexOf('l') + 2)));
        }
    }

    @Override
    public void onInternalListGet(ArrayList<String> status) {
        radioButtons = mPresenter.createRadioButtons(status, statusRadioGroup);

        for (RadioButton rb : radioButtons) {
            String radioText = rb.getText().toString();
            String intentStatus = getIntent().getStringExtra("STATUS");

            // Lakukan karena semua status berformat "Paket Internal....", ada "l" sebelum status asli.
            rb.setChecked(radioText.equals(intentStatus.substring(intentStatus.lastIndexOf('l') + 2)));

            if (!isBedaZona) { // Sembunyikan status nomor 3 dan 4
                statusRadioGroup.getChildAt(2).setVisibility(View.GONE);
                statusRadioGroup.getChildAt(3).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public RadioButton onRadioButtonRequested() {
        return new RadioButton(this);
    }

    private void changeStatus(String deliveryType, RadioButton selectedRadio) {
        String kodePaket = getIntent().getStringExtra("KODE_PAKET");
        int idPaket = getIntent().getIntExtra("ID_PAKET", 0);

        AppLogger.d("KODE_INTERNAL: %s", kodePaket);
        Intent homeIntent = new Intent(JobStatusActivity.this, MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(homeIntent);

        // Karena RadioButton dibuat secara automatis, kita tidak dapat memantau setiap
        // RadioButton menggunakan id. Karena itu...
        int rbindex = statusRadioGroup.indexOfChild(selectedRadio);
        switch (deliveryType) {
            // Status Internal ada 2, Internal Penjemputan dan Internal Pengantaran
            case "Internal Penjemputan":
            case "Internal Pengantaran":

                // Jika zona unit asal dan tujuan sama (false), status yang dipakai hanya 1, 2, 5, dan 6.
                // Jika zona unit asal dan tujuan beda (true), status yang dipakai 1 - 6.
                if (!isBedaZona && rbindex == 2) {
                    mPresenter.postInternalStatus(kodePaket, "5");
                } else {
                    mPresenter.postInternalStatus(kodePaket, String.valueOf(rbindex + 1));
                }

                if (rbindex + 1 == radioButtons.size())
                    startActivity(new Intent(JobStatusActivity.this, ProofActivity.class));

                break;
            case "Eksternal":
                mPresenter.postExternalStatus(idPaket, String.valueOf(rbindex + 1));
                if (rbindex + 1 == radioButtons.size())
                    startActivity(new Intent(JobStatusActivity.this, ProofActivity.class));
        }
    }
}