package com.cainiaohh.module.h2co3customkeyboard.gamecontroller.codes;

import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.definitions.map.KeyMap;
import com.lwjgl.input.Keyboard;

import java.util.HashMap;

public class AndroidKeyMap implements KeyMap, CoKeyMap {
    private final HashMap<Integer, String> androidKeyMap = new HashMap();

    public AndroidKeyMap() {
        init();
    }

    private void init() {
        this.androidKeyMap.put(Integer.valueOf(7), KEYMAP_KEY_0);
        this.androidKeyMap.put(Integer.valueOf(8), KEYMAP_KEY_1);
        this.androidKeyMap.put(Integer.valueOf(9), KEYMAP_KEY_2);
        this.androidKeyMap.put(Integer.valueOf(10), KEYMAP_KEY_3);
        this.androidKeyMap.put(Integer.valueOf(11), KEYMAP_KEY_4);
        this.androidKeyMap.put(Integer.valueOf(12), KEYMAP_KEY_5);
        this.androidKeyMap.put(Integer.valueOf(13), KEYMAP_KEY_6);
        this.androidKeyMap.put(Integer.valueOf(14), KEYMAP_KEY_7);
        this.androidKeyMap.put(Integer.valueOf(15), KEYMAP_KEY_8);
        this.androidKeyMap.put(Integer.valueOf(16), KEYMAP_KEY_9);
        this.androidKeyMap.put(Integer.valueOf(29), KEYMAP_KEY_A);
        this.androidKeyMap.put(Integer.valueOf(30), KEYMAP_KEY_B);
        this.androidKeyMap.put(Integer.valueOf(31), KEYMAP_KEY_C);
        this.androidKeyMap.put(Integer.valueOf(32), KEYMAP_KEY_D);
        this.androidKeyMap.put(Integer.valueOf(33), KEYMAP_KEY_E);
        this.androidKeyMap.put(Integer.valueOf(34), KEYMAP_KEY_F);
        this.androidKeyMap.put(Integer.valueOf(35), KEYMAP_KEY_G);
        this.androidKeyMap.put(Integer.valueOf(36), KEYMAP_KEY_H);
        this.androidKeyMap.put(Integer.valueOf(37), KEYMAP_KEY_I);
        this.androidKeyMap.put(Integer.valueOf(38), KEYMAP_KEY_J);
        this.androidKeyMap.put(Integer.valueOf(39), KEYMAP_KEY_K);
        this.androidKeyMap.put(Integer.valueOf(40), KEYMAP_KEY_L);
        this.androidKeyMap.put(Integer.valueOf(41), KEYMAP_KEY_M);
        this.androidKeyMap.put(Integer.valueOf(42), KEYMAP_KEY_N);
        this.androidKeyMap.put(Integer.valueOf(43), KEYMAP_KEY_O);
        this.androidKeyMap.put(Integer.valueOf(44), KEYMAP_KEY_P);
        this.androidKeyMap.put(Integer.valueOf(45), KEYMAP_KEY_Q);
        this.androidKeyMap.put(Integer.valueOf(46), KEYMAP_KEY_R);
        this.androidKeyMap.put(Integer.valueOf(47), KEYMAP_KEY_S);
        this.androidKeyMap.put(Integer.valueOf(48), KEYMAP_KEY_T);
        this.androidKeyMap.put(Integer.valueOf(49), KEYMAP_KEY_U);
        this.androidKeyMap.put(Integer.valueOf(50), KEYMAP_KEY_V);
        this.androidKeyMap.put(Integer.valueOf(51), KEYMAP_KEY_W);
        this.androidKeyMap.put(Integer.valueOf(52), KEYMAP_KEY_X);
        this.androidKeyMap.put(Integer.valueOf(53), KEYMAP_KEY_Y);
        this.androidKeyMap.put(Integer.valueOf(54), KEYMAP_KEY_Z);
        this.androidKeyMap.put(Integer.valueOf(69), KEYMAP_KEY_MINUS);
        this.androidKeyMap.put(Integer.valueOf(70), KEYMAP_KEY_EQUALS);
        this.androidKeyMap.put(Integer.valueOf(71), KEYMAP_KEY_LBRACKET);
        this.androidKeyMap.put(Integer.valueOf(72), KEYMAP_KEY_RBRACKET);
        this.androidKeyMap.put(Integer.valueOf(74), KEYMAP_KEY_SEMICOLON);
        this.androidKeyMap.put(Integer.valueOf(75), KEYMAP_KEY_APOSTROPHE);
        this.androidKeyMap.put(Integer.valueOf(68), KEYMAP_KEY_GRAVE);
        this.androidKeyMap.put(Integer.valueOf(73), KEYMAP_KEY_BACKSLASH);
        this.androidKeyMap.put(Integer.valueOf(55), KEYMAP_KEY_COMMA);
        this.androidKeyMap.put(Integer.valueOf(56), KEYMAP_KEY_PERIOD);
        this.androidKeyMap.put(Integer.valueOf(76), "/");
        this.androidKeyMap.put(Integer.valueOf(111), KEYMAP_KEY_ESC);
        this.androidKeyMap.put(Integer.valueOf(131), KEYMAP_KEY_F1);
        this.androidKeyMap.put(Integer.valueOf(132), KEYMAP_KEY_F2);
        this.androidKeyMap.put(Integer.valueOf(133), KEYMAP_KEY_F3);
        this.androidKeyMap.put(Integer.valueOf(134), KEYMAP_KEY_F4);
        this.androidKeyMap.put(Integer.valueOf(135), KEYMAP_KEY_F5);
        this.androidKeyMap.put(Integer.valueOf(136), KEYMAP_KEY_F6);
        this.androidKeyMap.put(Integer.valueOf(137), KEYMAP_KEY_F7);
        this.androidKeyMap.put(Integer.valueOf(138), KEYMAP_KEY_F8);
        this.androidKeyMap.put(Integer.valueOf(139), KEYMAP_KEY_F9);
        this.androidKeyMap.put(Integer.valueOf(140), KEYMAP_KEY_F10);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_NUMPADEQUALS), KEYMAP_KEY_F11);
        this.androidKeyMap.put(Integer.valueOf(142), KEYMAP_KEY_F12);
        this.androidKeyMap.put(Integer.valueOf(61), KEYMAP_KEY_TAB);
        this.androidKeyMap.put(Integer.valueOf(4), KEYMAP_KEY_BACKSPACE);
        this.androidKeyMap.put(Integer.valueOf(62), KEYMAP_KEY_SPACE);
        this.androidKeyMap.put(Integer.valueOf(115), KEYMAP_KEY_CAPITAL);
        this.androidKeyMap.put(Integer.valueOf(66), KEYMAP_KEY_ENTER);
        this.androidKeyMap.put(Integer.valueOf(59), KEYMAP_KEY_LSHIFT);
        this.androidKeyMap.put(Integer.valueOf(113), KEYMAP_KEY_LCTRL);
        this.androidKeyMap.put(Integer.valueOf(57), KEYMAP_KEY_LALT);
        this.androidKeyMap.put(Integer.valueOf(60), KEYMAP_KEY_RSHIFT);
        this.androidKeyMap.put(Integer.valueOf(114), KEYMAP_KEY_RCTRL);
        this.androidKeyMap.put(Integer.valueOf(58), KEYMAP_KEY_RALT);
        this.androidKeyMap.put(Integer.valueOf(19), KEYMAP_KEY_UP);
        this.androidKeyMap.put(Integer.valueOf(20), KEYMAP_KEY_DOWN);
        this.androidKeyMap.put(Integer.valueOf(21), KEYMAP_KEY_LEFT);
        this.androidKeyMap.put(Integer.valueOf(22), KEYMAP_KEY_RIGHT);
        this.androidKeyMap.put(Integer.valueOf(92), KEYMAP_KEY_PAGEUP);
        this.androidKeyMap.put(Integer.valueOf(93), KEYMAP_KEY_PAGEDOWN);
        this.androidKeyMap.put(Integer.valueOf(3), KEYMAP_KEY_HOME);
        this.androidKeyMap.put(Integer.valueOf(123), KEYMAP_KEY_END);
        this.androidKeyMap.put(Integer.valueOf(124), KEYMAP_KEY_INSERT);
        this.androidKeyMap.put(Integer.valueOf(67), KEYMAP_KEY_DELETE);
        this.androidKeyMap.put(Integer.valueOf(121), KEYMAP_KEY_PAUSE);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_CIRCUMFLEX), KEYMAP_KEY_NUMPAD0);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_AT), KEYMAP_KEY_NUMPAD1);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_COLON), KEYMAP_KEY_NUMPAD2);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_UNDERLINE), KEYMAP_KEY_NUMPAD3);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_KANJI), KEYMAP_KEY_NUMPAD4);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_STOP), KEYMAP_KEY_NUMPAD5);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_AX), KEYMAP_KEY_NUMPAD6);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_UNLABELED), KEYMAP_KEY_NUMPAD7);
        this.androidKeyMap.put(Integer.valueOf(152), KEYMAP_KEY_NUMPAD8);
        this.androidKeyMap.put(Integer.valueOf(153), KEYMAP_KEY_NUMPAD9);
        this.androidKeyMap.put(Integer.valueOf(143), KEYMAP_KEY_NUMLOCK);
        this.androidKeyMap.put(Integer.valueOf(116), KEYMAP_KEY_SCROLL);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_NUMPADENTER), KEYMAP_KEY_SUBTRACT);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_RCONTROL), KEYMAP_KEY_ADD);
        this.androidKeyMap.put(Integer.valueOf(158), KEYMAP_KEY_DECIMAL);
        this.androidKeyMap.put(Integer.valueOf(160), KEYMAP_KEY_NUMPADENTER);
        this.androidKeyMap.put(Integer.valueOf(154), KEYMAP_KEY_DIVIDE);
        this.androidKeyMap.put(Integer.valueOf(155), KEYMAP_KEY_MULTIPLY);
        this.androidKeyMap.put(Integer.valueOf(120), KEYMAP_KEY_PRINT);
    }

    public Object translate(Object obj) {
        return this.androidKeyMap.containsKey(obj) ? this.androidKeyMap.get(obj) : null;
    }
}
