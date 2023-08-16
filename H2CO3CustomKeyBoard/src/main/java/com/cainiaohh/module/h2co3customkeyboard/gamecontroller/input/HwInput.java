package com.cainiaohh.module.h2co3customkeyboard.gamecontroller.input;

import android.view.KeyEvent;
import android.view.MotionEvent;

public interface HwInput extends Input {
    boolean onKey(KeyEvent event);

    boolean onMotionKey(MotionEvent event);

    int getSource();
}
