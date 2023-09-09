package org.koishi.launcher.h2co3.launcher.ui;

import android.os.Bundle;

import org.koishi.launcher.h2co3.R;
import org.koishi.launcher.h2co3.component.H2CO3Activity;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class CustomKeyBoardActivity extends H2CO3Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_key_board);
    }
}