package id.ac.its.myits.courier.ui.job;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.data.db.model.PaketEksternal;
import id.ac.its.myits.courier.data.db.model.PaketInternal;
import id.ac.its.myits.courier.ui.base.BaseActivity;
import id.ac.its.myits.courier.ui.joblist.JobListActivity;
import id.ac.its.myits.courier.ui.jobstatus.JobStatusActivity;
import id.ac.its.myits.courier.ui.main.MainActivity;
import id.ac.its.myits.courier.utils.AppLogger;

public class JobActivity extends BaseActivity implements JobMvpView {

    @Inject
    JobMvpPresenter<JobMvpView> mPresenter;

    @BindView(R.id.changeStatusButton)
    Button changeStatusButton;

    @BindView(R.id.mapView)
    MapView mapView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.deliveryTypeText)
    TextView deliveryType;

    @BindView(R.id.kodeText)
    TextView packageCode;

    @BindView(R.id.unitNameText)
    TextView unitName;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load/initialize the osmdroid configuration, this can be done
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's
        //tile servers will get you banned based on this string

        setContentView(R.layout.activity_job_detail);

        setUp();
        initiateMaps();
    }

    @Override
    protected void setUp() {
        getActivityComponent().inject(JobActivity.this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(JobActivity.this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mapView.setTileSource(TileSourceFactory.MAPNIK);

        mapView.getController().setZoom(17.0);
        mapView.getController().setCenter(mPresenter.getLocation());

        tipePaket = getIntent().getStringExtra("TIPE_PAKET");
        retrieveData(tipePaket);
    }

    private void retrieveData(String tipePaket) {
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
        goBack(MainActivity.username, idUnit);
        finish(); // or your code
    }

    @OnClick(R.id.changeStatusButton)
    public void changeStatus() {
        Intent statusIntent = new Intent(this, JobStatusActivity.class);
        statusIntent.putExtra("TIPE_PAKET", deliveryType.getText());
        statusIntent.putExtra("STATUS", packageStatus.getText());
        statusIntent.putExtra("KODE_PAKET", packageCode.getText());
        statusIntent.putExtra("ID_PAKET", getIntent().getIntExtra("ID_PAKET", 0));
        startActivity(statusIntent);
    }

    public void initiateMaps() {
        mPresenter.requestPermissions(
                // if you need to show the current location, uncomment the line below
                 Manifest.permission.ACCESS_FINE_LOCATION,
                // WRITE_EXTERNAL_STORAGE is required in order to show the map
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.INTERNET
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPresenter.onPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        mapView.onResume(); //needed for compass, my location overlays, v6.0.0 and up

        // Refresh tampilan dan reset flag
        if (fromStatus) {
            retrieveData(tipePaket);
            fromStatus = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        mapView.onPause();  //needed for compass, my location overlays, v6.0.0 and up
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
        packageCode.setText(paket.getNomorResi());

        // Ganti dengan getStringExtra dari activity sebelumnya.
        unitName.setText(paket.getNamaUnit());
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
        unitName.setText(paket.getNamaUnit());
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

        description.setText(paket.getDeskripsi());
    }

    @Override
    public void onUnitIdRetrieved(int idUnit) {
        this.idUnit = idUnit;
    }

    @Override
    public void goBack(String username, int idUnit) {
        Intent backIntent = new Intent(this, JobListActivity.class);

        backIntent.putExtra("USERNAME", username);
        backIntent.putExtra("ID_UNIT", idUnit);

        AppLogger.d("%d: %s", idUnit, username);

        startActivity(backIntent);
    }
}