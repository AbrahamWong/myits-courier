package id.ac.its.myits.courier.data.network;

import com.androidnetworking.common.ANResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import id.ac.its.myits.courier.data.network.model.courier.UserInfo;
import id.ac.its.myits.courier.data.network.model.token.TokenRequest;
import id.ac.its.myits.courier.data.network.model.token.TokenResponse;
import io.reactivex.Observable;

public interface ApiHelper {

    public static final String MYITS_USER_TAG = "user_myits";

    ApiHeader getApiHeader();

    Observable<UserInfo> doGetUserInfo();

    Observable<JSONObject> getUnitList(String username);

    Observable<JSONArray> getUnitDetail(String username, int unitId);

    Observable<JSONArray> getExternalJobDetail(int idEksternal);
    Observable<JSONArray> getInternalJobDetail(String kodeInternal);

    ANResponse<TokenResponse> doSyncPostRefreshToken(TokenRequest.RefreshTokenRequest request);
}
