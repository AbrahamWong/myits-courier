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

    @BindView(R.id.status_1)
    RadioButton status1;
    @BindView(R.id.status_2)
    RadioButton status2;
    @BindView(R.id.status_3)
    RadioButton status3;
    @BindView(R.id.status_4)
    RadioButton status4;
    @BindView(R.id.status_5)
    RadioButton status5;

    @BindView(R.id.changeStatusLabel)
    TextView changeStatusLabel;

    @BindView(R.id.confirmStatusButton)
    Button confirmStatusButton;

    private String deliveryType;

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
        AppLogger.d("%s adalah tipe paket", deliveryType);

        String statusLabel = String.format(Locale.getDefault(),
                "%s %s",
                getResources().getText(R.string.change_packet_status),
                deliveryType);
        changeStatusLabel.setText(statusLabel);
        if (deliveryType.equals("Eksternal")) {
            mPresenter.getExternalStatuses();
        } else if (deliveryType.equals("Internal")) {
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
        status1.setText(status.get(0));
        status2.setText(status.get(1));
        status3.setText(status.get(2));
        status4.setVisibility(View.GONE);
        status5.setVisibility(View.GONE);

        String jobStatus = getIntent().getStringExtra("STATUS");
        if (status.get(0).equals(jobStatus)) {
            status1.setChecked(true);
        } else if (status.get(1).equals(jobStatus)) {
            status2.setChecked(true);
        } else if (status.get(2).equals(jobStatus)) {
            status3.setChecked(true);
        }
    }

    @Override
    public void onInternalListGet(ArrayList<String> status) {
        status1.setText(status.get(0));
        status2.setText(status.get(1));
        status3.setText(status.get(2));
        status4.setText(status.get(3));
        status5.setVisibility(View.GONE);

        String jobStatus = getIntent().getStringExtra("STATUS");
        String newStatus = jobStatus.substring(jobStatus.indexOf(" ", 6) + 1);
        if (status.get(0).equals(newStatus)) {
            status1.setChecked(true);
        } else if (status.get(1).equals(newStatus)) {
            status2.setChecked(true);
        } else if (status.get(2).equals(newStatus)) {
            status3.setChecked(true);
        } else if (status.get(3).equals(newStatus)) {
            status4.setChecked(true);
        } else if (status.get(4).equals(newStatus)) {
            status5.setChecked(true);
        }
    }

    private void changeStatus(String deliveryType, RadioButton selectedRadio) {
        String kodePaket = getIntent().getStringExtra("KODE_PAKET");
        int idPaket = getIntent().getIntExtra("ID_PAKET", 0);

        Intent homeIntent = new Intent(JobStatusActivity.this, MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(homeIntent);

        switch (deliveryType) {
            case "Internal":
                switch (selectedRadio.getId()) {
                    case R.id.status_1:
                        mPresenter.postInternalStatus(kodePaket, "1");
                        break;
                    case R.id.status_2:
                        mPresenter.postInternalStatus(kodePaket, "2");
                        break;
                    case R.id.status_3:
                        mPresenter.postInternalStatus(kodePaket, "3");
                        break;
                    case R.id.status_4:
                        mPresenter.postInternalStatus(kodePaket, "4");
                        break;
                    case R.id.status_5:
                        startActivity(new Intent(JobStatusActivity.this, ProofActivity.class));
                        mPresenter.postInternalStatus(kodePaket, "5");
                        break;
                }
                break;
            case "Eksternal":
                switch (selectedRadio.getId()) {
                    case R.id.status_1:
                        mPresenter.postExternalStatus(idPaket, "1");
                        break;
                    case R.id.status_2:
                        mPresenter.postExternalStatus(idPaket, "2");
                        break;
                    case R.id.status_3:
                        startActivity(new Intent(JobStatusActivity.this, ProofActivity.class));
                        mPresenter.postExternalStatus(idPaket, "3");
                        break;
                }
                break;
        }
    }
}