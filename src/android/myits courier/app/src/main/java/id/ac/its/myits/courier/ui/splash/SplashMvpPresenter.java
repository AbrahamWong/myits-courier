package id.ac.its.myits.courier.ui.splash;

import id.ac.its.myits.courier.ui.base.MvpPresenter;
import id.ac.its.myits.courier.ui.base.MvpView;

public interface SplashMvpPresenter<V extends SplashMvpView & MvpView> extends MvpPresenter<V> {
    void onUserChecking();
}
