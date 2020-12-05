package id.ac.its.myits.courier.ui.jobstatus;

import javax.inject.Inject;

import id.ac.its.myits.courier.data.DataManager;
import id.ac.its.myits.courier.ui.base.BasePresenter;
import id.ac.its.myits.courier.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;


public class JobStatusPresenter<V extends JobStatusMvpView> extends BasePresenter<V>
        implements JobStatusMvpPresenter<V> {

    @Inject
    public JobStatusPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }
}
