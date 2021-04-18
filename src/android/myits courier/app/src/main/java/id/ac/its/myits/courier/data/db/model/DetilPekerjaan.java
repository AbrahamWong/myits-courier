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

    // Unit asal dan Unit tujuan
    private String unitAsal;
    private String unitTujuan;

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
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy hh:mm:ss:a", Locale.getDefault());
            return sdf.parse(tanggal);
        } catch (ParseException e) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MMM  d yyyy hh:mm:ss:a", Locale.getDefault());
                return sdf.parse(tanggal);
            } catch (ParseException parseException) {
                parseException.printStackTrace();
                return null;
            }
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

        SimpleDateFormat nsdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy hh:mm:ss:a", Locale.getDefault());

            return String.format("%s, %s - %s",
                    nsdf.format(sdf.parse(tanggalPengiriman)),
//                    jamAwal.substring(0, jamAwal.length() - 3),
//                    jamAkhir.substring(0, jamAkhir.length() - 3));
                    jamAwal,
                    jamAkhir);
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

    public String getUnitAsal() {
        return unitAsal;
    }

    public void setUnitAsal(String unitAsal) {
        this.unitAsal = unitAsal;
    }

    public String getUnitTujuan() {
        return unitTujuan;
    }

    public void setUnitTujuan(String unitTujuan) {
        this.unitTujuan = unitTujuan;
    }
}
