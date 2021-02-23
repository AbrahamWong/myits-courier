package id.ac.its.myits.courier.data.network;

import id.ac.its.myits.courier.BuildConfig;

public final class ApiEndpoint {
    public static final String ENDPOINT_MYITS_USERINFO = BuildConfig.MYITS_URL + "/userinfo";

    public static final String ENDPOINT_TOKEN_SSO = BuildConfig.MYITS_URL + "/token";

    public static final String ENDPOINT_COURIER_USERINFO = BuildConfig.COURIER_URL + "/sso/detail";
    public static final String ENDPOINT_COURIER_DASHBOARD = BuildConfig.COURIER_URL + "/dashboard";

    public static final String ENDPOINT_COURIER_RIWAYAT_SEMUA = BuildConfig.COURIER_URL + "/riwayat/semua";
    public static final String ENDPOINT_COURIER_RIWAYAT_HARI_INI = BuildConfig.COURIER_URL + "/riwayat/tanggal";
    public static final String ENDPOINT_COURIER_RIWAYAT_DETIL = BuildConfig.COURIER_URL + "/riwayat/detail";

    public static final String ENDPOINT_COURIER_DAFTAR_PAKET = BuildConfig.COURIER_URL + "/monitoring";

    public static final String ENDPOINT_COURIER_DETIL_PAKET_EKSTERNAL = BuildConfig.COURIER_URL + "/eksternal/detail";
    public static final String ENDPOINT_COURIER_EDIT_PAKET_EKSTERNAL = BuildConfig.COURIER_URL + "/eksternal/status/edit";
    public static final String ENDPOINT_COURIER_DETIL_PAKET_INTERNAL = BuildConfig.COURIER_URL + "/internal/detail";
    public static final String ENDPOINT_COURIER_EDIT_PAKET_INTERNAL = BuildConfig.COURIER_URL + "/internal/status/edit";

    public static final String ENDPOINT_COURIER_DAFTAR_STATUS_EKSTERNAL = BuildConfig.COURIER_URL + "/status/eksternal";
    public static final String ENDPOINT_COURIER_DAFTAR_STATUS_INTERNAL = BuildConfig.COURIER_URL + "/status/internal";

    public static final String ENDPOINT_COURIER_SIMPAN_BUKTI_CARAKA = BuildConfig.COURIER_URL + "/bukti/simpan";
}
