package id.ac.its.myits.courier.data.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.inject.Inject;
import javax.inject.Singleton;

import id.ac.its.myits.courier.di.ApiKeyInfo;
import id.ac.its.myits.courier.di.AppTokenInfo;
import timber.log.Timber;

@Singleton
public class ApiHeader {

    private ProtectedApiHeader protectedApiHeader;
    private ProtectedApiHeader protectedAppApiHeader;
    private PublicApiHeader publicApiHeader;


    @Inject
    public ApiHeader(PublicApiHeader publicApiHeader, ProtectedApiHeader protectedApiHeader, @AppTokenInfo ProtectedApiHeader protectedAppApiHeader) {
        this.publicApiHeader = publicApiHeader;
        this.protectedApiHeader = protectedApiHeader;
        this.protectedAppApiHeader = protectedAppApiHeader;
    }

    public ProtectedApiHeader getProtectedApiHeader() {
        return protectedApiHeader;
    }

    public PublicApiHeader getPublicApiHeader() {
        return publicApiHeader;
    }

    public ProtectedApiHeader getProtectedAppApiHeader() { return protectedAppApiHeader; }

    public static final class PublicApiHeader {

        @Expose
        @SerializedName("Authorization")
        private String authorization;

        @Inject
        public PublicApiHeader(@ApiKeyInfo String apiKey) {
            this.setAuthorization(apiKey);
        }

        public String getAuthorization() {
            return authorization;
        }

        public void setAuthorization(String apiKey) {
            this.authorization = "Basic " + apiKey;
        }
    }

    public static final class ProtectedApiHeader {

        @SerializedName("Authorization")
        private String authorization;

        public ProtectedApiHeader(String accessToken) {
            setAuthorization(accessToken);
        }

        public String getAuthorization() {
            return authorization;
        }

        public void setAuthorization(String accessToken) {
            this.authorization = "Bearer " + accessToken;
            Timber.tag("Auth").d("setAuthorization: %s", this.authorization);
        }
    }

}
