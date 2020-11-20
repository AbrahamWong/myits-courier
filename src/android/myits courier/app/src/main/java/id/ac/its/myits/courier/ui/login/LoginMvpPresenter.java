package id.ac.its.myits.courier.ui.login;

import id.ac.its.myits.courier.data.network.model.token.TokenResponse;
import id.ac.its.myits.courier.ui.base.MvpPresenter;
import id.ac.its.myits.courier.ui.base.MvpView;


public interface LoginMvpPresenter <V extends LoginMvpView> extends MvpPresenter<V> {

    void onPersistAccessToken(TokenResponse tokenResponse);

    void onLoginSuccesful();

    void onPersistUser();
}
