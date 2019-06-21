package com.example.kisar.sqlite_veritabaniislemleri;

/**
 * Created by kisar on 18.06.2019.
 */

public class Ders {

    private long dersID;
    private String dersAdi;
    private int hedefSoruSayis;

    public Ders() {

    }

    public Ders(String dersAdi, int hedefSoruSayis, long dersID) {
        this.setDersAdi(dersAdi);
        this.setHedefSoruSayis(hedefSoruSayis);
        this.setDersID(dersID);
    }

    public String getDersAdi() {
        return dersAdi;
    }

    public void setDersAdi(String dersAdi) {
        this.dersAdi = dersAdi;
    }

    public int getHedefSoruSayis() {
        return hedefSoruSayis;
    }

    public void setHedefSoruSayis(int hedefSoruSayis) {
        this.hedefSoruSayis = hedefSoruSayis;
    }

    public long getDersID() {
        return dersID;
    }

    public void setDersID(long dersID) {
        this.dersID = dersID;
    }
}
