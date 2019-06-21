package com.example.kisar.sqlite_veritabaniislemleri;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int HAKKINDA=1;
    private static final int DERS=2;
    private static final int TARİH=3;
    private static final int DERSEKLE=4;
    long kayitID;
    private android.view.ActionMode actionMode;
    TableLayout tablo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tablo= (TableLayout) findViewById(R.id.tltablo);
        try{
            KayitListele();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),R.string.kayityok,Toast.LENGTH_SHORT).show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DERS);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void KayitListele() {
        veriTabani db = new veriTabani(getApplicationContext());
        tablo.removeAllViews();
        for(final Ogrenci ogrenci :db.KayitList()){
            TableRow row =new TableRow(MainActivity.this);
            row.setGravity(Gravity.CENTER);

            TextView tv_tarih=new TextView(MainActivity.this);
            tv_tarih.setPadding(2,2,2,2);
            tv_tarih.setTextColor(Color.BLACK);

            SimpleDateFormat df= new SimpleDateFormat("dd/MM/yyyy");
            Date tarih=new Date(ogrenci.getTarih());
            tv_tarih.setText(df.format(tarih)+"   ");


            TextView tv_ders=new TextView(MainActivity.this);
            tv_ders.setPadding(2,2,2,2);
            tv_ders.setTextColor(Color.BLACK);
            tv_ders.setText(ogrenci.getDersAdi()+"   ");


            TextView tv_soru=new TextView(MainActivity.this);
            tv_soru.setPadding(2,2,2,2);
            tv_soru.setTextColor(Color.BLACK);
            tv_soru.setText(""+ogrenci.getSoruSayisi());

            //satırlara objeler eklendi

            row.addView(tv_tarih);
            row.addView(tv_ders);
            row.addView(tv_soru);
            row.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    kayitID=ogrenci.getOgrenciID();
                    if(actionMode!=null){
                        return false;
                    }
                    MyActionModelCallback callback =new MyActionModelCallback(getApplicationContext(),kayitID,actionMode);
                    actionMode=startActionMode(callback);

                    v.setSelected(true);
                    KayitListele();
                    actionMode=null;
                    return true;
                }
            });

            tablo.addView(row);


        }


    }






    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       switch (id){
           case R.id.action_settings:

               return true;
           case R.id.tbtnekle:
               showDialog(DERS);
               return true;
           case R.id.tarih:
               return true;
           case R.id.paylas:
               return true;
           case R.id.hakkinde:
               showDialog(HAKKINDA);
           case R.id.dersEkle:
               showDialog(DERSEKLE);

               return true;
           default:
               return super.onOptionsItemSelected(item);
       }


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dersliste) {
           Intent i = new Intent(MainActivity.this,DersActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog=null;
        switch (id){
            case HAKKINDA:
                dialog=new Dialog(MainActivity.this);
                dialog.setTitle(R.string.tbtnhakkinda);
                dialog.setContentView(R.layout.hakinda);
                dialog.setCanceledOnTouchOutside(true);
                break;
            case DERS:
                dialog= getEkleDialogKayit();
                break;
            case TARİH:
                break;
            case DERSEKLE:
                dialog= getEkleDialogDers();
                break;
        }
        return dialog;
    }
    private Dialog getEkleDialogKayit(){
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View layout = layoutInflater.inflate(R.layout.dersekle,null);
        //arayüz nesnelerine ulaşıyoruz
        Button btnkaydet= (Button) layout.findViewById(R.id.btnkaydet);
        Button btniptal= (Button) layout.findViewById(R.id.btniptal);
        final EditText soru= (EditText) layout.findViewById(R.id.etsoru);
        final Spinner spders= (Spinner) layout.findViewById(R.id.spders);
        final DatePicker dptarih= (DatePicker) layout.findViewById(R.id.dptarih);


        ArrayAdapter<String> dersListe=
                new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,dersSpinnerList());
        dersListe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spders.setAdapter(dersListe);


        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(R.string.dersEkle);
        builder.setView(layout);

        final AlertDialog dialog =builder.create();
        dialog.show();
        btnkaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int gun=dptarih.getDayOfMonth();
                    int ay= dptarih.getMonth()+1;
                    int yil =dptarih.getYear();
                    Date date=null;
                    SimpleDateFormat df= new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        date=df.parse(gun+"/"+ay+"/"+yil);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long tarih=date.getTime();
                    int pozisyon =spders.getSelectedItemPosition();
                    String ders= (String) spders.getSelectedItem();
                    int sorusayisi=Integer.valueOf(soru.getText().toString());
                    Ogrenci ogrenci=new Ogrenci();
                    ogrenci.setDersAdi(ders);
                    ogrenci.setSoruSayisi(sorusayisi);
                    ogrenci.setTarih(tarih);
                    veriTabani db = new veriTabani(getApplicationContext());
                    long id= db.kayıtEkle(ogrenci);
                    if(id==-1){
                        Snackbar snackbar= Snackbar.make(findViewById(R.id.mainLaut),R.string.kayithata,Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                    else {
                        Snackbar snackbar= Snackbar.make(findViewById(R.id.mainLaut),R.string.kayitbasarili,Snackbar.LENGTH_SHORT);
                        snackbar.show();

                        KayitListele();
                    }
                    dialog.dismiss();

                }catch (Exception e){
                    Snackbar snackbar= Snackbar.make(findViewById(R.id.mainLaut),"Lütfen Formu Doldurunuz",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }



            }
        });
        btniptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                dialog.cancel();
            }
        });


        return null;
    }

    private String[] dersSpinnerList() {
        veriTabani db=new veriTabani(getApplicationContext());
        String liste[]=new String[db.DersList().size()];
        int say=0;
        for(Ders ders:db.DersList()){

           liste[say]=ders.getDersAdi();
            say++;

        }
        return liste;
    }



    private Dialog getEkleDialogDers(){
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View layout = layoutInflater.inflate(R.layout.ders,null);
        //arayüz nesnelerine ulaşıyoruz
        Button btnkaydet= (Button) layout.findViewById(R.id.btnkaydetders);
        Button btniptal= (Button) layout.findViewById(R.id.btniptalders);
        final EditText dersadiders= (EditText) layout.findViewById(R.id.etdersadi);
        final EditText hedefsoruders= (EditText) layout.findViewById(R.id.ethedefsoruders);
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(R.string.dersEkle);
        builder.setView(layout);
        final AlertDialog dialog =builder.create();
        dialog.show();
        btnkaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String dersadi=dersadiders.getText().toString();
                    int hedefsoru=Integer.valueOf(hedefsoruders.getText().toString());
                    Ders ders = new Ders();
                    ders.setDersAdi(dersadi);
                    ders.setHedefSoruSayis(hedefsoru);
                    //Toast.makeText(getApplicationContext(),ders.getDersAdi().toString()+ders.getHedefSoruSayis(),Toast.LENGTH_LONG).show();
                    veriTabani db = new veriTabani(getApplicationContext());
                    long id= db.kayıtEkle(ders);
                    if(id==-1){
                        Snackbar snackbar= Snackbar.make(findViewById(R.id.mainLaut),R.string.kayithata,Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                    else {
                        Snackbar snackbar= Snackbar.make(findViewById(R.id.mainLaut),R.string.kayitbasarili,Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                    dialog.dismiss();

                }catch (Exception e){
                    Snackbar snackbar= Snackbar.make(findViewById(R.id.mainLaut),"Lütfen Formu Doldurunuz",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }



            }
        });
        btniptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                dialog.cancel();
            }
        });


        return null;
    }

}

