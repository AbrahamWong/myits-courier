package id.ac.its.myits.courier.ui.joblist;

import androidx.recyclerview.widget.RecyclerView;

import id.ac.its.myits.courier.ui.base.MvpPresenter;
import id.ac.its.myits.courier.ui.base.MvpView;

public interface JobListMvpPresenter<V extends JobListMvpView & MvpView> extends MvpPresenter<V> {
    void initRecyclerView(RecyclerView rv);

    void getUnitDetails(int unitId);
}
