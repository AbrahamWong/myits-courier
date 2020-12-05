package id.ac.its.myits.courier.ui.history;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;

import id.ac.its.myits.courier.data.DataManager;
import id.ac.its.myits.courier.data.db.model.DetilStatus;
import id.ac.its.myits.courier.ui.adapter.HistoryAdapter;
import id.ac.its.myits.courier.ui.base.BasePresenter;
import id.ac.its.myits.courier.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;


public class HistoryDetailPresenter<V extends HistoryMvpView> extends BasePresenter<V>
        implements HistoryMvpPresenter<V> {

    HistoryMvpView view;

    // SELECT * FROM tabel_riwayat WHERE id = @SELECTED_ID;
    ArrayList<DetilStatus> daftarStatus = new ArrayList<>();
    HistoryAdapter adapter;

    // const in Java
    private static final int CAMERA_REQUEST_CODE = 101;

    @Inject
    public HistoryDetailPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void plugRecycler(final RecyclerView rv) {
        setDummyData();

        adapter = new HistoryAdapter(daftarStatus);
        LinearLayoutManager layoutManager = new LinearLayoutManager(rv.getContext(), RecyclerView.VERTICAL, false);

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }

    @Override
    public void setMvpView(HistoryMvpView v) {
        view = v;
    }

    public void setDummyData() {
        daftarStatus.add(new DetilStatus("Paket sedang diantar", "2020-11-10 10:00"));
        daftarStatus.add(new DetilStatus("Paket sedang diantar", "2020-11-10 13:00"));
        daftarStatus.add(new DetilStatus("Paket sedang diantar", "2020-11-10 15:00"));
        daftarStatus.add(new DetilStatus("Item successfully delivered", "2020-11-10 16:00"));
    }
}
