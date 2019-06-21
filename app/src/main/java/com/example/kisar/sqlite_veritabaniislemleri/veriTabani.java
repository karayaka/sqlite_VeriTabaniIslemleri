package com.example.kisar.sqlite_veritabaniislemleri;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kisar on 16.06.2019.
 */

public class veriTabani extends SQLiteOpenHelper {
    //veritabanı özelikleri
    private static final String VERITABANI_ISMI="veritabani";
    private static final int VERITABANI_VERION=3;
    private static final String TABLO_ADİ="derstakip";
    private static final String TABLO_ADİ2="ders";
    //tablo kolanları isimleri

    private static final String KAYITID="_id";
    private static final String ID="_id";
    private static final String KDERS="_ders";
    private static final String DERS="_ders";
    private static final String SORUSAYISI="_sorusayisi";
    private static final String HEDEFSORUSAYISI="_sorusayisi";
    private static final String TARIH="_tarih";


    public veriTabani(Context context) {
        super(context, VERITABANI_ISMI, null, VERITABANI_VERION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tablo_olustur="CREATE TABLE "+TABLO_ADİ+" ("+KAYITID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                DERS+" TEXT, "+
                SORUSAYISI+" INTEGER NOT NULL,"+
                TARIH+" INTEGER NOT NULL);";


        String tablo_olustur2="CREATE TABLE "+TABLO_ADİ2+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                KDERS+" TEXT, "+
                HEDEFSORUSAYISI+" INTEGER NOT NULL);";

        db.execSQL(tablo_olustur);
        db.execSQL(tablo_olustur2);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLO_ADİ);
        db.execSQL("DROP TABLE IF EXISTS "+TABLO_ADİ2);

        onCreate(db);
    }

    public long kayıtEkle(Ogrenci ogrenci) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(DERS,ogrenci.getDersAdi());
        cv.put(SORUSAYISI,ogrenci.getSoruSayisi());
        cv.put(TARIH,ogrenci.getTarih());
        long id= db.insert(TABLO_ADİ,null,cv);
        db.close();
        return id;
    }
    public long kayıtEkle(Ders ders) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(KDERS,ders.getDersAdi());
        cv.put(HEDEFSORUSAYISI,ders.getHedefSoruSayis());

        long id= db.insert(TABLO_ADİ2,null,cv);

        db.close();
        return id;
    }


    public List<Ders> DersList() {
        SQLiteDatabase db= this.getReadableDatabase();
        String sutunlar[]=new String[]{ID,KDERS,HEDEFSORUSAYISI};
        Cursor c=db.query(TABLO_ADİ2,sutunlar,null,null,null,null,null);
        List<Ders> dersList=new ArrayList<>();
        int dersIdsira=c.getColumnIndex(ID);
        int dersdersadisira=c.getColumnIndex(KDERS);
        int hedefsorusira=c.getColumnIndex(HEDEFSORUSAYISI);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            Ders ders=new Ders();
            ders.setDersID(c.getLong(dersIdsira));
            ders.setDersAdi(c.getString(dersdersadisira));
            ders.setHedefSoruSayis(c.getInt(hedefsorusira));
            dersList.add(ders);
        }
        db.close();

        return dersList;
    }
    public List<Ogrenci> KayitList() {
        SQLiteDatabase db= this.getReadableDatabase();
        String sutunlar[]=new String[]{KAYITID,DERS,SORUSAYISI,TARIH};
        Cursor c=db.query(TABLO_ADİ,sutunlar,null,null,null,null,null);
        List<Ogrenci> katitList=new ArrayList<>();
        int dersIdsira=c.getColumnIndex(KAYITID);
        int dersdersadisira=c.getColumnIndex(DERS);
        int hedefsorusira=c.getColumnIndex(SORUSAYISI);
        int tarihsira=c.getColumnIndex(TARIH);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            Ogrenci ogrenci=new Ogrenci();
            ogrenci.setOgrenciID(c.getLong(dersIdsira));
            ogrenci.setDersAdi(c.getString(dersdersadisira));
            ogrenci.setSoruSayisi(c.getInt(hedefsorusira));
            ogrenci.setTarih(c.getLong(tarihsira));
            katitList.add(ogrenci);
        }
        db.close();

        return katitList;
    }

    public void KayitSil(long kayitID) {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLO_ADİ,KAYITID+"="+kayitID,null);
        db.close();

    }
}
