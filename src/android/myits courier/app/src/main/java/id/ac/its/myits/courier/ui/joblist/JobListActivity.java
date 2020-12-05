package id.ac.its.myits.courier.ui.joblist;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.ui.adapter.JobListAdapter;
import id.ac.its.myits.courier.ui.base.BaseActivity;

public class JobListActivity extends BaseActivity implements JobListMvpView {

    @Inject
    JobListMvpPresenter<JobListMvpView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.jobListRecyclerView)
    RecyclerView jobListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);

        setUp();
    }

    @Override
    protected void setUp() {
        getActivityComponent().inject(JobListActivity.this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(JobListActivity.this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mPresenter.getUnitDetails(getIntent().getStringExtra("USERNAME"), getIntent().getIntExtra("ID_UNIT", 0));
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

    @Override
    public void onDetailRetrieved(JobListAdapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        jobListRecyclerView.setAdapter(adapter);
        jobListRecyclerView.setLayoutManager(layoutManager);
    }
}