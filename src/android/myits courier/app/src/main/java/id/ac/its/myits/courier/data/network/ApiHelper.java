package id.ac.its.myits.courier.data.network;

import com.androidnetworking.common.ANResponse;
import com.androidnetworking.interfaces.OkHttpResponseListener;

import org.json.JSONArray;
import org.json.JSONObject;

import id.ac.its.myits.courier.data.network.model.courier.UserInfo;
import id.ac.its.myits.courier.data.network.model.token.TokenRequest;
import id.ac.its.myits.courier.data.network.model.token.TokenResponse;
import io.reactivex.Observable;

public interface ApiHelper {

    String MYITS_USER_TAG = "user_myits";

    ApiHeader getApiHeader();

    Observable<UserInfo> doGetUserInfo();

    Observable<JSONObject> getUnitList(String username);

    Observable<JSONObject> getAllHistory(String username);
    Observable<JSONObject> getTodayHistory(String username);
    Observable<JSONObject> getHistoryDetail(String kodePaket);

    Observable<JSONArray> getUnitDetail(String username, int unitId);

    Observable<JSONArray> getExternalJobDetail(int idEksternal);

    void postExternalJobEdit(int idPaket, String status, OkHttpResponseListener listener);

    Observable<JSONArray> getInternalJobDetail(String kodeInternal);

    void postInternalJobEdit(String kodePaket, String status, OkHttpResponseListener listener);

    Observable<JSONArray> getExternalStatuses();

    Observable<JSONArray> getInternalStatuses();

    ANResponse<TokenResponse> doSyncPostRefreshToken(TokenRequest.RefreshTokenRequest request);

    void postProofOfDelivery(String kodePaket, String imgBase64, OkHttpResponseListener listener);
}
