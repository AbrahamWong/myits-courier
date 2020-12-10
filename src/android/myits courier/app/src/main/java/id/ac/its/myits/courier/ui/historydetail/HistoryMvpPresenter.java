package id.ac.its.myits.courier.ui.historydetail;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.its.myits.courier.data.db.model.DetilStatus;
import id.ac.its.myits.courier.ui.base.MvpPresenter;
import id.ac.its.myits.courier.ui.base.MvpView;

public interface HistoryMvpPresenter<V extends HistoryMvpView & MvpView> extends MvpPresenter<V> {
    void setMvpView(HistoryMvpView v);
    void initRecycler(RecyclerView rv);
    void plugRecycler(RecyclerView rv, ArrayList<DetilStatus> historyList);

    void getHistoryDetail(String kodePaket);
}
