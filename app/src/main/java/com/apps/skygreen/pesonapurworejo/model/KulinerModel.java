package com.apps.skygreen.pesonapurworejo.model;

public class KulinerModel {
    private String idKuliner;
    private String namaKuliner;
    private String lokasi;
    private String kategori;
    private String icon;
    private String deskripsi;
    private String pengelola;
    private String sosialMedia;

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getPengelola() {
        return pengelola;
    }

    public void setPengelola(String pengelola) {
        this.pengelola = pengelola;
    }

    public String getSosialMedia() {
        return sosialMedia;
    }

    public void setSosialMedia(String sosialMedia) {
        this.sosialMedia = sosialMedia;
    }

    public String getIdKuliner() {
        return idKuliner;
    }

    public void setIdKuliner(String idKuliner) {
        this.idKuliner = idKuliner;
    }

    public String getNamaKuliner() {
        return namaKuliner;
    }

    public void setNamaKuliner(String namaKuliner) {
        this.namaKuliner = namaKuliner;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
