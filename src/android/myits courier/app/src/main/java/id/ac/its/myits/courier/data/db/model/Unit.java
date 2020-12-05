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

    @SerializedName("jumlah")
    private int jumlahPaket;

    public Unit() {
    }

    public Unit(int _id, String namaUnit, String alamat, String tipeUnit, String nomorTelepon, int jumlahPaket) {
        this._id = _id;
        this.namaUnit = namaUnit;
        this.alamat = alamat;
        this.tipeUnit = tipeUnit;
        this.nomorTelepon = nomorTelepon;
        this.jumlahPaket = jumlahPaket;
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

    public int getJumlahPaket() {
        return jumlahPaket;
    }

    public void setJumlahPaket(int jumlahPaket) {
        this.jumlahPaket = jumlahPaket;
    }
}
