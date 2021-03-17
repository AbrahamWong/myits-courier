package id.ac.its.myits.courier.data.db.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetilPekerjaan {
    private int idPaket;
    private String status;
    private String jenisPaket;
    private String kodePaket;
    private int jumlahPaket;
    private String namaPetugas;
    private String deskripsi;
    private String tanggal;

    // Khusus untuk daftar pekerjaan
    private String tanggalPengiriman;
    private String jamAwal;
    private String jamAkhir;
    private int terlambat;

    public DetilPekerjaan() {
    }

    public int getIdPaket() {
        return idPaket;
    }

    public void setIdPaket(int idPaket) {
        this.idPaket = idPaket;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJenisPaket() {
        return jenisPaket;
    }

    public void setJenisPaket(String jenisPaket) {
        this.jenisPaket = jenisPaket;
    }

    public String getKodePaket() {
        return kodePaket;
    }

    public void setKodePaket(String kodePaket) {
        this.kodePaket = kodePaket;
    }

    public int getJumlahPaket() {
        return jumlahPaket;
    }

    public void setJumlahPaket(int jumlahPaket) {
        this.jumlahPaket = jumlahPaket;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public Date getTanggalAsDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return sdf.parse(tanggal);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Khusus untuk daftar pekerjaan
    public String getTanggalPengiriman() {
        return tanggalPengiriman;
    }

    public void setTanggalPengiriman(String tanggalPengiriman) {
        this.tanggalPengiriman = tanggalPengiriman;
    }

    public String getJamAwal() {
        return jamAwal;
    }

    public void setJamAwal(String jamAwal) {
        this.jamAwal = jamAwal;
    }

    public String getJamAkhir() {
        return jamAkhir;
    }

    public void setJamAkhir(String jamAkhir) {
        this.jamAkhir = jamAkhir;
    }

    public String getPackageShift() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat nsdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

            return String.format("%s, %s - %s",
                    nsdf.format(sdf.parse(tanggalPengiriman)),
                    jamAwal.substring(0, jamAwal.length() - 3),
                    jamAkhir.substring(0, jamAkhir.length() - 3));
        } catch (ParseException pe) {
            pe.printStackTrace();
            return null;
        }
    }

    public int isTerlambat() {
        return terlambat;
    }

    public void setTerlambat(int terlambat) {
        this.terlambat = terlambat;
    }
}
