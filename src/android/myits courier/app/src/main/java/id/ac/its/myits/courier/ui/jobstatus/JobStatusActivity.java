package id.ac.its.myits.courier.ui.jobstatus;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.widget.Toolbar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.ui.base.BaseActivity;

public class JobStatusActivity extends BaseActivity implements JobStatusMvpView {

    @Inject
    JobStatusMvpPresenter<JobStatusMvpView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.statusRadioGroup)
    RadioGroup statusRadioGroup;

    @BindView(R.id.confirmStatusButton)
    Button confirmStatusButton;

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
        switch (option.getId()) {
            case R.id.status_1:
                break;
            case R.id.status_2:
                break;
            case R.id.status_3:
                break;
            case R.id.status_4:
                break;
        }
        finish();
    }
}