package com.example.kisar.sqlite_veritabaniislemleri;

/**
 * Created by kisar on 16.06.2019.
 */

public class Ogrenci {
    private long OgrenciID;
    private String dersAdi;
    private int soruSayisi;
    private long tarih;

    public Ogrenci() {

    }

    public Ogrenci(String dersAdi, int soruSayisi, long tarih,long ogrenciID) {
        this.setDersAdi(dersAdi);
        this.setSoruSayisi(soruSayisi);
        this.setTarih(tarih);
        this.setOgrenciID(ogrenciID);
    }

    public String getDersAdi() {
        return dersAdi;
    }

    public void setDersAdi(String dersAdi) {
        this.dersAdi = dersAdi;
    }

    public int getSoruSayisi() {
        return soruSayisi;
    }

    public void setSoruSayisi(int soruSayisi) {
        this.soruSayisi = soruSayisi;
    }

    public long getTarih() {
        return tarih;
    }

    public void setTarih(long tarih) {
        this.tarih = tarih;
    }

    public long getOgrenciID() {
        return OgrenciID;
    }

    public void setOgrenciID(long ogrenciID) {
        OgrenciID = ogrenciID;
    }
}
