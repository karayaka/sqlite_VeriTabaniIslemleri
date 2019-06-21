package com.example.kisar.sqlite_veritabaniislemleri;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by kisar on 21.06.2019.
 */

public class MyActionModelCallback implements android.view.ActionMode.Callback {

    private Context context;
    private long kayitID;
    private android.view.ActionMode actionMode;
    public MyActionModelCallback(Context context,long kayitID ,android.view.ActionMode actionMode) {
        this.setContext(context);
        this.setKayitID(kayitID);
        this.setActionMode(actionMode);
    }

    @Override
    public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.context_menu,menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
        switch (item.getItemId()){
            case R.id.content_sil:
                veriTabani db=new veriTabani(getContext());
                db.KayitSil(getKayitID());
                //hocaya sorulacak
                //silme işlemleri
                mode.finish();
                return true;
            case R.id.content_duzenle:
                //duzenleme işlemleri
                mode.finish();
                return true;
            default:
                return false;
        }

    }

    @Override
    public void onDestroyActionMode(android.view.ActionMode mode) {
        setActionMode(null);

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public long getKayitID() {
        return kayitID;
    }

    public void setKayitID(long kayitID) {
        this.kayitID = kayitID;
    }

    public android.view.ActionMode getActionMode() {
        return actionMode;
    }

    public void setActionMode(android.view.ActionMode actionMode) {
        this.actionMode = actionMode;
    }
}
