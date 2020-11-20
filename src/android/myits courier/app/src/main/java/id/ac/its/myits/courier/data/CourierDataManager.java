package id.ac.its.myits.courier.data;

import android.content.Context;

import com.androidnetworking.common.ANResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import id.ac.its.myits.courier.data.network.ApiHeader;
import id.ac.its.myits.courier.data.network.ApiHelper;
import id.ac.its.myits.courier.data.network.model.courier.UserInfo;
import id.ac.its.myits.courier.data.network.model.token.TokenRequest;
import id.ac.its.myits.courier.data.network.model.token.TokenResponse;
import id.ac.its.myits.courier.data.pref.PreferencesHelper;
import id.ac.its.myits.courier.di.ApplicationContext;
import io.reactivex.Observable;

@Singleton
public class CourierDataManager implements DataManager {
    private static final String TAG = "AppDataManager";

    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;
    @Inject
    public CourierDataManager(@ApplicationContext Context context,
                              PreferencesHelper preferencesHelper,
                              ApiHelper apiHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public void updateApiHeader(String accessToken) {
        mApiHelper.getApiHeader().getProtectedApiHeader().setAuthorization(accessToken);
    }

    @Override
    public void setUserAsLoggedOut() {
        updateApiHeader(null);
        mPreferencesHelper.clearSharedPreferences();
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
    }

    @Override
    public String getRefreshToken() {
        return mPreferencesHelper.getRefreshToken();
    }

    @Override
    public void setRefreshToken(String refreshToken) {
        mPreferencesHelper.setRefreshToken(refreshToken);
    }

    @Override
    public String getTokenType() {
        return mPreferencesHelper.getTokenType();
    }

    @Override
    public void setTokenType(String tokenType) {
        mPreferencesHelper.setTokenType(tokenType);
    }

    @Override
    public Long getTokenExpiration() {
        return mPreferencesHelper.getTokenExpiration();
    }

    @Override
    public void setTokenExpiration(Long expiresIn) {
        mPreferencesHelper.setTokenExpiration(expiresIn);
    }

    @Override
    public Long getTokenTimestamp() {
        return mPreferencesHelper.getTokenTimestamp();
    }

    @Override
    public void setTokenTimestamp(Long timestamp) {
        mPreferencesHelper.setTokenTimestamp(timestamp);
    }

    @Override
    public String getScope() {
        return mPreferencesHelper.getScope();
    }

    @Override
    public void setScope(String scope) {
        mPreferencesHelper.setScope(scope);
    }

    @Override
    public String getUsersInJson() {
        return null;
    }

    @Override
    public void setUsersInJson(String users) {
        mPreferencesHelper.setUsersInJson(users);
    }


    @Override
    public Integer getActiveUserId() {
        return mPreferencesHelper.getActiveUserId();
    }

    @Override
    public void setActiveUserId(Integer id) {
        mPreferencesHelper.setActiveUserId(id);
    }

    @Override
    public UserProfile getActiveUserProfile() {
        return mPreferencesHelper.getActiveUserProfile();
    }

    @Override
    public String getUsername() {
        return mPreferencesHelper.getUsername();
    }

    @Override
    public void setUsername(String username) {
        mPreferencesHelper.setUsername(username);
    }

    @Override
    public String getPassword() {
        return mPreferencesHelper.getPassword();
    }

    @Override
    public void setPassword(String password) {
        mPreferencesHelper.setPassword(password);
    }


    @Override
    public void clearSharedPreferences() {
        mPreferencesHelper.clearSharedPreferences();
    }


    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public Observable<UserInfo> doGetUserInfo() {
        return mApiHelper.doGetUserInfo();
    }

    @Override
    public ANResponse<TokenResponse> doSyncPostRefreshToken(TokenRequest.RefreshTokenRequest request) {
        return mApiHelper.doSyncPostRefreshToken(request);
    }
}
