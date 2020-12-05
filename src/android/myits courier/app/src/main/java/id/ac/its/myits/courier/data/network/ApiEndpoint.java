package id.ac.its.myits.courier.data.network;

import id.ac.its.myits.courier.BuildConfig;

public final class ApiEndpoint {
    public static final String ENDPOINT_MYITS_USERINFO = BuildConfig.MYITS_URL + "/userinfo";

    public static final String ENDPOINT_TOKEN_SSO = BuildConfig.MYITS_URL + "/token";

    public static final String ENDPOINT_COURIER_DASHBOARD               = BuildConfig.COURIER_API_URL + "/dashboard";

    public static final String ENDPOINT_COURIER_DAFTAR_PAKET            = BuildConfig.COURIER_API_URL + "/monitoring";

    public static final String ENDPOINT_COURIER_DETIL_PAKET_EKSTERNAL   = BuildConfig.COURIER_API_URL + "/eksternal/detail";
    public static final String ENDPOINT_COURIER_DETIL_PAKET_INTERNAL    = BuildConfig.COURIER_API_URL + "/internal/detail";

}
