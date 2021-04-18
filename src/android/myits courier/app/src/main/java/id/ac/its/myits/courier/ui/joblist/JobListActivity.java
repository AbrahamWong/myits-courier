package id.ac.its.myits.courier.ui.joblist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import id.ac.its.myits.courier.utils.Statics;

public class JobListActivity extends BaseActivity implements JobListMvpView {

    @Inject
    JobListMvpPresenter<JobListMvpView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.unitToText)
    TextView unitToText;
    @BindView(R.id.unitFromText)
    TextView unitFromText;

    @BindView(R.id.jobListRecyclerView)
    RecyclerView jobListToRecyclerView;
    @BindView(R.id.jobListFromRecyclerView)
    RecyclerView jobListFromRecyclerView;

    @BindView(R.id.unitFromDrop)
    ImageView packageFromDrop;
    @BindView(R.id.unitToDrop)
    ImageView packageToDrop;
    boolean recyclerFromVisible = false;
    boolean recyclerToVisible = false;

    @BindView(R.id.srl_joblist)
    SwipeRefreshLayout swipeRefreshLayout;

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
        mPresenter.initRecyclerView(jobListToRecyclerView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // API Request
        unitToText.setText(getResources().getString(R.string.unit_name_to, Statics.unitName));
        unitFromText.setText(getResources().getString(R.string.unit_name_from, Statics.unitName));
        idUnit = Statics.currentUnitId;
        mPresenter.getUnitDetails(idUnit);

        // Refresh
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorSecondary);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.getUnitDetails(idUnit);
            swipeRefreshLayout.setRefreshing(false);
        });

        // Show list of items if icon clicked
        packageFromDrop.setOnClickListener((View v) -> {
            packageFromDrop.setImageResource(recyclerFromVisible ?
                    R.drawable.ic_expand_more_black_36 : R.drawable.ic_chevron_right_black_36);
            jobListFromRecyclerView.setVisibility(recyclerFromVisible ? View.VISIBLE : View.GONE);
            recyclerFromVisible = !recyclerFromVisible;
        });
        packageToDrop.setOnClickListener((View v) -> {
            packageToDrop.setImageResource(recyclerToVisible ?
                    R.drawable.ic_expand_more_black_36 : R.drawable.ic_chevron_right_black_36);
            jobListToRecyclerView.setVisibility(recyclerToVisible ? View.VISIBLE : View.GONE);
            recyclerToVisible = !recyclerToVisible;
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
    public void jobToUnitRetrieved(JobListAdapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        jobListToRecyclerView.setAdapter(adapter);
        jobListToRecyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();

        adapter.setOnClickListener(detil -> {
            Intent jobIntent = new Intent(JobListActivity.this, JobActivity.class);

            if (detil.getJenisPaket().equals("Eksternal")) {
                jobIntent.putExtra("ID_PAKET", detil.getIdPaket());

            } else {
                jobIntent.putExtra("KODE_INTERNAL", detil.getKodePaket());
            }
            jobIntent.putExtra("TIPE_PAKET", detil.getJenisPaket());
            jobIntent.putExtra("ID_UNIT", idUnit);

            jobIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

            startActivity(jobIntent);
            finish();
        });
    }

    @Override
    public void jobFromUnitRetrieved(JobListAdapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        jobListFromRecyclerView.setAdapter(adapter);
        jobListFromRecyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();

        adapter.setOnClickListener(detil -> {
            Intent jobIntent = new Intent(JobListActivity.this, JobActivity.class);

            if (detil.getJenisPaket().equals("Eksternal")) {
                jobIntent.putExtra("ID_PAKET", detil.getIdPaket());

            } else {
                jobIntent.putExtra("KODE_INTERNAL", detil.getKodePaket());
            }
            jobIntent.putExtra("TIPE_PAKET", detil.getJenisPaket());
            jobIntent.putExtra("ID_UNIT", idUnit);

            jobIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

            startActivity(jobIntent);
            finish();
        });

    }
}