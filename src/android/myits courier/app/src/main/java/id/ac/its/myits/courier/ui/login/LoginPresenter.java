package id.ac.its.myits.courier.ui.login;

import javax.inject.Inject;

import id.ac.its.myits.courier.data.DataManager;
import id.ac.its.myits.courier.data.network.model.courier.UserInfo;
import id.ac.its.myits.courier.data.network.model.token.TokenResponse;
import id.ac.its.myits.courier.ui.base.BasePresenter;
import id.ac.its.myits.courier.utils.AppLogger;
import id.ac.its.myits.courier.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class LoginPresenter <V extends LoginMvpView> extends BasePresenter<V>
        implements LoginMvpPresenter<V> {

    @Inject
    public LoginPresenter(DataManager dataManager,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    private UserInfo userInfo;

    @Override
    public void onPersistAccessToken(TokenResponse tokenResponse) {
        getDataManager().setAccessToken(tokenResponse.getAccessToken());
        getDataManager().setRefreshToken(tokenResponse.getRefreshToken());

        //AppLogger.d("accesstoken " + tokenResponse.getAccessToken());
        //AppLogger.d("refreshtoken " + tokenResponse.getRefreshToken());
        getDataManager().updateApiHeader(tokenResponse.getAccessToken());
        onPersistUser();
        getMvpView().openMainActivity();
    }

    @Override
    public void onLoginSuccesful() {
        getMvpView().showLoading(null, null);
        getDataManager().doGetUserInfo()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserInfo>() {
                    @Override
                    public void accept(UserInfo userInfo) throws Exception {
                        AppLogger.d("AppAuthSample " + "userinfo " + userInfo.getUserdata().getPreferredUsername());
                        AppLogger.d("AppAuthSample " + "sub " + userInfo.getUserdata().getSub());
                        onPersistUser();
                        getMvpView().openMainActivity();
                        getMvpView().hideLoading();
                    }
                }, new Consumer<Throwable>(){
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (getMvpView().isNetworkConnected()) {
                            getMvpView().showMessage("Terjadi kesalahan! Mohon untuk mengulang kembali.");
                            getMvpView().hideLoading();
                        }
                    }
                });
    }

    @Override
    public void onPersistUser() {
        setActiveUserId(2020); //ini hardcode
        AppLogger.d("Active user id: %s | %s", getDataManager().getActiveUserId(), getActiveUserId());
    }
}
