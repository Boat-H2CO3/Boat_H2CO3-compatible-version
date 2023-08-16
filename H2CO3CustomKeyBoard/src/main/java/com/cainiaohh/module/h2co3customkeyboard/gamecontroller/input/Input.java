package com.cainiaohh.module.h2co3customkeyboard.gamecontroller.input;

import android.content.Context;

import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.controller.Controller;

public interface Input {
    boolean load(Context context, Controller controller);

    boolean unload();

    void setGrabCursor(boolean isGrabbed); // 赋值 MARK_INPUT_MODE

    void runConfigure();

    void saveConfig();

    boolean isEnabled();

    void setEnabled(boolean enabled);

    void onPaused();

    void onResumed();

    Controller getController();
}
