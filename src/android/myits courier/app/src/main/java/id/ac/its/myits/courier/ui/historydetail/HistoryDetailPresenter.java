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
                        historyTimeline.setStatus(jsonHistory.getString("riwayat_status"));

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

        history.setId(object.getInt("id"));
        history.setKodePaket(object.getString("kode"));
        history.setNamaPetugas(object.getString("nama_petugas"));
        history.setStatus(object.getString("status"));
        history.setBeratMinimal(object.getInt("berat_minimal"));
        history.setBeratMaksimal(object.getInt("berat_maksimal"));
        history.setDeskripsi(object.getString("deskripsi"));
        history.setJumlahPaket(object.getInt("jumlah"));
        history.setJenisPaket(object.getString("jenis"));
        history.setNamaUnit(object.getString("nama_unit"));
        AppLogger.d("Get nama unit: %s", history.getNamaUnit());

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
    String namaUnit;
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

    public String getNamaUnit() {
        return namaUnit;
    }

    public void setNamaUnit(String namaUnit) {
        this.namaUnit = namaUnit;
    }

    public int getJumlahPaket() {
        return jumlahPaket;
    }

    public void setJumlahPaket(int jumlahPaket) {
        this.jumlahPaket = jumlahPaket;
    }
}