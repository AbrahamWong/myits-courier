package id.ac.its.myits.courier.ui.main;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.its.myits.courier.data.db.model.Unit;
import id.ac.its.myits.courier.ui.base.MvpPresenter;
import id.ac.its.myits.courier.ui.base.MvpView;

public interface MainMvpPresenter <V extends MainMvpView & MvpView> extends MvpPresenter<V> {
    ArrayList<Unit> getUnit();

    void plugRecycler(RecyclerView v);

    void onLoggingOut();

    void test();
}
