package id.ac.its.myits.courier.data.network.model.token;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenRequest {
    private TokenRequest() {}

    public static class RefreshTokenRequest {

        @Expose
        @SerializedName("grant_type")
        private String grantType;

        @Expose
        @SerializedName("refresh_token")
        private String refreshToken;

        @Expose
        @SerializedName("client_id")
        private String clientId;

        @Expose
        @SerializedName("client_secret")
        private String clientSecret;

        public RefreshTokenRequest() {
        }

        public RefreshTokenRequest(String grantType, String refreshToken, String clientId, String clientSecret) {
            setRefreshToken(refreshToken);
            setGrantType(grantType);
            setClientId(clientId);
            setClientSecret(clientSecret);
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public String getGrantType() {
            return grantType;
        }

        public void setGrantType(String grantType) {
            this.grantType = grantType;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

    }

    public static class RevokeTokenRequest {

        @Expose
        @SerializedName("token")
        private String token;

        public RevokeTokenRequest(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RevokeTokenRequest that = (RevokeTokenRequest) o;

            return token.equals(that.token);

        }

        @Override
        public int hashCode() {
            return token.hashCode();
        }
    }
}