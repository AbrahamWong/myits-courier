package id.ac.its.myits.courier.ui.joblist;

import id.ac.its.myits.courier.ui.adapter.JobListAdapter;
import id.ac.its.myits.courier.ui.base.MvpView;

public interface JobListMvpView extends MvpView {
    void jobToUnitRetrieved(JobListAdapter adapter);

    void jobFromUnitRetrieved(JobListAdapter adapter);
}
