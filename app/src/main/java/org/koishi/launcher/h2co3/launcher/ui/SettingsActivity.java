package org.koishi.launcher.h2co3.launcher.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import org.koishi.launcher.h2co3.R;
import org.koishi.launcher.h2co3.component.H2CO3Activity;
import org.koishi.launcher.h2co3.launcher.ui.custom.LauncherSettingsFragment;

import java.util.Objects;

import org.koishi.launcher.h2co3core.utils.cainiaohh.CHTools;

public class SettingsActivity extends H2CO3Activity {

    public SharedPreferences sp;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getTheme().applyStyle(rikka.material.preference.R.style.ThemeOverlay_Rikka_Material3_Preference, true);
        setContentView(R.layout.activity_settings);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.menu_terminal);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
            //startActivity(new Intent(SettingsActivity.this,HomeActivity.class));
        });
        TextView bigTitle = (TextView) toolbar.getChildAt(0);
        bigTitle.setText(getResources().getString(R.string.settings));
        sp = getSharedPreferences("h2co3_preferences_launcher_setting", MODE_PRIVATE);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new LauncherSettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        //startActivity(new Intent(SettingsActivity.this,HomeActivity.class));
    }

    @Override
    public void onPause() {
        super.onPause();
        new Thread(() -> {
            String set_id = sp.getString("set_id", "player");
            String set_control = sp.getString("set_control", "None");
            String set_jvm = sp.getString("set_jvm", "-boat -Xmx4000M");
            String set_mcf = sp.getString("set_mcf", "");
            String set_gl = sp.getString("set_gl", "gl14es");
            String set_java = sp.getString("set_java", "jre_17");
            String set_source = sp.getString("set_source", "https://download.mcbbs.net");
            boolean set_click_b = sp.getBoolean("set_click", false);
            boolean set_pause_b = sp.getBoolean("set_pause", false);
            boolean set_single_b = sp.getBoolean("set_single", false);
            boolean set_check_b = sp.getBoolean("set_check_file", false);
            String set_click = String.valueOf(set_click_b);
            String set_pause = String.valueOf(set_pause_b);
            String set_single = String.valueOf(set_single_b);
            String set_check = String.valueOf(set_check_b);

            CHTools.setBoatJson("auth_player_name", set_id);
            CHTools.setAppJson("jumpToLeft", set_control);
            CHTools.setBoatJson("extraJavaFlags", set_jvm);
            CHTools.setBoatJson("extraMinecraftFlags", set_mcf);
            CHTools.setAppJson("openGL", set_gl);
            CHTools.setAppJson("java", set_java);
            CHTools.setAppJson("sourceLink", set_source);
            CHTools.setAppJson("backToRightClick", set_click);
            CHTools.setAppJson("dontEsc", set_pause);
            CHTools.setAppJson("allVerLoad", set_single);
            CHTools.setAppJson("checkFile", set_check);
        }).start();

    }
}