package com.conexa.techsupport.models;

public class Task {
    private String id;
    private String namaPelanggan;
    private String kontak;
    private String alamat;
    private String status;
    private String tanggal;
    private String jenis;

    //field aktivasi
    private String paket;

    //field maintenance
    private String issue;

    public Task() {

    }

    // Getter & Setter untuk ID
    public String getId() {
        return id;
    }

    public void setId(String id) { // Tambahkan method ini
        this.id = id;
    }

    public String getNamaPelanggan(){
        return  namaPelanggan;
    }
    public void setNamaPelanggan(String namaPelanggan){
        this.namaPelanggan = namaPelanggan;
    }

    public String getKontak() {
        return kontak;
    }
    public void setKontak(String kontak) {
        this.kontak = kontak;
    }

    public String getAlamat() {
        return alamat;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal() {
        return tanggal;
    }
    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getPaket() {
        return paket;
    }
    public void setPaket(String paket) {
        this.paket = paket;
    }

    public String getIssue() {
        return issue;
    }
    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getJenis() {
        return jenis;
    }
    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    // Helper method untuk mengecek jenis task
    public boolean isAktivasi() {
        return "aktivasi".equals(jenis);
    }

}
