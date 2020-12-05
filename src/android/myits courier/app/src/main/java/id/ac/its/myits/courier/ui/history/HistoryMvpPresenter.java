package id.ac.its.myits.courier.ui.history;

import androidx.recyclerview.widget.RecyclerView;

import id.ac.its.myits.courier.ui.base.MvpPresenter;
import id.ac.its.myits.courier.ui.base.MvpView;

public interface HistoryMvpPresenter<V extends HistoryMvpView & MvpView> extends MvpPresenter<V> {
    void setMvpView(HistoryMvpView v);
    void plugRecycler(RecyclerView rv);
}
