package org.koishi.launcher.h2co3;

import android.os.Bundle;

import org.koishi.launcher.h2co3.application.H2CO3Activity;

public class LoginActivity extends H2CO3Activity {

    String url = "https://www.minecraft.net/en-us/download/alternative/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}