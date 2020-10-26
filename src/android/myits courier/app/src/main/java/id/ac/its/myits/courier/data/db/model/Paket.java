package id.ac.its.myits.courier.data.db.model;

import com.google.gson.annotations.SerializedName;

public class Paket {
    @SerializedName("id_paket")
    int _id;
    TipePaket tipePaket;
    int prioritas;
    @SerializedName("status_paket")
    String statusPaket;
    Float berat;
    @SerializedName("nomor_resi")
    String nomorResi;

    public enum TipePaket {
        PENGIRIMAN_EKSTERNAL("Pengiriman Eksternal"),
        PENGIRIMAN_INTERNAL("Pengiriman Internal"),
        PENJEMPUTAN_INTERNAL("Penjemputan Internal");

        private String name;

        TipePaket(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public Paket(int _id, TipePaket tipePaket, int prioritas, String statusPaket, Float berat, String nomorResi) {
        this._id = _id;
        this.tipePaket = tipePaket;
        this.prioritas = prioritas;
        this.statusPaket = statusPaket;
        this.berat = berat;
        this.nomorResi = nomorResi;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public TipePaket getTipePaket() {
        return tipePaket;
    }

    public void setTipePaket(TipePaket tipePaket) {
        this.tipePaket = tipePaket;
    }

    public int getPrioritas() {
        return prioritas;
    }

    public void setPrioritas(int prioritas) {
        this.prioritas = prioritas;
    }

    public String getStatusPaket() {
        return statusPaket;
    }

    public void setStatusPaket(String statusPaket) {
        this.statusPaket = statusPaket;
    }

    public Float getBerat() {
        return berat;
    }

    public void setBerat(Float berat) {
        this.berat = berat;
    }

    public String getNomorResi() {
        return nomorResi;
    }

    public void setNomorResi(String nomorResi) {
        this.nomorResi = nomorResi;
    }
}
