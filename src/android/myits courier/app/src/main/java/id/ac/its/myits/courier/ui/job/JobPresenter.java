package id.ac.its.myits.courier.ui.job;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

import javax.inject.Inject;

import id.ac.its.myits.courier.data.DataManager;
import id.ac.its.myits.courier.data.db.model.PaketEksternal;
import id.ac.its.myits.courier.data.db.model.PaketInternal;
import id.ac.its.myits.courier.ui.base.BasePresenter;
import id.ac.its.myits.courier.utils.AppLogger;
import id.ac.its.myits.courier.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;


public class JobPresenter<V extends JobMvpView> extends BasePresenter<V>
        implements JobMvpPresenter<V> {

    // const in Java
    private static final int MAP_REQUEST_CODE = 104;

    // Koordinat TC, ganti dengan data dari API jika sudah bisa
    private static GeoPoint location = new GeoPoint(-7.27957,112.79728);

    PaketEksternal paketEksternal;

    @Inject
    public JobPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void requestPermissions(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission((Context) getMvpView(), permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) getMvpView(), permissions, MAP_REQUEST_CODE);
            }
        }

    }

    @Override
    public void onPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MAP_REQUEST_CODE :
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText((Activity) getMvpView(), "You need to allow map to use this app.", Toast.LENGTH_SHORT).show();
                } else {
                    // Success
                }
                break;
        }
    }

    @Override
    public GeoPoint getLocation() {
        return location;
    }

    @Override
    public void getEksternalPaket(int id) {
        getMvpView().showLoading(null, null);
        getDataManager().getExternalJobDetail(id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(jsonArray -> {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    // Pasang manual karena APInya berbentuk JSONArray, bukan PaketEksternal.class
                    PaketEksternal paket = new PaketEksternal();
                    paket.setIdPaket(id);
                    paket.setNomorResi(jsonObject.getString("kode"));
                    paket.setNamaPetugas(jsonObject.getString("nama_petugas"));
                    paket.setStatus(jsonObject.getString("STATUS"));
                    paket.setBeratMinimal(jsonObject.getInt("berat_minimal"));
                    paket.setBeratMaksimal(jsonObject.getInt("berat_maksimal"));
                    paket.setDeskripsiPaket(jsonObject.getString("deskripsi"));
                    paket.setNamaUnit(jsonObject.getString("nama_unit"));

                    getMvpView().setAllExternalText(paket);
                    getMvpView().hideLoading();
                }, new Consumer<Throwable>(){
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (getMvpView().isNetworkConnected()) {
                            AppLogger.d("Telah terjadi kesalahan");
                            getMvpView().showMessage("Terjadi kesalahan! Mohon untuk mengulang kembali.");
                            getMvpView().hideLoading();
                        }
                    }
                });
    }

    @Override
    public void getInternalPaket(String kodePaket) {
        getMvpView().showLoading(null, null);
        getDataManager().getInternalJobDetail(kodePaket)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(jsonArray -> {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    // Pasang manual karena APInya berbentuk JSONArray, bukan PaketEksternal.class
                    PaketInternal paket = new PaketInternal();
                    paket.setIdPaket(jsonObject.getInt("id_paket"));
                    paket.setKodeInternal(kodePaket);
                    paket.setNama_tu(jsonObject.getString("nama_petugas"));
                    paket.setStatus(jsonObject.getString("STATUS"));
                    paket.setBerat_minimal(jsonObject.getInt("berat_minimal"));
                    paket.setBerat_maksimal(jsonObject.getInt("berat_maksimal"));
                    paket.setDeskripsi(jsonObject.getString("deskripsi"));
                    paket.setNamaUnit(jsonObject.getString("nama_unit"));

                    getMvpView().setAllInternalText(paket);
                    getMvpView().hideLoading();
                }, new Consumer<Throwable>(){
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (getMvpView().isNetworkConnected()) {
                            AppLogger.d("Telah terjadi kesalahan");
                            getMvpView().showMessage("Terjadi kesalahan! Mohon untuk mengulang kembali.");
                            getMvpView().hideLoading();
                        }
                    }
                });
    }
}
