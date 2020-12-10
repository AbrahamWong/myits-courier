package id.ac.its.myits.courier.data.network.model.interceptor;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANResponse;

import java.io.IOException;

import id.ac.its.myits.courier.BuildConfig;
import id.ac.its.myits.courier.data.DataManager;
import id.ac.its.myits.courier.data.network.ApiHelper;
import id.ac.its.myits.courier.data.network.model.token.TokenRequest;
import id.ac.its.myits.courier.data.network.model.token.TokenResponse;
import id.ac.its.myits.courier.utils.AppLogger;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

    public class RefreshTokenInterceptor implements Interceptor {
        private DataManager dataManager;

        private RefreshTokenErrorListener errorListener;

        public RefreshTokenInterceptor(DataManager dataManager,
                                       RefreshTokenErrorListener listener) {
            this.dataManager = dataManager;
            this.errorListener = listener;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);

            if (response.code() == 401) {

                AppLogger.d("Access token is expired!");
                AppLogger.d("Intercepting request for refresh token");

                ANResponse<TokenResponse> anResponse = refreshToken();

                synchronized (this) {
                    if (anResponse.isSuccess()) {
                        if (anResponse.getOkHttpResponse().code() == 200) {
                            dataManager.setAccessToken(anResponse.getResult().getAccessToken());
                            dataManager.setRefreshToken(anResponse.getResult().getRefreshToken());

                            dataManager.updateApiHeader(anResponse.getResult().getAccessToken());

                            AppLogger.d("Refresh token " + anResponse.getResult().getRefreshToken() );
                            AppLogger.d("Refresh token successful");

                            Request.Builder builder = request.newBuilder();
                            String token = dataManager.getAccessToken();
                            setAuthHeader(builder, token);

                            request = builder.build();

                            return chain.proceed(request);
                        }
                        else if (anResponse.getOkHttpResponse().code() == 400) { //bad request, wrong refresh token
                            AppLogger.d("Error while refreshing token , bad request");
                            errorListener.onError(anResponse.getOkHttpResponse().code());
                        }
                    }
                    else {
                        AppLogger.d("Error while refreshing token, not success");
                        errorListener.onError(anResponse.getOkHttpResponse().code());
                    }

                    AndroidNetworking.forceCancel(ApiHelper.MYITS_USER_TAG);

                }
            }
            return response;
        }

        private void setAuthHeader(Request.Builder builder, String token) {
            if (token != null) {
                builder.header("Authorization", String.format("Bearer %s", token));
            }
        }

        private ANResponse<TokenResponse> refreshToken() {

            String currentRefreshToken = dataManager.getRefreshToken();

            final TokenRequest.RefreshTokenRequest request = new TokenRequest.RefreshTokenRequest();
            request.setRefreshToken(currentRefreshToken);
            AppLogger.d("currentrefreshtoken " + currentRefreshToken);
            request.setGrantType("refresh_token");
            request.setClientId(BuildConfig.CLIENT_ID);
            request.setClientSecret(BuildConfig.CLIENT_SECRET);

            ANResponse<TokenResponse> response = dataManager.doSyncPostRefreshToken(request);

            return response;

        }



        public interface RefreshTokenErrorListener {

            void onError(int code);

         }
}

