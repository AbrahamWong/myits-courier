package id.ac.its.myits.courier.ui.joblist;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.ui.adapter.JobListAdapter;
import id.ac.its.myits.courier.ui.base.BaseActivity;
import id.ac.its.myits.courier.ui.job.JobActivity;
import id.ac.its.myits.courier.utils.AppLogger;

public class JobListActivity extends BaseActivity implements JobListMvpView {

    @Inject
    JobListMvpPresenter<JobListMvpView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.jobListRecyclerView)
    RecyclerView jobListRecyclerView;

    @BindView(R.id.srl_joblist)
    SwipeRefreshLayout swipeRefreshLayout;

    private String username;
    private int idUnit;

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
        mPresenter.initRecyclerView(jobListRecyclerView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        username = getIntent().getStringExtra("USERNAME");
        idUnit = getIntent().getIntExtra("ID_UNIT", 0);
        AppLogger.d("Id unit %d with username %s.\n\n", idUnit, username);
        mPresenter.getUnitDetails(username, idUnit);

        // Refresh
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorSecondary);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.getUnitDetails(username, idUnit);
            swipeRefreshLayout.setRefreshing(false);
        });
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
        adapter.notifyDataSetChanged();

        adapter.setOnClickListener(detil -> {
            Intent jobIntent = new Intent(JobListActivity.this, JobActivity.class);

            if (detil.getJenisPaket().equals("Eksternal")) {
                jobIntent.putExtra("ID_PAKET", detil.getIdPaket());

            } else {
                jobIntent.putExtra("KODE_INTERNAL", detil.getKodePaket());
            }
            jobIntent.putExtra("TIPE_PAKET", detil.getJenisPaket());
            jobIntent.putExtra("USERNAME", username);
            jobIntent.putExtra("ID_UNIT", idUnit);
            jobIntent.putExtra("RETURN", 0);

            startActivity(jobIntent);
            finish();
        });
    }
}