package id.ac.its.myits.courier.data.network;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.interfaces.OkHttpResponseListener;
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
    private final ApiHeader apiHeader;

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
        return Rx2AndroidNetworking.get(ApiEndpoint.ENDPOINT_COURIER_USERINFO)
                .setTag(MYITS_USER_TAG)
                .addHeaders(this.getApiHeader().getProtectedApiHeader())
                .build()
                .getObjectObservable(UserInfo.class);
    }

    @Override
    public Observable<JSONObject> getUnitList() {
        return Rx2AndroidNetworking
                .get(ApiEndpoint.ENDPOINT_COURIER_DASHBOARD)
                .setTag(MYITS_USER_TAG)
                .addHeaders(this.getApiHeader().getProtectedApiHeader())
                .build()
                .getJSONObjectObservable();
    }

    @Override
    public Observable<JSONObject> getAllHistory() {
        return Rx2AndroidNetworking
                .get(ApiEndpoint.ENDPOINT_COURIER_RIWAYAT_SEMUA)
                .setTag(MYITS_USER_TAG)
                .addHeaders(this.getApiHeader().getProtectedApiHeader())
                .build()
                .getJSONObjectObservable();
    }

    @Override
    public Observable<JSONObject> getTodayHistory() {
        return Rx2AndroidNetworking
                .get(ApiEndpoint.ENDPOINT_COURIER_RIWAYAT_HARI_INI)
                .setTag(MYITS_USER_TAG)
                .addHeaders(this.getApiHeader().getProtectedApiHeader())
                .build()
                .getJSONObjectObservable();
    }

    @Override
    public Observable<JSONObject> getHistoryDetail(String kodePaket) {
        return Rx2AndroidNetworking
                .get(ApiEndpoint.ENDPOINT_COURIER_RIWAYAT_DETIL + "/" + kodePaket)
                .setTag(MYITS_USER_TAG)
                .addHeaders(this.getApiHeader().getProtectedApiHeader())
                .build()
                .getJSONObjectObservable();
    }

    @Override
    public Observable<JSONArray> getUnitDetail(int unitId) {
        return Rx2AndroidNetworking
                .get(ApiEndpoint.ENDPOINT_COURIER_DAFTAR_PAKET + String.format(Locale.getDefault(), "/%d", unitId))
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
    public void postExternalJobEdit(int idPaket, String status, OkHttpResponseListener listener) {
        Rx2AndroidNetworking
                .post(ApiEndpoint.ENDPOINT_COURIER_EDIT_PAKET_EKSTERNAL + String.format(Locale.ENGLISH, "/%d", idPaket))
                .addBodyParameter("status", status)
                .setTag(MYITS_USER_TAG)
                .addHeaders(this.getApiHeader().getProtectedApiHeader())
                .build()
                .getAsOkHttpResponse(listener);
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
    public void postInternalJobEdit(String kodePaket, String status, OkHttpResponseListener listener) {
        Rx2AndroidNetworking
                .post(ApiEndpoint.ENDPOINT_COURIER_EDIT_PAKET_INTERNAL + "/" + kodePaket)
                .addBodyParameter("status", status)
                .setTag(MYITS_USER_TAG)
                .addHeaders(this.getApiHeader().getProtectedApiHeader())
                .build()
                .getAsOkHttpResponse(listener);
    }

    @Override
    public Observable<JSONArray> getExternalStatuses() {
        return Rx2AndroidNetworking
                .get(ApiEndpoint.ENDPOINT_COURIER_DAFTAR_STATUS_EKSTERNAL)
                .setTag(MYITS_USER_TAG)
                .addHeaders(this.getApiHeader().getProtectedApiHeader())
                .build()
                .getJSONArrayObservable();
    }

    @Override
    public Observable<JSONArray> getInternalStatuses() {
        return Rx2AndroidNetworking
                .get(ApiEndpoint.ENDPOINT_COURIER_DAFTAR_STATUS_INTERNAL)
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

    @Override
    public void postProofOfDelivery(String kodePaket, String imgBase64, OkHttpResponseListener listener) {
        Rx2AndroidNetworking
                .post(ApiEndpoint.ENDPOINT_COURIER_SIMPAN_BUKTI_CARAKA + "/" + kodePaket)
                .addBodyParameter("foto_bukti", imgBase64)
                .setTag(MYITS_USER_TAG)
                .addHeaders(this.getApiHeader().getProtectedApiHeader())
                .build()
                .getAsOkHttpResponse(listener);
    }
}
