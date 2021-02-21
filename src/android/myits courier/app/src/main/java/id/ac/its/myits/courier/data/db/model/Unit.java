package id.ac.its.myits.courier.data.db.model;

import com.google.gson.annotations.SerializedName;

public class Unit {
    @SerializedName("id_unit")
    private int _id;

    @SerializedName("nama_unit")
    private String namaUnit;

    private String alamat;

    @SerializedName("tipe_unit")
    private String tipeUnit;

    @SerializedName("no_telp")
    private String nomorTelepon;

    @SerializedName("jumlah_eksternal")
    private int jumlahPaketEksternal;

    @SerializedName("jumlah_internal_in")
    private int jumlahPaketInternalMasuk;

    @SerializedName("jumlah_internal_out")
    private int jumlahPaketInternalKeluar;

    public Unit() {
    }

    public Unit(int _id, String namaUnit, String alamat, String tipeUnit, String nomorTelepon, int jumlahPaketEksternal) {
        this._id = _id;
        this.namaUnit = namaUnit;
        this.alamat = alamat;
        this.tipeUnit = tipeUnit;
        this.nomorTelepon = nomorTelepon;
        this.jumlahPaketEksternal = jumlahPaketEksternal;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNamaUnit() {
        return namaUnit;
    }

    public void setNamaUnit(String namaUnit) {
        this.namaUnit = namaUnit;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTipeUnit() {
        return tipeUnit;
    }

    public void setTipeUnit(String tipeUnit) {
        this.tipeUnit = tipeUnit;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public int getJumlahPaketEksternal() {
        return jumlahPaketEksternal;
    }

    public void setJumlahPaketEksternal(int jumlahPaketEksternal) {
        this.jumlahPaketEksternal = jumlahPaketEksternal;
    }

    public int getJumlahPaketInternalMasuk() {
        return jumlahPaketInternalMasuk;
    }

    public void setJumlahPaketInternalMasuk(int jumlahPaketInternalMasuk) {
        this.jumlahPaketInternalMasuk = jumlahPaketInternalMasuk;
    }

    public int getJumlahPaketInternalKeluar() {
        return jumlahPaketInternalKeluar;
    }

    public void setJumlahPaketInternalKeluar(int jumlahPaketInternalKeluar) {
        this.jumlahPaketInternalKeluar = jumlahPaketInternalKeluar;
    }

    public int getJumlahPaket() {
        return this.jumlahPaketEksternal + this.jumlahPaketInternalMasuk + this.jumlahPaketInternalKeluar;
    }
}
