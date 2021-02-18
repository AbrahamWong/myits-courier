package id.ac.its.myits.courier.data.db.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PaketEksternal implements Parcelable {
    @SerializedName("id_paket_eksternal")
    private int idPaket;
    @SerializedName("kode")
    private String nomorResi;
    @SerializedName("nama_petugas")
    private String namaPetugas;

    private int jumlah_paket;

    @SerializedName("status")
    private String status;

    @SerializedName("deskripsi")
    private String deskripsiPaket;

    private String tanggal_terima;

    @SerializedName("berat_minimal")
    private int beratMinimal;

    @SerializedName("berat_maksimal")
    private int beratMaksimal;

    @SerializedName("nama_unit")
    private String namaUnit;

    public PaketEksternal() {
    }

    public PaketEksternal(int idPaket, String nomorResi, String namaPetugas, int jumlah_paket,
                          String status, String deskripsiPaket, String tanggal_terima,
                          int beratMinimal, int beratMaksimal, String namaUnit) {
        this.idPaket = idPaket;
        this.nomorResi = nomorResi;
        this.namaPetugas = namaPetugas;
        this.jumlah_paket = jumlah_paket;
        this.status = status;
        this.deskripsiPaket = deskripsiPaket;
        this.tanggal_terima = tanggal_terima;
        this.beratMinimal = beratMinimal;
        this.beratMaksimal = beratMaksimal;
        this.namaUnit = namaUnit;
    }

    public int getIdPaket() {
        return idPaket;
    }

    public void setIdPaket(int idPaket) {
        this.idPaket = idPaket;
    }

    public String getNomorResi() {
        return nomorResi;
    }

    public void setNomorResi(String nomorResi) {
        this.nomorResi = nomorResi;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
    }

    public int getJumlah_paket() {
        return jumlah_paket;
    }

    public void setJumlah_paket(int jumlah_paket) {
        this.jumlah_paket = jumlah_paket;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeskripsiPaket() {
        return deskripsiPaket;
    }

    public void setDeskripsiPaket(String deskripsiPaket) {
        this.deskripsiPaket = deskripsiPaket;
    }

    public String getTanggal_terima() {
        return tanggal_terima;
    }

    public void setTanggal_terima(String tanggal_terima) {
        this.tanggal_terima = tanggal_terima;
    }

    public int getBeratMinimal() {
        return beratMinimal;
    }

    public void setBeratMinimal(int beratMinimal) {
        this.beratMinimal = beratMinimal;
    }

    public int getBeratMaksimal() {
        return beratMaksimal;
    }

    public void setBeratMaksimal(int beratMaksimal) {
        this.beratMaksimal = beratMaksimal;
    }

    public String getNamaUnit() {
        return namaUnit;
    }

    public void setNamaUnit(String namaUnit) {
        this.namaUnit = namaUnit;
    }

    public static Creator<PaketEksternal> getCREATOR() {
        return CREATOR;
    }

    public PaketEksternal(Parcel in) {
        idPaket = in.readInt();
        nomorResi = in.readString();
        namaPetugas = in.readString();
        jumlah_paket = in.readInt();
        status = in.readString();
        deskripsiPaket = in.readString();
        tanggal_terima = in.readString();
        beratMinimal = in.readInt();
        beratMaksimal = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idPaket);
        dest.writeString(nomorResi);
        dest.writeString(namaPetugas);
        dest.writeInt(jumlah_paket);
        dest.writeString(status);
        dest.writeString(deskripsiPaket);
        dest.writeString(tanggal_terima);
        dest.writeInt(beratMinimal);
        dest.writeInt(beratMaksimal);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PaketEksternal> CREATOR = new Creator<PaketEksternal>() {
        @Override
        public PaketEksternal createFromParcel(Parcel in) {
            return new PaketEksternal(in);
        }

        @Override
        public PaketEksternal[] newArray(int size) {
            return new PaketEksternal[size];
        }
    };
}
