package id.ac.its.myits.courier.ui.jobstatus;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseListener;

import java.util.ArrayList;

import javax.inject.Inject;

import id.ac.its.myits.courier.data.DataManager;
import id.ac.its.myits.courier.ui.base.BasePresenter;
import id.ac.its.myits.courier.utils.AppLogger;
import id.ac.its.myits.courier.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Response;


public class JobStatusPresenter<V extends JobStatusMvpView> extends BasePresenter<V>
        implements JobStatusMvpPresenter<V> {

    @Inject
    public JobStatusPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    private String statusPrefix;

    public void setStatusPrefix(String statusPrefix) {
        this.statusPrefix = statusPrefix;
    }

    @Override
    public void getExternalStatuses() {
        getDataManager().getExternalStatuses()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(jsonArray -> {
                    ArrayList<String> statuses = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        // Format status telah diketahui, dan diminta untuk menghilangkan 2 kata pertama agar
                        // mengurangi pemakaian kata yang berlebihan dan berulang.
                        String status = jsonArray.getJSONObject(i).getString("status");
                        setStatusPrefix(status.substring(0, status.indexOf(" ", 6)));

                        String newStatus = status.substring(status.indexOf(" ", 6) + 1);
                        newStatus = newStatus.substring(0, 1).toUpperCase() + newStatus.substring(1);
                        statuses.add(newStatus);
                    }

                    getMvpView().onExternalListGet(statuses);
                }, throwable -> {
                    if (getMvpView().isNetworkConnected()) {
                        AppLogger.d("Telah terjadi kesalahan");
                        getMvpView().showMessage("Terjadi kesalahan! Mohon untuk mengulang kembali.");
                        getMvpView().hideLoading();
                    }
                });
    }

    @Override
    public void getInternalStatuses() {
        getDataManager().getInternalStatuses()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(jsonArray -> {
                    ArrayList<String> statuses = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        // Format status telah diketahui, dan diminta untuk menghilangkan 2 kata pertama agar
                        // mengurangi pemakaian kata yang berlebihan dan berulang.
                        String status = jsonArray.getJSONObject(i).getString("status");
                        setStatusPrefix(status.substring(0, status.indexOf(" ", 6)));

                        String newStatus = status.substring(status.indexOf(" ", 6) + 1);
                        newStatus = newStatus.substring(0, 1).toUpperCase() + newStatus.substring(1);
                        statuses.add(newStatus);
                    }

                    getMvpView().onInternalListGet(statuses);
                }, throwable -> {
                    if (getMvpView().isNetworkConnected()) {
                        AppLogger.d("Telah terjadi kesalahan");
                        getMvpView().showMessage("Terjadi kesalahan! Mohon untuk mengulang kembali.");
                        getMvpView().hideLoading();
                    }
                });
    }

    @Override
    public void postExternalStatus(int idPaket, String status) {
        getDataManager().postExternalJobEdit(idPaket, status, new OkHttpResponseListener() {
            @Override
            public void onResponse(Response response) {
                if (response.code() == 200) {
                    AppLogger.d("%d says: I approb", response.code());
                    AppLogger.d("kode paket: %d\nStatus: %s", idPaket, status);
                }
            }

            @Override
            public void onError(ANError anError) {
                getMvpView().showMessage("Terjadi kesalahan jaringan.");
            }
        });
    }

    @Override
    public void postInternalStatus(String kodePaket, String status) {
        getDataManager().postInternalJobEdit(kodePaket, status, new OkHttpResponseListener() {
            @Override
            public void onResponse(Response response) {
                if (response.code() == 200) {
                    AppLogger.d("%d says: I approb", response.code());
                }
            }

            @Override
            public void onError(ANError anError) {
                getMvpView().showMessage("Terjadi kesalahan jaringan.");
            }
        });
    }

    @Override
    public ArrayList<RadioButton> createRadioButtons(ArrayList<String> statuses, RadioGroup rg) {
        ArrayList<RadioButton> arb = new ArrayList<>();
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 4, 0, 12);

        for (String status : statuses) {
            RadioButton rb = getMvpView().onRadioButtonRequested();
            rb.setText(status);
            rb.setLayoutParams(params);

            arb.add(rb);
            rg.addView(rb);
        }

        return arb;
    }
}
