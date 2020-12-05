package id.ac.its.myits.courier.data.network;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

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
    public Observable<JSONObject> getUnitList(String username) {
        return Rx2AndroidNetworking
                .get(ApiEndpoint.ENDPOINT_COURIER_DASHBOARD + "/" + username)
                .setTag(MYITS_USER_TAG)
                .addHeaders(this.getApiHeader().getProtectedApiHeader())
                .build()
                .getJSONObjectObservable();
    }

    @Override
    public Observable<JSONArray> getUnitDetail(String username, int unitId) {
        return Rx2AndroidNetworking
                .get(ApiEndpoint.ENDPOINT_COURIER_DAFTAR_PAKET + String.format(Locale.ENGLISH,"/%s/%d", username, unitId))
                .setTag(MYITS_USER_TAG)
                .addHeaders(this.getApiHeader().getProtectedApiHeader())
                .build()
                .getJSONArrayObservable();
    }

    @Override
    public Observable<JSONArray> getExternalJobDetail(int idEksternal) {
        return Rx2AndroidNetworking
                .get(ApiEndpoint.ENDPOINT_COURIER_DETIL_PAKET_EKSTERNAL + String.format(Locale.ENGLISH,"/%d", idEksternal))
                .setTag(MYITS_USER_TAG)
                .addHeaders(this.getApiHeader().getProtectedApiHeader())
                .build()
                .getJSONArrayObservable();
    }

    @Override
    public Observable<JSONArray> getInternalJobDetail(String kodeInternal) {
        return Rx2AndroidNetworking
                .get(ApiEndpoint.ENDPOINT_COURIER_DETIL_PAKET_INTERNAL + "/" + kodeInternal)
                .setTag(MYITS_USER_TAG)
                .addHeaders(this.getApiHeader().getProtectedApiHeader())
                .build()
                .getJSONArrayObservable();
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
