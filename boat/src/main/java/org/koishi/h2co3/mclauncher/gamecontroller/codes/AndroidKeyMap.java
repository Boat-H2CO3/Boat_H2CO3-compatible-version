package org.koishi.h2co3.mclauncher.gamecontroller.codes;

import org.koishi.h2co3.mclauncher.gamecontroller.definitions.map.KeyMap;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;

public class AndroidKeyMap implements KeyMap, CoKeyMap {
    private final HashMap<Integer, String> androidKeyMap = new HashMap();

    public AndroidKeyMap() {
        init();
    }

    private void init() {
        this.androidKeyMap.put(Integer.valueOf(7), KeyMap.KEYMAP_KEY_0);
        this.androidKeyMap.put(Integer.valueOf(8), KeyMap.KEYMAP_KEY_1);
        this.androidKeyMap.put(Integer.valueOf(9), KeyMap.KEYMAP_KEY_2);
        this.androidKeyMap.put(Integer.valueOf(10), KeyMap.KEYMAP_KEY_3);
        this.androidKeyMap.put(Integer.valueOf(11), KeyMap.KEYMAP_KEY_4);
        this.androidKeyMap.put(Integer.valueOf(12), KeyMap.KEYMAP_KEY_5);
        this.androidKeyMap.put(Integer.valueOf(13), KeyMap.KEYMAP_KEY_6);
        this.androidKeyMap.put(Integer.valueOf(14), KeyMap.KEYMAP_KEY_7);
        this.androidKeyMap.put(Integer.valueOf(15), KeyMap.KEYMAP_KEY_8);
        this.androidKeyMap.put(Integer.valueOf(16), KeyMap.KEYMAP_KEY_9);
        this.androidKeyMap.put(Integer.valueOf(29), KeyMap.KEYMAP_KEY_A);
        this.androidKeyMap.put(Integer.valueOf(30), KeyMap.KEYMAP_KEY_B);
        this.androidKeyMap.put(Integer.valueOf(31), KeyMap.KEYMAP_KEY_C);
        this.androidKeyMap.put(Integer.valueOf(32), KeyMap.KEYMAP_KEY_D);
        this.androidKeyMap.put(Integer.valueOf(33), KeyMap.KEYMAP_KEY_E);
        this.androidKeyMap.put(Integer.valueOf(34), KeyMap.KEYMAP_KEY_F);
        this.androidKeyMap.put(Integer.valueOf(35), KeyMap.KEYMAP_KEY_G);
        this.androidKeyMap.put(Integer.valueOf(36), KeyMap.KEYMAP_KEY_H);
        this.androidKeyMap.put(Integer.valueOf(37), KeyMap.KEYMAP_KEY_I);
        this.androidKeyMap.put(Integer.valueOf(38), KeyMap.KEYMAP_KEY_J);
        this.androidKeyMap.put(Integer.valueOf(39), KeyMap.KEYMAP_KEY_K);
        this.androidKeyMap.put(Integer.valueOf(40), KeyMap.KEYMAP_KEY_L);
        this.androidKeyMap.put(Integer.valueOf(41), KeyMap.KEYMAP_KEY_M);
        this.androidKeyMap.put(Integer.valueOf(42), KeyMap.KEYMAP_KEY_N);
        this.androidKeyMap.put(Integer.valueOf(43), KeyMap.KEYMAP_KEY_O);
        this.androidKeyMap.put(Integer.valueOf(44), KeyMap.KEYMAP_KEY_P);
        this.androidKeyMap.put(Integer.valueOf(45), KeyMap.KEYMAP_KEY_Q);
        this.androidKeyMap.put(Integer.valueOf(46), KeyMap.KEYMAP_KEY_R);
        this.androidKeyMap.put(Integer.valueOf(47), KeyMap.KEYMAP_KEY_S);
        this.androidKeyMap.put(Integer.valueOf(48), KeyMap.KEYMAP_KEY_T);
        this.androidKeyMap.put(Integer.valueOf(49), KeyMap.KEYMAP_KEY_U);
        this.androidKeyMap.put(Integer.valueOf(50), KeyMap.KEYMAP_KEY_V);
        this.androidKeyMap.put(Integer.valueOf(51), KeyMap.KEYMAP_KEY_W);
        this.androidKeyMap.put(Integer.valueOf(52), KeyMap.KEYMAP_KEY_X);
        this.androidKeyMap.put(Integer.valueOf(53), KeyMap.KEYMAP_KEY_Y);
        this.androidKeyMap.put(Integer.valueOf(54), KeyMap.KEYMAP_KEY_Z);
        this.androidKeyMap.put(Integer.valueOf(69), KeyMap.KEYMAP_KEY_MINUS);
        this.androidKeyMap.put(Integer.valueOf(70), KeyMap.KEYMAP_KEY_EQUALS);
        this.androidKeyMap.put(Integer.valueOf(71), KeyMap.KEYMAP_KEY_LBRACKET);
        this.androidKeyMap.put(Integer.valueOf(72), KeyMap.KEYMAP_KEY_RBRACKET);
        this.androidKeyMap.put(Integer.valueOf(74), KeyMap.KEYMAP_KEY_SEMICOLON);
        this.androidKeyMap.put(Integer.valueOf(75), KeyMap.KEYMAP_KEY_APOSTROPHE);
        this.androidKeyMap.put(Integer.valueOf(68), KeyMap.KEYMAP_KEY_GRAVE);
        this.androidKeyMap.put(Integer.valueOf(73), KeyMap.KEYMAP_KEY_BACKSLASH);
        this.androidKeyMap.put(Integer.valueOf(55), KeyMap.KEYMAP_KEY_COMMA);
        this.androidKeyMap.put(Integer.valueOf(56), KeyMap.KEYMAP_KEY_PERIOD);
        this.androidKeyMap.put(Integer.valueOf(76), "/");
        this.androidKeyMap.put(Integer.valueOf(111), KeyMap.KEYMAP_KEY_ESC);
        this.androidKeyMap.put(Integer.valueOf(131), KeyMap.KEYMAP_KEY_F1);
        this.androidKeyMap.put(Integer.valueOf(132), KeyMap.KEYMAP_KEY_F2);
        this.androidKeyMap.put(Integer.valueOf(133), KeyMap.KEYMAP_KEY_F3);
        this.androidKeyMap.put(Integer.valueOf(134), KeyMap.KEYMAP_KEY_F4);
        this.androidKeyMap.put(Integer.valueOf(135), KeyMap.KEYMAP_KEY_F5);
        this.androidKeyMap.put(Integer.valueOf(136), KeyMap.KEYMAP_KEY_F6);
        this.androidKeyMap.put(Integer.valueOf(137), KeyMap.KEYMAP_KEY_F7);
        this.androidKeyMap.put(Integer.valueOf(138), KeyMap.KEYMAP_KEY_F8);
        this.androidKeyMap.put(Integer.valueOf(139), KeyMap.KEYMAP_KEY_F9);
        this.androidKeyMap.put(Integer.valueOf(140), KeyMap.KEYMAP_KEY_F10);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_NUMPADEQUALS), KeyMap.KEYMAP_KEY_F11);
        this.androidKeyMap.put(Integer.valueOf(142), KeyMap.KEYMAP_KEY_F12);
        this.androidKeyMap.put(Integer.valueOf(61), KeyMap.KEYMAP_KEY_TAB);
        this.androidKeyMap.put(Integer.valueOf(4), KeyMap.KEYMAP_KEY_BACKSPACE);
        this.androidKeyMap.put(Integer.valueOf(62), KeyMap.KEYMAP_KEY_SPACE);
        this.androidKeyMap.put(Integer.valueOf(115), KeyMap.KEYMAP_KEY_CAPITAL);
        this.androidKeyMap.put(Integer.valueOf(66), KeyMap.KEYMAP_KEY_ENTER);
        this.androidKeyMap.put(Integer.valueOf(59), KeyMap.KEYMAP_KEY_LSHIFT);
        this.androidKeyMap.put(Integer.valueOf(113), KeyMap.KEYMAP_KEY_LCTRL);
        this.androidKeyMap.put(Integer.valueOf(57), KeyMap.KEYMAP_KEY_LALT);
        this.androidKeyMap.put(Integer.valueOf(60), KeyMap.KEYMAP_KEY_RSHIFT);
        this.androidKeyMap.put(Integer.valueOf(114), KeyMap.KEYMAP_KEY_RCTRL);
        this.androidKeyMap.put(Integer.valueOf(58), KeyMap.KEYMAP_KEY_RALT);
        this.androidKeyMap.put(Integer.valueOf(19), KeyMap.KEYMAP_KEY_UP);
        this.androidKeyMap.put(Integer.valueOf(20), KeyMap.KEYMAP_KEY_DOWN);
        this.androidKeyMap.put(Integer.valueOf(21), KeyMap.KEYMAP_KEY_LEFT);
        this.androidKeyMap.put(Integer.valueOf(22), KeyMap.KEYMAP_KEY_RIGHT);
        this.androidKeyMap.put(Integer.valueOf(92), KeyMap.KEYMAP_KEY_PAGEUP);
        this.androidKeyMap.put(Integer.valueOf(93), KeyMap.KEYMAP_KEY_PAGEDOWN);
        this.androidKeyMap.put(Integer.valueOf(3), KeyMap.KEYMAP_KEY_HOME);
        this.androidKeyMap.put(Integer.valueOf(123), KeyMap.KEYMAP_KEY_END);
        this.androidKeyMap.put(Integer.valueOf(124), KeyMap.KEYMAP_KEY_INSERT);
        this.androidKeyMap.put(Integer.valueOf(67), KeyMap.KEYMAP_KEY_DELETE);
        this.androidKeyMap.put(Integer.valueOf(121), KeyMap.KEYMAP_KEY_PAUSE);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_CIRCUMFLEX), KeyMap.KEYMAP_KEY_NUMPAD0);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_AT), KeyMap.KEYMAP_KEY_NUMPAD1);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_COLON), KeyMap.KEYMAP_KEY_NUMPAD2);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_UNDERLINE), KeyMap.KEYMAP_KEY_NUMPAD3);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_KANJI), KeyMap.KEYMAP_KEY_NUMPAD4);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_STOP), KeyMap.KEYMAP_KEY_NUMPAD5);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_AX), KeyMap.KEYMAP_KEY_NUMPAD6);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_UNLABELED), KeyMap.KEYMAP_KEY_NUMPAD7);
        this.androidKeyMap.put(Integer.valueOf(152), KeyMap.KEYMAP_KEY_NUMPAD8);
        this.androidKeyMap.put(Integer.valueOf(153), KeyMap.KEYMAP_KEY_NUMPAD9);
        this.androidKeyMap.put(Integer.valueOf(143), KeyMap.KEYMAP_KEY_NUMLOCK);
        this.androidKeyMap.put(Integer.valueOf(116), KeyMap.KEYMAP_KEY_SCROLL);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_NUMPADENTER), KeyMap.KEYMAP_KEY_SUBTRACT);
        this.androidKeyMap.put(Integer.valueOf(Keyboard.KEY_RCONTROL), KeyMap.KEYMAP_KEY_ADD);
        this.androidKeyMap.put(Integer.valueOf(158), KeyMap.KEYMAP_KEY_DECIMAL);
        this.androidKeyMap.put(Integer.valueOf(160), KeyMap.KEYMAP_KEY_NUMPADENTER);
        this.androidKeyMap.put(Integer.valueOf(154), KeyMap.KEYMAP_KEY_DIVIDE);
        this.androidKeyMap.put(Integer.valueOf(155), KeyMap.KEYMAP_KEY_MULTIPLY);
        this.androidKeyMap.put(Integer.valueOf(120), KeyMap.KEYMAP_KEY_PRINT);
    }

    public Object translate(Object obj) {
        return this.androidKeyMap.containsKey(obj) ? this.androidKeyMap.get(obj) : null;
    }
}
