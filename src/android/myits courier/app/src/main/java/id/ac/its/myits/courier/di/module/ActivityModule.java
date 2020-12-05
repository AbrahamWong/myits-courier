package id.ac.its.myits.courier.di.module;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import id.ac.its.myits.courier.di.ActivityContext;
import id.ac.its.myits.courier.di.PerActivity;
import id.ac.its.myits.courier.ui.history.HistoryDetailPresenter;
import id.ac.its.myits.courier.ui.history.HistoryMvpPresenter;
import id.ac.its.myits.courier.ui.history.HistoryMvpView;
import id.ac.its.myits.courier.ui.job.JobMvpPresenter;
import id.ac.its.myits.courier.ui.job.JobMvpView;
import id.ac.its.myits.courier.ui.job.JobPresenter;
import id.ac.its.myits.courier.ui.joblist.JobListMvpPresenter;
import id.ac.its.myits.courier.ui.joblist.JobListMvpView;
import id.ac.its.myits.courier.ui.joblist.JobListPresenter;
import id.ac.its.myits.courier.ui.jobstatus.JobStatusMvpPresenter;
import id.ac.its.myits.courier.ui.jobstatus.JobStatusMvpView;
import id.ac.its.myits.courier.ui.jobstatus.JobStatusPresenter;
import id.ac.its.myits.courier.ui.login.LoginMvpPresenter;
import id.ac.its.myits.courier.ui.login.LoginMvpView;
import id.ac.its.myits.courier.ui.login.LoginPresenter;
import id.ac.its.myits.courier.ui.main.MainMvpPresenter;
import id.ac.its.myits.courier.ui.main.MainMvpView;
import id.ac.its.myits.courier.ui.main.MainPresenter;
import id.ac.its.myits.courier.ui.qr.QrMvpPresenter;
import id.ac.its.myits.courier.ui.qr.QrMvpView;
import id.ac.its.myits.courier.ui.qr.QrPresenter;
import id.ac.its.myits.courier.ui.splash.SplashMvpPresenter;
import id.ac.its.myits.courier.ui.splash.SplashMvpView;
import id.ac.its.myits.courier.ui.splash.SplashPresenter;
import id.ac.its.myits.courier.utils.rx.CourierAppScheduler;
import id.ac.its.myits.courier.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {
    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new CourierAppScheduler();
    }

    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(
            LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity

    MainMvpPresenter<MainMvpView> provideMainPresenter(
            MainPresenter<MainMvpView> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    JobMvpPresenter<JobMvpView> provideJobsPresenter(
            JobPresenter<JobMvpView> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    QrMvpPresenter<QrMvpView> provideQrPresenter(
            QrPresenter<QrMvpView> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    HistoryMvpPresenter<HistoryMvpView> provideHistoryDetailPresenter(
            HistoryDetailPresenter<HistoryMvpView> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(
                    SplashPresenter<SplashMvpView> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    JobListMvpPresenter<JobListMvpView> provideJobListPresenter(
                    JobListPresenter<JobListMvpView> presenter){
        return presenter;
    }

    @Provides
    @PerActivity
    JobStatusMvpPresenter<JobStatusMvpView> provideJobStatusPresenter(
                    JobStatusPresenter<JobStatusMvpView> presenter){
        return presenter;
    }
}
