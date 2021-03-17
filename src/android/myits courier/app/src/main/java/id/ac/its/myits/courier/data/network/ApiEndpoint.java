package id.ac.its.myits.courier.data.network;

import id.ac.its.myits.courier.BuildConfig;

public final class ApiEndpoint {
    public static final String ENDPOINT_MYITS_USERINFO = BuildConfig.DEV_MYITS_URL + "/userinfo";

    // Ubah DEV_MYITS_URL ke MYITS_URL untuk production
    public static final String ENDPOINT_TOKEN_SSO = BuildConfig.DEV_MYITS_URL + "/token";

    // Ubah DEV_COURIER_URL ke COURIER_URL untuk production
    public static final String ENDPOINT_COURIER_USERINFO = BuildConfig.DEV_COURIER_URL + "/sso/detail";
    public static final String ENDPOINT_COURIER_DASHBOARD = BuildConfig.DEV_COURIER_URL + "/dashboard";

    public static final String ENDPOINT_COURIER_RIWAYAT_SEMUA = BuildConfig.DEV_COURIER_URL + "/riwayat/semua";
    public static final String ENDPOINT_COURIER_RIWAYAT_HARI_INI = BuildConfig.DEV_COURIER_URL + "/riwayat/tanggal";
    public static final String ENDPOINT_COURIER_RIWAYAT_DETIL = BuildConfig.DEV_COURIER_URL + "/riwayat/detail";

    public static final String ENDPOINT_COURIER_DAFTAR_PAKET = BuildConfig.DEV_COURIER_URL + "/monitoring";

    public static final String ENDPOINT_COURIER_DETIL_PAKET_EKSTERNAL = BuildConfig.DEV_COURIER_URL + "/eksternal/detail";
    public static final String ENDPOINT_COURIER_EDIT_PAKET_EKSTERNAL = BuildConfig.DEV_COURIER_URL + "/eksternal/status/edit";
    public static final String ENDPOINT_COURIER_DETIL_PAKET_INTERNAL = BuildConfig.DEV_COURIER_URL + "/internal/detail";
    public static final String ENDPOINT_COURIER_EDIT_PAKET_INTERNAL = BuildConfig.DEV_COURIER_URL + "/internal/status/edit";

    public static final String ENDPOINT_COURIER_DAFTAR_STATUS_EKSTERNAL = BuildConfig.DEV_COURIER_URL + "/status/eksternal";
    public static final String ENDPOINT_COURIER_DAFTAR_STATUS_INTERNAL = BuildConfig.DEV_COURIER_URL + "/status/internal";

    public static final String ENDPOINT_COURIER_SIMPAN_BUKTI_CARAKA = BuildConfig.DEV_COURIER_URL + "/bukti/simpan";
}
