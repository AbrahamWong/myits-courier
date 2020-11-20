package id.ac.its.myits.courier.data.network;

import com.androidnetworking.common.ANResponse;

import id.ac.its.myits.courier.data.network.model.token.TokenRequest;
import id.ac.its.myits.courier.data.network.model.token.TokenResponse;
import io.reactivex.Observable;

import id.ac.its.myits.courier.data.network.model.courier.UserInfo;

public interface ApiHelper {

    public static final String MYITS_USER_TAG = "user_myits";

    ApiHeader getApiHeader();

    Observable<UserInfo> doGetUserInfo();

    ANResponse<TokenResponse> doSyncPostRefreshToken(TokenRequest.RefreshTokenRequest request);
}
