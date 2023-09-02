package org.koishi.launcher.h2co3;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.preference.PreferenceManager;

import rikka.material.app.MaterialActivity;

public class H2CO3Activity extends MaterialActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //这里做你想做的事
            boolean spIsAuth = sharedPreferences.getBoolean("material", true);
            if (spIsAuth) {
                setTheme(R.style.Theme_H2CO3_DynamicColors);
            } else {
                setTheme(R.style.Theme_H2CO3);
            }
        } else {
            setTheme(R.style.Theme_H2CO3);
        }

    }
}
