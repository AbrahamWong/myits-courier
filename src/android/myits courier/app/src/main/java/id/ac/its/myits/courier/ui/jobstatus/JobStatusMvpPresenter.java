package id.ac.its.myits.courier.ui.jobstatus;

import id.ac.its.myits.courier.ui.base.MvpPresenter;
import id.ac.its.myits.courier.ui.base.MvpView;

public interface JobStatusMvpPresenter<V extends JobStatusMvpView & MvpView> extends MvpPresenter<V> {
    void getExternalStatuses();

    void getInternalStatuses();

    void postExternalStatus(int idPaket, String status);

    void postInternalStatus(String kodePaket, String status);

    String getStatusPrefix();
}
