package id.ac.its.myits.courier.data.db.model;

public class DetilPekerjaan {
    private int idPaket;
    private String status;
    private String jenisPaket;
    private String kodePaket;
    private int jumlahPaket;
    private String namaPetugas;
    private String deskripsi;
    private String tanggal;

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
}
