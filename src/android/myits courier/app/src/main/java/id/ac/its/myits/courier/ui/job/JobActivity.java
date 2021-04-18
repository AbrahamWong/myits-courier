package id.ac.its.myits.courier.ui.job;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.data.db.model.PaketEksternal;
import id.ac.its.myits.courier.data.db.model.PaketInternal;
import id.ac.its.myits.courier.ui.base.BaseActivity;
import id.ac.its.myits.courier.ui.joblist.JobListActivity;
import id.ac.its.myits.courier.ui.jobstatus.JobStatusActivity;
import id.ac.its.myits.courier.utils.AppLogger;
import id.ac.its.myits.courier.utils.Statics;

public class JobActivity extends BaseActivity implements JobMvpView {

    @Inject
    JobMvpPresenter<JobMvpView> mPresenter;

    @BindView(R.id.changeStatusButton)
    Button changeStatusButton;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.deliveryTypeText)
    TextView deliveryType;

    @BindView(R.id.kodeText)
    TextView packageCode;

    @BindView(R.id.unitNameTextFrom)
    TextView unitNameFrom;
    @BindViews({R.id.personIconUnitFrom, R.id.unitLabelFrom})
    List<View> fromView;
    @BindView(R.id.unitNameTextTo)
    TextView unitNameTo;

    @BindView(R.id.picText)
    TextView PIC;

    @BindView(R.id.packageStatus)
    TextView packageStatus;

    @BindView(R.id.weightText)
    TextView weightText;

    @BindView(R.id.countPacketText)
    TextView packageQty;

    @BindView(R.id.textView3)
    TextView description;

    // Untuk menandakan bahwa telah selesai mengubah status
    public static boolean fromStatus = false;
    int idEksternal = 0;
    static String tipePaket;
    String kodeInternal = "";
    int idUnit;

    boolean isBedaZona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        setUp();
    }

    @Override
    protected void setUp() {
        getActivityComponent().inject(JobActivity.this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(JobActivity.this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tipePaket = getIntent().getStringExtra("TIPE_PAKET");
        retrieveData(tipePaket);
    }

    private void retrieveData(String tipePaket) {
        AppLogger.d("Tipe paket: %s", tipePaket);
        if (tipePaket.equals("Eksternal")) {
            idEksternal = getIntent().getIntExtra("ID_PAKET", 0);
            onDataFetched(
                    String.valueOf(idEksternal),
                    tipePaket);
            AppLogger.d("Id: %d test here",
                    getIntent().getIntExtra("ID_PAKET", 0));
        } else {
            kodeInternal = getIntent().getStringExtra("KODE_INTERNAL");
            onDataFetched(
                    kodeInternal,
                    tipePaket
            );
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
        goBack(Statics.username, idUnit);
        finish(); // or your code
    }

    @OnClick(R.id.changeStatusButton)
    public void changeStatus() {
        Intent statusIntent = new Intent(this, JobStatusActivity.class);
        statusIntent.putExtra("TIPE_PAKET", deliveryType.getText());
        statusIntent.putExtra("STATUS", packageStatus.getText());
        statusIntent.putExtra("KODE_PAKET", packageCode.getText());
        statusIntent.putExtra("ID_PAKET", getIntent().getIntExtra("ID_PAKET", 0));
        statusIntent.putExtra("BEDA_ZONA", isBedaZona);
        startActivity(statusIntent);
    }

    @Override
    public void onDataFetched(String id, String tipePaket) {
        if (tipePaket.equals("Eksternal")) {
            mPresenter.getEksternalPaket(Integer.parseInt(id));
        } else {
            mPresenter.getInternalPaket(id);
        }
    }

    @Override
    public void setAllExternalText(PaketEksternal paket) {
        deliveryType.setText(getIntent().getStringExtra("TIPE_PAKET"));
        packageCode.setText(paket.getKodeEksternal());

        // Ganti dengan getStringExtra dari activity sebelumnya.
        for (View v : fromView) {
            v.setVisibility(View.GONE);
        }
        unitNameFrom.setVisibility(View.GONE);

        unitNameTo.setText(paket.getNamaUnit());
        PIC.setText(paket.getNamaPetugas());
        packageStatus.setText(paket.getStatus());

        if (paket.getBeratMaksimal() < paket.getBeratMinimal()) {
            weightText.setText(
                    String.format(
                            Locale.ENGLISH,
                            "Lebih dari %d kg",
                            paket.getBeratMinimal()));
        } else {
            weightText.setText(
                    String.format(
                            Locale.ENGLISH,
                            "%d - %d kg",
                            paket.getBeratMinimal(), paket.getBeratMaksimal()));
        }

        description.setText(paket.getDeskripsiPaket());
    }

    @Override
    public void setAllInternalText(PaketInternal paket) {
        deliveryType.setText(getIntent().getStringExtra("TIPE_PAKET"));
        packageCode.setText(paket.getKodeInternal());

        // Ganti dengan getStringExtra dari activity sebelumnya.
        unitNameFrom.setText(paket.getNamaUnitAsal());
        unitNameTo.setText(paket.getNamaUnitTujuan());
        PIC.setText(paket.getNama_tu());
        packageStatus.setText(paket.getStatus());

        if (paket.getBerat_maksimal() < paket.getBerat_minimal()) {
            weightText.setText(
                    String.format(
                            Locale.ENGLISH,
                            "Lebih dari %d kg",
                            paket.getBerat_minimal()));

        } else {
            weightText.setText(
                    String.format(
                            Locale.ENGLISH,
                            "%d - %d kg",
                            paket.getBerat_minimal(), paket.getBerat_maksimal()));
        }

        isBedaZona = paket.isBedaZona();
        description.setText(paket.getDeskripsi());
    }

    @Override
    public void goBack(String username, int idUnit) {
        Intent backIntent = new Intent(this, JobListActivity.class);

//        backIntent.putExtra("USERNAME", username);
//        backIntent.putExtra("ID_UNIT", idUnit);
//
//        AppLogger.d("%d: %s", idUnit, username);

        startActivity(backIntent);
    }
}