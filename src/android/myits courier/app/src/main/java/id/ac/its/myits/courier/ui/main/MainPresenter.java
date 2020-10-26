package id.ac.its.myits.courier.ui.main;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.openid.appauth.AuthState;

import java.util.ArrayList;

import javax.inject.Inject;

import id.ac.its.myits.courier.data.DataManager;
import id.ac.its.myits.courier.data.db.model.Paket;
import id.ac.its.myits.courier.data.db.model.Unit;
import id.ac.its.myits.courier.data.db.model.Zona;
import id.ac.its.myits.courier.ui.adapter.MainAdapter;
import id.ac.its.myits.courier.ui.base.BasePresenter;
import id.ac.its.myits.courier.ui.detail.DetailActivity;
import id.ac.its.myits.courier.utils.AuthStateManager;
import id.ac.its.myits.courier.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;


public class MainPresenter  <V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    MainAdapter adapter;

    @Inject
    public MainPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public ArrayList<Unit> getUnit() {
        // API Request untuk meminta daftar unit dari suatu zona.
        // Untuk sekarang, anggap saja hanya ada 1 Zona, yaitu Zona A.

        ArrayList<Unit> array = new ArrayList<Unit>();
        array.add(new Unit(1,
                "Tata Usaha Teknik Informatika",
                "Jl. Teknik Kimia - Gedung Departemen Teknik Informatika, Keputih, Kec. Sukolilo, Kota SBY, Jawa Timur 60117",
                "Tata Usaha",
                "0315939212"));
        array.add(new Unit(2,
                        "Tata Usaha Sistem Informasi",
                        "Keputih, Kec. Sukolilo, Kota SBY, Jawa Timur 60117",
                        "Tata Usaha",
                        "0315999944"));

        ArrayList<Paket> daftarPaketInformatika = new ArrayList<Paket>();
        daftarPaketInformatika.add(new Paket(1001,
                Paket.TipePaket.PENGIRIMAN_EKSTERNAL,
                1,
                "Diantar",
                2.0f,
                "JT1912"));

        ArrayList<Paket> daftarPaketSI = new ArrayList<Paket>();
        daftarPaketSI.add(new Paket(3001,
                Paket.TipePaket.PENGIRIMAN_EKSTERNAL,
                1,
                "Menunggu Penjemputan",
                0.4f,
                "JT1027"));
        daftarPaketSI.add(new Paket(3002,
                Paket.TipePaket.PENJEMPUTAN_INTERNAL,
                1,
                "Menunggu Penjemputan",
                3.0f,
                "JT0471"));


        array.get(0).setDaftarPaket(daftarPaketInformatika);
        array.get(1).setDaftarPaket(daftarPaketSI);

        return array;
    }

    @Override
    public void plugRecycler(final RecyclerView v) {
        adapter = new MainAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());

        adapter.setOnItemClicked(new MainAdapter.MainAdapterCallback() {
            @Override
            public void onItemClicked(Unit unit) {
                getMvpView().openDetailActivity(unit);
            }
        });

        v.setLayoutManager(layoutManager);
        v.setAdapter(adapter);
    }

    @Override
    public void onLoggingOut(AuthStateManager stateManager) {
        AuthState currentState = stateManager.getCurrent();
        AuthState clearedState = new AuthState(currentState.getAuthorizationServiceConfiguration());

        if (currentState.getLastAuthorizationResponse() != null) {
            clearedState.update(currentState.getLastRegistrationResponse());
        }
        stateManager.replace(clearedState);
    }

}
