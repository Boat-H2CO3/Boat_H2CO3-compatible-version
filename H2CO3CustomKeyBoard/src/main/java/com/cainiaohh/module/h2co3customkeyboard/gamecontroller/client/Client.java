package com.cainiaohh.module.h2co3customkeyboard.gamecontroller.client;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.definitions.id.key.KeyEvent;

public interface Client extends KeyEvent {
    void setKey(int keyCode, boolean pressed);

    void setMouseButton(int mouseCode, boolean pressed);

    void setPointer(int x, int y);

    void setPointerInc(int xInc, int yInc);

    Activity getActivity();

    void addView(View v);

    void addContentView(View view, ViewGroup.LayoutParams params);

    void typeWords(String str);

    //void addControllerView(View v);
    int[] getGrabbedPointer();

    int[] getLoosenPointer();

    ViewGroup getViewsParent();

    View getSurfaceLayerView();

    boolean isGrabbed();
}