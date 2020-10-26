package id.ac.its.myits.courier.data.db.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Unit {
    @SerializedName("id_unit") int _id;
    @SerializedName("nama_unit") String namaUnit;
    String alamat;
    @SerializedName("tipe_unit") String tipeUnit;
    @SerializedName("no_telp") String nomorTelepon;

    ArrayList<Paket> daftarPaket;

    public Unit(int _id, String namaUnit, String alamat, String tipeUnit, String nomorTelepon) {
        this._id = _id;
        this.namaUnit = namaUnit;
        this.alamat = alamat;
        this.tipeUnit = tipeUnit;
        this.nomorTelepon = nomorTelepon;
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

    public ArrayList<Paket> getDaftarPaket() {
        return daftarPaket;
    }

    public void setDaftarPaket(ArrayList<Paket> daftarPaket) {
        this.daftarPaket = daftarPaket;
    }
}
