package com.cainiaohh.module.h2co3customkeyboard.gamecontroller.input.otg;

import static com.cainiaohh.module.h2co3customkeyboard.gamecontroller.definitions.id.key.KeyEvent.ANDROID_TO_KEYMAP;
import static com.cainiaohh.module.h2co3customkeyboard.gamecontroller.definitions.id.key.KeyEvent.KEYBOARD_BUTTON;

import android.app.Service;
import android.content.Context;
import android.media.AudioManager;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.codes.Translation;
import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.controller.Controller;
import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.definitions.map.KeyMap;
import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.event.BaseKeyEvent;
import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.input.HwInput;

public class Phone implements HwInput {

    private final static String TAG = "Phone";
    private Translation mTrans;
    private Controller mController;
    private final int type = KEYBOARD_BUTTON;
    private Context mContext;
    private boolean isEnabled = false;

    @Override
    public boolean load(Context context, Controller controller) {
        mTrans = new Translation(ANDROID_TO_KEYMAP);
        this.mContext = context;
        this.mController = controller;
        return true;
    }

    @Override
    public boolean unload() {
        return false;
    }

    @Override
    public void setGrabCursor(boolean isGrabbed) {

    }

    @Override
    public void runConfigure() {

    }

    @Override
    public void saveConfig() {

    }

    @Override
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    @Override
    public boolean onKey(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                sendKeyEvent(KeyMap.KEYMAP_KEY_ESC, event);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onMotionKey(MotionEvent event) {
        return false;
    }

    @Override
    public int getSource() {
        return InputDevice.SOURCE_KEYBOARD;
    }

    private void sendKeyEvent(String keyName, KeyEvent event) {
        boolean pressed;
        switch (event.getAction()) {
            case KeyEvent.ACTION_UP:
                pressed = false;
                break;
            case KeyEvent.ACTION_DOWN:
                pressed = true;
                break;
            default:
                return;

        }
        mController.sendKey(new BaseKeyEvent(TAG, keyName, pressed, type, null));
    }

    private void adjustAudio(int direction) {
        AudioManager audioManager = (AudioManager) mContext.getSystemService(Service.AUDIO_SERVICE);
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, direction, AudioManager.FLAG_SHOW_UI);
    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onResumed() {

    }

    @Override
    public Controller getController() {
        return this.mController;
    }
}
