package com.cainiaohh.module.h2co3customkeyboard.gamecontroller.controller;

import android.view.KeyEvent;
import android.view.MotionEvent;

public interface HwController extends Controller {

    boolean dispatchKeyEvent(KeyEvent event);

    boolean dispatchMotionKeyEvent(MotionEvent event);
}
