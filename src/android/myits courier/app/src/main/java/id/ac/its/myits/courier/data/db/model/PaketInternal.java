package id.ac.its.myits.courier.data.db.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PaketInternal implements Parcelable {
    private int idPaket;
    private String kodeInternal;
    private String nama_tu;
    private String status;
    private int berat_minimal;
    private int berat_maksimal;
    private int jumlah_paket;
    private String deskripsi;
    private String namaUnitAsal;
    private String namaUnitTujuan;

    private boolean isBedaZona;

    public PaketInternal() {
    }

    public PaketInternal(int idPaket, String kodeInternal, String nama_tu, String status, int berat_minimal, int berat_maksimal, int jumlah_paket, String deskripsi, String namaUnitAsal, String namaUnitTujuan, boolean isBedaZona) {
        this.idPaket = idPaket;
        this.kodeInternal = kodeInternal;
        this.nama_tu = nama_tu;
        this.status = status;
        this.berat_minimal = berat_minimal;
        this.berat_maksimal = berat_maksimal;
        this.jumlah_paket = jumlah_paket;
        this.deskripsi = deskripsi;
        this.namaUnitAsal = namaUnitAsal;
        this.namaUnitTujuan = namaUnitTujuan;
        this.isBedaZona = isBedaZona;
    }

    protected PaketInternal(Parcel in) {
        idPaket = in.readInt();
        kodeInternal = in.readString();
        nama_tu = in.readString();
        status = in.readString();
        berat_minimal = in.readInt();
        berat_maksimal = in.readInt();
        jumlah_paket = in.readInt();
        deskripsi = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idPaket);
        dest.writeString(kodeInternal);
        dest.writeString(nama_tu);
        dest.writeString(status);
        dest.writeInt(berat_minimal);
        dest.writeInt(berat_maksimal);
        dest.writeInt(jumlah_paket);
        dest.writeString(deskripsi);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PaketInternal> CREATOR = new Creator<PaketInternal>() {
        @Override
        public PaketInternal createFromParcel(Parcel in) {
            return new PaketInternal(in);
        }

        @Override
        public PaketInternal[] newArray(int size) {
            return new PaketInternal[size];
        }
    };

    public int getIdPaket() {
        return idPaket;
    }

    public void setIdPaket(int idPaket) {
        this.idPaket = idPaket;
    }

    public String getKodeInternal() {
        return kodeInternal;
    }

    public void setKodeInternal(String kodeInternal) {
        this.kodeInternal = kodeInternal;
    }

    public String getNama_tu() {
        return nama_tu;
    }

    public void setNama_tu(String nama_tu) {
        this.nama_tu = nama_tu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBerat_minimal() {
        return berat_minimal;
    }

    public void setBerat_minimal(int berat_minimal) {
        this.berat_minimal = berat_minimal;
    }

    public int getBerat_maksimal() {
        return berat_maksimal;
    }

    public void setBerat_maksimal(int berat_maksimal) {
        this.berat_maksimal = berat_maksimal;
    }

    public int getJumlah_paket() {
        return jumlah_paket;
    }

    public void setJumlah_paket(int jumlah_paket) {
        this.jumlah_paket = jumlah_paket;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public static Creator<PaketInternal> getCREATOR() {
        return CREATOR;
    }

    public String getNamaUnitAsal() {
        return namaUnitAsal;
    }

    public void setNamaUnitAsal(String namaUnitAsal) {
        this.namaUnitAsal = namaUnitAsal;
    }

    public String getNamaUnitTujuan() {
        return namaUnitTujuan;
    }

    public void setNamaUnitTujuan(String namaUnitTujuan) {
        this.namaUnitTujuan = namaUnitTujuan;
    }

    public boolean isBedaZona() {
        return isBedaZona;
    }

    public void setBedaZona(boolean bedaZona) {
        isBedaZona = bedaZona;
    }
}
