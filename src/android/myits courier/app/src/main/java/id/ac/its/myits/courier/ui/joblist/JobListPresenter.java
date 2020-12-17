package id.ac.its.myits.courier.ui.joblist;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

import id.ac.its.myits.courier.data.DataManager;
import id.ac.its.myits.courier.data.db.model.DetilPekerjaan;
import id.ac.its.myits.courier.ui.adapter.JobListAdapter;
import id.ac.its.myits.courier.ui.base.BasePresenter;
import id.ac.its.myits.courier.utils.AppLogger;
import id.ac.its.myits.courier.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;


public class JobListPresenter<V extends JobListMvpView> extends BasePresenter<V>
        implements JobListMvpPresenter<V> {

    @Inject
    public JobListPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void initRecyclerView(RecyclerView rv) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(rv.getContext());

        JobListAdapter adapter = new JobListAdapter(new ArrayList<>());

        rv.setAdapter(adapter);
        rv.setLayoutManager(layoutManager);
    }

    @Override
    public void getUnitDetails(String username, int unitId) {
        getMvpView().showLoading(null, null);
        getDataManager().getUnitDetail(username, unitId).subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(jsonArray -> {
                    ArrayList<DetilPekerjaan> jobList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        DetilPekerjaan jobDetail = new DetilPekerjaan();
                        jobDetail.setIdPaket(jsonObject.getInt("id_paket"));
                        jobDetail.setStatus(jsonObject.getString("status"));
                        jobDetail.setKodePaket(jsonObject.getString("kode"));
                        jobDetail.setNamaPetugas(jsonObject.getString("nama_petugas"));
                        jobDetail.setJumlahPaket(jsonObject.getInt("jumlah"));
                        jobDetail.setJenisPaket(jsonObject.getString("jenis"));

                        jobList.add(jobDetail);
                    }
                    JobListAdapter adapter = new JobListAdapter(jobList);
                    getMvpView().onDetailRetrieved(adapter);
                    getMvpView().hideLoading();

                }, throwable -> {
                    if (getMvpView().isNetworkConnected()) {
                        AppLogger.e(throwable, "Telah terjadi kesalahan");
                        getMvpView().showMessage("Terjadi kesalahan! Mohon untuk mengulang kembali.");
                        getMvpView().hideLoading();
                    }
                });
    }
}
