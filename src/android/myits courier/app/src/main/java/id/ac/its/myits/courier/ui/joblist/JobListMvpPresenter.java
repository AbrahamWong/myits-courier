package id.ac.its.myits.courier.ui.joblist;

import id.ac.its.myits.courier.ui.base.MvpPresenter;
import id.ac.its.myits.courier.ui.base.MvpView;

public interface JobListMvpPresenter<V extends JobListMvpView & MvpView> extends MvpPresenter<V> {
    void getUnitDetails(String username, int unitId);
}
