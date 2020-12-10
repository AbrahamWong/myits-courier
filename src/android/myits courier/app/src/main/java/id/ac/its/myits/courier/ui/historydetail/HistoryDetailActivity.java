package id.ac.its.myits.courier.ui.historydetail;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.data.db.model.DetilStatus;
import id.ac.its.myits.courier.ui.base.BaseActivity;


public class HistoryDetailActivity extends BaseActivity implements HistoryMvpView {

    @Inject
    HistoryMvpPresenter<HistoryMvpView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.deliveryTypeText)
    TextView deliveryTypeText;

    @BindView(R.id.kodeText)
    TextView kodeText;

    @BindView(R.id.unitNameText)
    TextView unitNameText;

    @BindView(R.id.picText)
    TextView picText;

    @BindView(R.id.packageStatus)
    TextView packageStatus;

    @BindView(R.id.weightText)
    TextView weightText;

    @BindView(R.id.countPacketText)
    TextView countPacketText;

    @BindView(R.id.textView3)
    TextView descriptionText;

    @BindView(R.id.timelineRecyclerView)
    RecyclerView timelineRecyclerView;

    private String kodePaket;

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
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        getActivityComponent().inject(HistoryDetailActivity.this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.setMvpView(this);
        mPresenter.onAttach(HistoryDetailActivity.this);
        mPresenter.initRecycler(timelineRecyclerView);

        kodePaket = getIntent().getStringExtra("KODE_PAKET");
        mPresenter.getHistoryDetail(kodePaket);
    }

    @Override
    public void showHistoryDetails(DetilRiwayat historyDetail, ArrayList<DetilStatus> historyList) {
        deliveryTypeText.setText(historyDetail.getJenisPaket());
        kodeText.setText(historyDetail.getKodePaket());
        unitNameText.setText(historyDetail.getNamaUnit());
        picText.setText(historyDetail.getNamaPetugas());
        packageStatus.setText(historyDetail.getStatus());
        if (historyDetail.getBeratMinimal() > historyDetail.getBeratMaksimal()) {
            weightText.setText(String.format(Locale.ENGLISH, "Lebih dari %d kg",
                    historyDetail.getBeratMinimal()));
        } else {
            weightText.setText(String.format(Locale.ENGLISH, "%d - %d kg",
                    historyDetail.getBeratMinimal(),
                    historyDetail.getBeratMaksimal()));
        }
        countPacketText.setText(String.format(Locale.ENGLISH, "%d paket", historyDetail.getJumlahPaket()));
        descriptionText.setText(historyDetail.getDeskripsi());


        mPresenter.plugRecycler(timelineRecyclerView, historyList);
        hideLoading();
    }
}