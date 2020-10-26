package id.ac.its.myits.courier.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.data.db.model.Unit;
import id.ac.its.myits.courier.ui.base.BaseActivity;
import id.ac.its.myits.courier.ui.main.MainActivity;
import id.ac.its.myits.courier.ui.main.MainMvpView;

public class DetailActivity extends BaseActivity implements DetailMvpView {

    @Inject
    DetailMvpPresenter<DetailMvpView> mPresenter;

    @BindView(R.id.tv_tata_usaha)
    TextView tvTataUsaha;

    @BindView(R.id.rv_paket)
    RecyclerView rvPaket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setUp();
        mPresenter.plugRecycler(rvPaket);
    }

    @Override
    protected void setUp() {
        getActivityComponent().inject(DetailActivity.this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(DetailActivity.this);
        mPresenter.onIntentRetrieved();

        tvTataUsaha.setText(mPresenter.getUnitName());
    }

    @Override
    public String retrieveFromIntent() {
        Intent intent = getIntent();
        return intent.getStringExtra("KEY_UNIT");
    }
}