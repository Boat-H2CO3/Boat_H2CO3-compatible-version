package org.koishi.launcher.h2co3.launcher.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.koishi.launcher.h2co3.R;
import org.koishi.launcher.h2co3.component.H2CO3Activity;

public class MainActivity extends H2CO3Activity {

    private AlertDialog mDialog;
    private View dialogBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove this line if you don't want AndroidIDE to show this app's logs
//        LogSender.startLogging(this);

        setContentView(R.layout.activity_main);
        // actionBar();

        MaterialToolbar toolbar = findViewById(R.id.toolbar);

        BottomNavigationView navView = findViewById(R.id.navigation);

        dialogBg = findViewById(R.id.dialog_bg);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        setSupportActionBar(toolbar);
        NavController navController =
                Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(
                        R.id.navigation_home,
                        R.id.navigation_custom,
                        R.id.navigation_version,
                        R.id.navigation_manager)
                        .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDialog != null) {
            mDialog.dismiss();
        } else {

        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            dialogBg.setVisibility(View.GONE);
        } else {
            dialogBg.setVisibility(View.VISIBLE);
            if (mDialog != null) {
                mDialog.dismiss();
            } else {
            }
        }
    }
}
