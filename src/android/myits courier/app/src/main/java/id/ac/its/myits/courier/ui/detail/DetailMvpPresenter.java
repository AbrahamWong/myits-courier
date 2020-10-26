package id.ac.its.myits.courier.ui.detail;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.its.myits.courier.data.db.model.Paket;
import id.ac.its.myits.courier.ui.base.MvpPresenter;
import id.ac.its.myits.courier.ui.base.MvpView;

public interface DetailMvpPresenter <V extends DetailMvpView & MvpView> extends MvpPresenter<V> {
    void plugRecycler(RecyclerView rv);

    void onIntentRetrieved();
    ArrayList<Paket> onPaketRequested();
    String getUnitName();
}
