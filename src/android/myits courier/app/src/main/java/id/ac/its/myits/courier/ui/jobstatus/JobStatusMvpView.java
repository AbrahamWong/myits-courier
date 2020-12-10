package id.ac.its.myits.courier.ui.jobstatus;

import java.util.ArrayList;

import id.ac.its.myits.courier.ui.base.MvpView;

public interface JobStatusMvpView extends MvpView {
    void onExternalListGet(ArrayList<String> status);
    void onInternalListGet(ArrayList<String> status);
}
