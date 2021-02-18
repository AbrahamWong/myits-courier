package id.ac.its.myits.courier.data.network.model.courier;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @Expose
    @SerializedName("data")
    private CarakaData userdata;

    public CarakaData getUserdata() {
        return userdata;
    }

    public void setUserdata(CarakaData userdata) {
        this.userdata = userdata;
    }

    public class CarakaData {
        @Expose
        @SerializedName("nama")
        private String username;

        @Expose
        @SerializedName("sub")
        private String sub;

        @Expose
        @SerializedName("id_sso")
        private String idSSO;

        @Expose
        @SerializedName("zona")
        private String zonaCaraka;

        @Expose
        @SerializedName("preferred_username")
        private String preferredUsername;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getSub() {
            return sub;
        }

        public void setSub(String sub) {
            this.sub = sub;
        }

        public String getPreferredUsername() {
            return preferredUsername;
        }

        public void setPreferredUsername(String preferredUsername) {
            this.preferredUsername = preferredUsername;
        }

        public String getIdSSO() {
            return idSSO;
        }

        public void setIdSSO(String idSSO) {
            this.idSSO = idSSO;
        }

        public String getZonaCaraka() {
            return zonaCaraka;
        }

        public void setZonaCaraka(String zonaCaraka) {
            this.zonaCaraka = zonaCaraka;
        }
    }
}
