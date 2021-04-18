package id.ac.its.myits.courier.ui.historydetail;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

import id.ac.its.myits.courier.data.DataManager;
import id.ac.its.myits.courier.data.db.model.DetilStatus;
import id.ac.its.myits.courier.ui.adapter.HistoryAdapter;
import id.ac.its.myits.courier.ui.base.BasePresenter;
import id.ac.its.myits.courier.utils.AppLogger;
import id.ac.its.myits.courier.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;


public class HistoryDetailPresenter<V extends HistoryMvpView> extends BasePresenter<V>
        implements HistoryMvpPresenter<V> {

    HistoryMvpView view;

    HistoryAdapter adapter;

    @Inject
    public HistoryDetailPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void initRecycler(RecyclerView rv) {
        adapter = new HistoryAdapter(new ArrayList<>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(rv.getContext(), RecyclerView.VERTICAL, false);

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }

    @Override
    public void plugRecycler(final RecyclerView rv, ArrayList<DetilStatus> historyList) {
        adapter = new HistoryAdapter(historyList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(rv.getContext(), RecyclerView.VERTICAL, false);

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getHistoryDetail(String kodePaket) {
        getMvpView().showLoading(null, null);
        getDataManager().getHistoryDetail(kodePaket)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(jsonObject -> {
                    JSONArray packageArray = jsonObject.getJSONArray("detail_paket");
                    JSONObject packageDetail = packageArray.getJSONObject(0);
                    DetilRiwayat historyDetail = retrieveFromJSON(packageDetail);

                    ArrayList<DetilStatus> historyList = new ArrayList<>();
                    JSONArray history = jsonObject.getJSONArray("riwayat_status");

                    for (int i = 0; i < history.length(); i++) {
                        JSONObject jsonHistory = history.getJSONObject(i);
                        DetilStatus historyTimeline = new DetilStatus();
                        historyTimeline.setDate(jsonHistory.getString("created_at"));
                        historyTimeline.setStatus(jsonHistory.getString("status"));

                        historyList.add(historyTimeline);
                    }

                    getMvpView().showHistoryDetails(historyDetail, historyList);
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
    public void setMvpView(HistoryMvpView v) {
        view = v;
    }

    private DetilRiwayat retrieveFromJSON(JSONObject object) throws JSONException {
        DetilRiwayat history = new DetilRiwayat();
        String jenisPaket;

        history.setId(object.getInt("id"));
        history.setKodePaket(object.getString("kode"));
        history.setNamaPetugas(object.getString("nama_petugas"));
        history.setStatus(object.getString("status_string"));
        history.setBeratMinimal(object.getInt("berat_minimal"));
        history.setBeratMaksimal(object.getInt("berat_maksimal"));
        history.setDeskripsi(object.getString("deskripsi"));
        history.setJumlahPaket(object.getInt("jumlah"));

        jenisPaket = object.getString("jenis");
        history.setJenisPaket(jenisPaket);

        if (jenisPaket.equals("Eksternal")) {
            history.setNamaUnitAsal(null);
            history.setNamaUnitTujuan(object.getString("nama_unit"));
        } else {
            history.setNamaUnitAsal(object.getString("nama_unit_asal"));
            history.setNamaUnitTujuan(object.getString("nama_unit_tujuan"));
        }

        AppLogger.d("Get nama unit: %s ke %s", history.getNamaUnitAsal(), history.getNamaUnitTujuan());

        return history;
    }
}

class DetilRiwayat {
    int id;
    String jenisPaket;
    String kodePaket;
    String namaPetugas;
    String status;
    int beratMinimal, beratMaksimal;
    String deskripsi;
    String namaUnitAsal;
    String namaUnitTujuan;
    int jumlahPaket;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJenisPaket() {
        return jenisPaket;
    }

    public void setJenisPaket(String jenisPaket) {
        this.jenisPaket = jenisPaket;
    }

    public String getKodePaket() {
        return kodePaket;
    }

    public void setKodePaket(String kodePaket) {
        this.kodePaket = kodePaket;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBeratMinimal() {
        return beratMinimal;
    }

    public void setBeratMinimal(int beratMinimal) {
        this.beratMinimal = beratMinimal;
    }

    public int getBeratMaksimal() {
        return beratMaksimal;
    }

    public void setBeratMaksimal(int beratMaksimal) {
        this.beratMaksimal = beratMaksimal;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getJumlahPaket() {
        return jumlahPaket;
    }

    public void setJumlahPaket(int jumlahPaket) {
        this.jumlahPaket = jumlahPaket;
    }

    public String getNamaUnitAsal() {
        return namaUnitAsal;
    }

    public void setNamaUnitAsal(String namaUnitAsal) {
        this.namaUnitAsal = namaUnitAsal;
    }

    public String getNamaUnitTujuan() {
        return namaUnitTujuan;
    }

    public void setNamaUnitTujuan(String namaUnitTujuan) {
        this.namaUnitTujuan = namaUnitTujuan;
    }
}