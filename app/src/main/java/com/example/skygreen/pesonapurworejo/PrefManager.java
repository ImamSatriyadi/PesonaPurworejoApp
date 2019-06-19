package com.example.skygreen.pesonapurworejo;

import android.content.Context;
import android.content.SharedPreferences;

/*
 * NIM   : 10116063
 * NAMA  : IMAM SATRIYADI
 * KELAS : AKB-2 / IF-2
 *
 * CHANGE LOG : SABTU 20 APRIL 2019
 * MEMBUAT PREFERENCED MANAGER
 * */

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    int privateMode   = 0;
    private static final String prefName            =  "introslider";
    private static final String isFirstTimeLaunch   =  "isFirstTimeLaunch";

    public PrefManager(Context context){
        this._context   = context;
        pref            = _context.getSharedPreferences(prefName, privateMode);
        editor          = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFisTime){
        editor.putBoolean(isFirstTimeLaunch, isFisTime);
        editor.commit();
    }


    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(isFirstTimeLaunch, true);
    }
}
