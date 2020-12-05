package id.ac.its.myits.courier.ui.history;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.ui.base.BaseActivity;


public class HistoryDetailActivity extends BaseActivity implements HistoryMvpView {

    @Inject
    HistoryMvpPresenter<HistoryMvpView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.timelineRecyclerView)
    RecyclerView timelineRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        setUp();
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

    @Override
    protected void setUp() {
        getActivityComponent().inject(HistoryDetailActivity.this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.setMvpView(this);
        mPresenter.onAttach(HistoryDetailActivity.this);
        mPresenter.plugRecycler(timelineRecyclerView);
    }
}