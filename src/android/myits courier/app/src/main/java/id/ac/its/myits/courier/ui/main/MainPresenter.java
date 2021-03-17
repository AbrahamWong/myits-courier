package id.ac.its.myits.courier.ui.main;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.inject.Inject;

import id.ac.its.myits.courier.data.DataManager;
import id.ac.its.myits.courier.data.db.model.DetilPekerjaan;
import id.ac.its.myits.courier.data.db.model.Unit;
import id.ac.its.myits.courier.data.network.model.courier.UserInfo;
import id.ac.its.myits.courier.ui.base.BasePresenter;
import id.ac.its.myits.courier.ui.main.fragment.home.HomeFragment;
import id.ac.its.myits.courier.ui.main.fragment.profile.ProfileFragment;
import id.ac.its.myits.courier.utils.AppLogger;
import id.ac.its.myits.courier.utils.Statics;
import id.ac.its.myits.courier.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;


public class MainPresenter  <V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    @Inject
    public MainPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onLoggingOut() {
        getDataManager().clearSharedPreferences();
        getMvpView().openLoginActivity();
    }

    @Override
    public void test() {
        getDataManager().doGetUserInfo()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(userInfo -> AppLogger.d("AppAuthSample " + "userinfo " + userInfo.getUserdata().getPreferredUsername()), new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (getMvpView().isNetworkConnected()) {
                            getMvpView().showMessage("Terjadi kesalahan! Mohon untuk mengulang kembali.");
                            getMvpView().hideLoading();
                        }
                    }
                });
    }

    @Override
    public UserInfo getUserInfo() {
        final UserInfo[] ui = {null};
        getDataManager().doGetUserInfo()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(userInfo -> {
                    AppLogger.d("AppAuthSample " + "userinfo " + userInfo.getUserdata().getPreferredUsername());
                    AppLogger.d("AppAuthSample " + "username " + userInfo.getUserdata().getUsername());
                    AppLogger.d("AppAuthSample " + "id / sub " + userInfo.getUserdata().getSub());
//                    getMvpView().showMessage("Selamat datang " + userInfo.getUserdata().getUsername());
                    ui[0] = userInfo;
                    Statics.username = userInfo.getUserdata().getUsername();
                    Statics.userSsoId = userInfo.getUserdata().getIdSSO();
                    Statics.userZone = userInfo.getUserdata().getZonaCaraka();

                    getUnits(userInfo.getUserdata().getUsername());
                }, throwable -> {
                    if (getMvpView().isNetworkConnected()) {
                        getMvpView().showMessage("Terjadi kesalahan! Mohon untuk mengulang kembali.");
                        getMvpView().hideLoading();
                    }
                });
        return ui[0];
    }

    @Override
    public void getUnits(String username) {
        getMvpView().showLoading(null, null);
        getDataManager().getUnitList(username)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(jsonObject -> {
                    ArrayList<Unit> unitList = new ArrayList<>();

                    JSONArray array = jsonObject.getJSONArray("unit");

                    setHomeDetail(username, Statics.userZone);
                    setProfileDetail(username, Statics.userZone);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonUnit = array.getJSONObject(i);
                        Unit unit = new Unit();
                        unit.set_id(jsonUnit.getInt("id_unit"));
                        unit.setNamaUnit(jsonUnit.getString("nama_unit"));
                        unit.setJumlahPaketEksternal(jsonUnit.getInt("jumlah_eksternal"));
                        unit.setJumlahPaketInternalMasuk(jsonUnit.getInt("jumlah_internal_in"));
                        unit.setJumlahPaketInternalKeluar(jsonUnit.getInt("jumlah_internal_out"));

                        unitList.add(unit);
                        AppLogger.d("Unit %s ditambahkan ke unitList", unit.getNamaUnit());
                    }

                    Comparator<Unit> unitComparator = Comparator.comparing(Unit::getJumlahPaket);
                    Collections.sort(unitList, unitComparator);
                    Collections.reverse(unitList);

                    getMvpView().showUnitList(unitList);
                    getMvpView().hideLoading();
                }, throwable -> {
                    if (getMvpView().isNetworkConnected()) {
                        getMvpView().showMessage("Terjadi kesalahan! Mohon untuk mengulang kembali.");
                        getMvpView().hideLoading();
                        AppLogger.e(throwable, "Error yang diberikan adalah:");
                    }
                });
    }

    @Override
    public void getAllHistory(String username) {
        getDataManager().getAllHistory(username)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(jsonObject -> {
                    ArrayList<DetilPekerjaan> jobList = new ArrayList<>();

                    int total = jsonObject.getInt("total");
                    JSONArray array = jsonObject.getJSONArray("paket");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonJobs = array.getJSONObject(i);
                        DetilPekerjaan jobDetail = new DetilPekerjaan();
                        jobDetail.setStatus(jsonJobs.getString("STATUS"));
                        jobDetail.setKodePaket(jsonJobs.getString("kode"));
                        jobDetail.setNamaPetugas(jsonJobs.getString("nama_petugas"));
                        jobDetail.setJumlahPaket(jsonJobs.getInt("jumlah"));
                        jobDetail.setJenisPaket(jsonJobs.getString("jenis"));
                        jobDetail.setTanggal(jsonJobs.getString("tanggal"));

                        AppLogger.d("%s ditambahkan ke jobList", jobDetail.getKodePaket());
                        jobList.add(jobDetail);
                    }

                    Comparator<DetilPekerjaan> allHistoryComparator = Comparator.comparing(DetilPekerjaan::getTanggalAsDate);
                    Collections.sort(jobList, allHistoryComparator);
                    Collections.reverse(jobList);

                    getMvpView().showAllHistory(jobList, total);
                    AppLogger.d("%d paket secara keseluruhan", total);
                    getMvpView().hideLoading();
                }, throwable -> {
                    if (getMvpView().isNetworkConnected()) {
                        getMvpView().showMessage("Terjadi kesalahan! Mohon untuk mengulang kembali.");
                        getMvpView().hideLoading();
                        AppLogger.e(throwable, "Error yang diberikan adalah:");
                    }
                });
    }

    @Override
    public void getTodayHistory(String username) {
        getDataManager().getTodayHistory(username)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(jsonObject -> {
                    ArrayList<DetilPekerjaan> jobList = new ArrayList<>();

                    int total = jsonObject.getInt("total");
                    JSONArray array = jsonObject.getJSONArray("paket");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonJobs = array.getJSONObject(i);
                        DetilPekerjaan jobDetail = new DetilPekerjaan();
                        jobDetail.setStatus(jsonJobs.getString("STATUS"));
                        jobDetail.setKodePaket(jsonJobs.getString("kode"));
                        jobDetail.setNamaPetugas(jsonJobs.getString("nama_petugas"));
                        jobDetail.setJumlahPaket(jsonJobs.getInt("jumlah"));
                        jobDetail.setJenisPaket(jsonJobs.getString("jenis"));
                        jobDetail.setTanggal(jsonJobs.getString("tanggal"));

                        AppLogger.d("%s ditambahkan ke jobList", jobDetail.getKodePaket());
                        jobList.add(jobDetail);
                    }

                    Comparator<DetilPekerjaan> todayHistoryComparator = Comparator.comparing(DetilPekerjaan::getTanggalAsDate);
                    Collections.sort(jobList, todayHistoryComparator);
                    Collections.reverse(jobList);

                    getMvpView().showAllHistory(jobList, total);
                    getMvpView().hideLoading();
                }, throwable -> {
                    if (getMvpView().isNetworkConnected()) {
                        getMvpView().showMessage("Terjadi kesalahan! Mohon untuk mengulang kembali.");
                        getMvpView().hideLoading();
                        AppLogger.e(throwable, "Error yang diberikan adalah:");
                    }
                });
    }


    void setHomeDetail(String username, String zone) {
        if (getMvpView() instanceof HomeFragment) {
            ((HomeFragment) getMvpView()).changeNameDetail(username, zone);
        }
    }

    void setProfileDetail(String username, String zone) {
        if (getMvpView() instanceof ProfileFragment) {
            ((ProfileFragment) getMvpView()).setProfile(username, zone);
        }
    }
}
