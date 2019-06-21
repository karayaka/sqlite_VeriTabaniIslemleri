package com.example.kisar.sqlite_veritabaniislemleri;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DersActivity extends AppCompatActivity {

    ListView listDers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ders);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.dersliste);
        listDers= (ListView) findViewById(R.id.listDers);
        listDers.setBackgroundColor(Color.WHITE);
        ArrayAdapter<String>DersLİst=new
                ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,dersListe());
        listDers.setAdapter(DersLİst);

    }

    private String[] dersListe() {
        veriTabani db=new veriTabani(getApplicationContext());
        String liste[]=new String[db.DersList().size()];
        int say=0;
        for(Ders ders:db.DersList()){

            liste[say]="Ders Adı: "+ders.getDersAdi()+"Hedef Soru:"+ders.getHedefSoruSayis();
            say++;

        }
        return liste;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            Intent i = new Intent(DersActivity.this, MainActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);

    }
}
