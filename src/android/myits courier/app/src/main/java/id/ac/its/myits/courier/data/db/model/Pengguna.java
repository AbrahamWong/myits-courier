package id.ac.its.myits.courier.data.db.model;

public class Pengguna {
    String nama;
    String alamat;
    String nomorTelepon;
    String email;
    RolePengguna peranPengguna;

    public enum RolePengguna {
        PENGIRIM, PENERIMA
    }
}
