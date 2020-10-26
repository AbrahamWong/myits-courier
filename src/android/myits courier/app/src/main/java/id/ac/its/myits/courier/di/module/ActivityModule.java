package id.ac.its.myits.courier.di.module;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import id.ac.its.myits.courier.di.ActivityContext;
import id.ac.its.myits.courier.di.PerActivity;
import id.ac.its.myits.courier.ui.detail.DetailMvpPresenter;
import id.ac.its.myits.courier.ui.detail.DetailMvpView;
import id.ac.its.myits.courier.ui.detail.DetailPresenter;
import id.ac.its.myits.courier.ui.login.LoginMvpPresenter;
import id.ac.its.myits.courier.ui.login.LoginMvpView;
import id.ac.its.myits.courier.ui.login.LoginPresenter;
import id.ac.its.myits.courier.ui.main.MainMvpPresenter;
import id.ac.its.myits.courier.ui.main.MainMvpView;
import id.ac.its.myits.courier.ui.main.MainPresenter;
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
    DetailMvpPresenter<DetailMvpView> provideDetailPresenter(
            DetailPresenter<DetailMvpView> presenter){
        return presenter;
    }
}
