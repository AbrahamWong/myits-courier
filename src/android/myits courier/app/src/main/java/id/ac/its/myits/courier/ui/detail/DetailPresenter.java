package id.ac.its.myits.courier.ui.detail;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import javax.inject.Inject;

import id.ac.its.myits.courier.data.DataManager;
import id.ac.its.myits.courier.data.db.model.Paket;
import id.ac.its.myits.courier.data.db.model.Unit;
import id.ac.its.myits.courier.ui.adapter.DetailAdapter;
import id.ac.its.myits.courier.ui.adapter.MainAdapter;
import id.ac.its.myits.courier.ui.base.BasePresenter;
import id.ac.its.myits.courier.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

public class DetailPresenter <V extends DetailMvpView> extends BasePresenter<V>
        implements DetailMvpPresenter<V> {

    private Unit unit;

    DetailAdapter adapter;

    // Nanti diupdate kalau ada paket masuk lagi
    // private ArrayList<Paket> daftarPaket;

    @Inject
    public DetailPresenter (DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void plugRecycler(RecyclerView rv) {
        adapter = new DetailAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(rv.getContext());

//        adapter.setOnItemClicked(new MainAdapter.MainAdapterCallback() {
//            @Override
//            public void onItemClicked(Unit unit) {
//                getMvpView().openDetailActivity(unit);
//            }
//        });

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }

    @Override
    public void onIntentRetrieved() {
        Gson gson = new Gson();

        String unitJson = getMvpView().retrieveFromIntent();
        unit = gson.fromJson(unitJson, Unit.class);
    }

    @Override
    public ArrayList<Paket> onPaketRequested() {
        return unit.getDaftarPaket();
    }

    public String getUnitName() {
        return unit.getNamaUnit();
    }


}
