package id.ac.its.myits.courier.data.network;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Inject;
import javax.inject.Singleton;

import id.ac.its.myits.courier.data.network.model.courier.UserInfo;
import id.ac.its.myits.courier.data.network.model.token.TokenRequest;
import id.ac.its.myits.courier.data.network.model.token.TokenResponse;
import io.reactivex.Observable;

@Singleton
public class CourierApiHelper implements ApiHelper{
    private ApiHeader apiHeader;

    @Inject
    public CourierApiHelper(ApiHeader apiHeader) {
        this.apiHeader = apiHeader;
    }

    @Override
    public ApiHeader getApiHeader() {
        return apiHeader;
    }

    @Override
    public Observable<UserInfo> doGetUserInfo() {
        return Rx2AndroidNetworking.get(ApiEndpoint.ENDPOINT_MYITS_USERINFO)
                .setTag(MYITS_USER_TAG)
                .addHeaders(this.getApiHeader().getProtectedApiHeader())
                .build()
                .getObjectObservable(UserInfo.class);
    }

    @Override
    public ANResponse<TokenResponse> doSyncPostRefreshToken(TokenRequest.RefreshTokenRequest request) {
        ANRequest anRequest = AndroidNetworking.post(ApiEndpoint.ENDPOINT_TOKEN_SSO)
                .setContentType("application/x-www-form-urlencoded")
                .addBodyParameter(request)
                .build();

        return anRequest.executeForObject(TokenResponse.class);
    }
}
