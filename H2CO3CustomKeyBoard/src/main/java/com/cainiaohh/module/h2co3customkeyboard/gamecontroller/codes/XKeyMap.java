package com.cainiaohh.module.h2co3customkeyboard.gamecontroller.codes;

import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.definitions.map.KeyMap;
import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.definitions.map.MouseMap;

import java.util.HashMap;

public class XKeyMap implements KeyMap, CoKeyMap {
    private final HashMap<String, Integer> xKeyMap = new HashMap();

    public XKeyMap() {
        init();
    }

    private void init() {
        this.xKeyMap.put(KEYMAP_KEY_0, Integer.valueOf(11));
        HashMap hashMap = this.xKeyMap;
        Integer valueOf = Integer.valueOf(2);
        hashMap.put(KEYMAP_KEY_1, valueOf);
        hashMap = this.xKeyMap;
        Integer valueOf2 = Integer.valueOf(3);
        hashMap.put(KEYMAP_KEY_2, valueOf2);
        hashMap = this.xKeyMap;
        Integer valueOf3 = Integer.valueOf(4);
        hashMap.put(KEYMAP_KEY_3, valueOf3);
        hashMap = this.xKeyMap;
        Integer valueOf4 = Integer.valueOf(5);
        hashMap.put(KEYMAP_KEY_4, valueOf4);
        this.xKeyMap.put(KEYMAP_KEY_5, Integer.valueOf(6));
        this.xKeyMap.put(KEYMAP_KEY_6, Integer.valueOf(7));
        this.xKeyMap.put(KEYMAP_KEY_7, Integer.valueOf(8));
        this.xKeyMap.put(KEYMAP_KEY_8, Integer.valueOf(9));
        this.xKeyMap.put(KEYMAP_KEY_9, Integer.valueOf(10));
        this.xKeyMap.put(KEYMAP_KEY_A, Integer.valueOf(30));
        this.xKeyMap.put(KEYMAP_KEY_B, Integer.valueOf(48));
        this.xKeyMap.put(KEYMAP_KEY_C, Integer.valueOf(46));
        this.xKeyMap.put(KEYMAP_KEY_D, Integer.valueOf(32));
        this.xKeyMap.put(KEYMAP_KEY_E, Integer.valueOf(18));
        this.xKeyMap.put(KEYMAP_KEY_F, Integer.valueOf(33));
        this.xKeyMap.put(KEYMAP_KEY_G, Integer.valueOf(34));
        this.xKeyMap.put(KEYMAP_KEY_H, Integer.valueOf(35));
        this.xKeyMap.put(KEYMAP_KEY_I, Integer.valueOf(23));
        this.xKeyMap.put(KEYMAP_KEY_J, Integer.valueOf(36));
        this.xKeyMap.put(KEYMAP_KEY_K, Integer.valueOf(37));
        this.xKeyMap.put(KEYMAP_KEY_L, Integer.valueOf(38));
        this.xKeyMap.put(KEYMAP_KEY_M, Integer.valueOf(50));
        this.xKeyMap.put(KEYMAP_KEY_N, Integer.valueOf(49));
        this.xKeyMap.put(KEYMAP_KEY_O, Integer.valueOf(24));
        hashMap = this.xKeyMap;
        Integer valueOf5 = Integer.valueOf(25);
        hashMap.put(KEYMAP_KEY_P, valueOf5);
        this.xKeyMap.put(KEYMAP_KEY_Q, Integer.valueOf(16));
        this.xKeyMap.put(KEYMAP_KEY_R, Integer.valueOf(19));
        this.xKeyMap.put(KEYMAP_KEY_S, Integer.valueOf(31));
        this.xKeyMap.put(KEYMAP_KEY_T, Integer.valueOf(20));
        this.xKeyMap.put(KEYMAP_KEY_U, Integer.valueOf(22));
        this.xKeyMap.put(KEYMAP_KEY_V, Integer.valueOf(47));
        this.xKeyMap.put(KEYMAP_KEY_W, Integer.valueOf(17));
        this.xKeyMap.put(KEYMAP_KEY_X, Integer.valueOf(45));
        this.xKeyMap.put(KEYMAP_KEY_Y, Integer.valueOf(21));
        this.xKeyMap.put(KEYMAP_KEY_Z, Integer.valueOf(44));
        this.xKeyMap.put(KEYMAP_KEY_MINUS, Integer.valueOf(12));
        this.xKeyMap.put(KEYMAP_KEY_EQUALS, Integer.valueOf(13));
        this.xKeyMap.put(KEYMAP_KEY_LBRACKET, Integer.valueOf(26));
        this.xKeyMap.put(KEYMAP_KEY_RBRACKET, Integer.valueOf(27));
        this.xKeyMap.put(KEYMAP_KEY_SEMICOLON, Integer.valueOf(39));
        this.xKeyMap.put(KEYMAP_KEY_APOSTROPHE, Integer.valueOf(40));
        this.xKeyMap.put(KEYMAP_KEY_GRAVE, Integer.valueOf(41));
        this.xKeyMap.put(KEYMAP_KEY_BACKSLASH, Integer.valueOf(43));
        this.xKeyMap.put(KEYMAP_KEY_COMMA, Integer.valueOf(51));
        this.xKeyMap.put(KEYMAP_KEY_PERIOD, Integer.valueOf(52));
        this.xKeyMap.put("/", Integer.valueOf(53));
        hashMap = this.xKeyMap;
        Integer valueOf6 = Integer.valueOf(1);
        hashMap.put(KEYMAP_KEY_ESC, valueOf6);
        this.xKeyMap.put(KEYMAP_KEY_F1, Integer.valueOf(59));
        this.xKeyMap.put(KEYMAP_KEY_F2, Integer.valueOf(60));
        this.xKeyMap.put(KEYMAP_KEY_F3, Integer.valueOf(61));
        this.xKeyMap.put(KEYMAP_KEY_F4, Integer.valueOf(62));
        this.xKeyMap.put(KEYMAP_KEY_F5, Integer.valueOf(63));
        this.xKeyMap.put(KEYMAP_KEY_F6, Integer.valueOf(64));
        this.xKeyMap.put(KEYMAP_KEY_F7, Integer.valueOf(65));
        this.xKeyMap.put(KEYMAP_KEY_F8, Integer.valueOf(66));
        this.xKeyMap.put(KEYMAP_KEY_F9, Integer.valueOf(67));
        this.xKeyMap.put(KEYMAP_KEY_F10, Integer.valueOf(68));
        this.xKeyMap.put(KEYMAP_KEY_F11, Integer.valueOf(87));
        this.xKeyMap.put(KEYMAP_KEY_F12, Integer.valueOf(88));
        this.xKeyMap.put(KEYMAP_KEY_TAB, Integer.valueOf(15));
        this.xKeyMap.put(KEYMAP_KEY_BACKSPACE, Integer.valueOf(14));
        this.xKeyMap.put(KEYMAP_KEY_SPACE, Integer.valueOf(57));
        this.xKeyMap.put(KEYMAP_KEY_CAPITAL, Integer.valueOf(58));
        this.xKeyMap.put(KEYMAP_KEY_ENTER, Integer.valueOf(28));
        this.xKeyMap.put(KEYMAP_KEY_LSHIFT, Integer.valueOf(42));
        this.xKeyMap.put(KEYMAP_KEY_LCTRL, Integer.valueOf(29));
        this.xKeyMap.put(KEYMAP_KEY_LALT, Integer.valueOf(56));
        this.xKeyMap.put(KEYMAP_KEY_RSHIFT, Integer.valueOf(54));
        this.xKeyMap.put(KEYMAP_KEY_RCTRL, Integer.valueOf(97));
        this.xKeyMap.put(KEYMAP_KEY_RALT, Integer.valueOf(100));
        this.xKeyMap.put(KEYMAP_KEY_UP, Integer.valueOf(103));
        this.xKeyMap.put(KEYMAP_KEY_DOWN, Integer.valueOf(108));
        this.xKeyMap.put(KEYMAP_KEY_LEFT, Integer.valueOf(105));
        this.xKeyMap.put(KEYMAP_KEY_RIGHT, Integer.valueOf(106));
        this.xKeyMap.put(KEYMAP_KEY_PAGEUP, Integer.valueOf(104));
        this.xKeyMap.put(KEYMAP_KEY_PAGEDOWN, Integer.valueOf(109));
        this.xKeyMap.put(KEYMAP_KEY_HOME, Integer.valueOf(102));
        this.xKeyMap.put(KEYMAP_KEY_END, Integer.valueOf(107));
        this.xKeyMap.put(KEYMAP_KEY_INSERT, Integer.valueOf(110));
        this.xKeyMap.put(KEYMAP_KEY_DELETE, Integer.valueOf(111));
        this.xKeyMap.put(KEYMAP_KEY_PAUSE, Integer.valueOf(119));
        this.xKeyMap.put(KEYMAP_KEY_NUMPAD0, Integer.valueOf(82));
        this.xKeyMap.put(KEYMAP_KEY_NUMPAD1, Integer.valueOf(79));
        this.xKeyMap.put(KEYMAP_KEY_NUMPAD2, Integer.valueOf(80));
        this.xKeyMap.put(KEYMAP_KEY_NUMPAD3, Integer.valueOf(81));
        this.xKeyMap.put(KEYMAP_KEY_NUMPAD4, Integer.valueOf(75));
        this.xKeyMap.put(KEYMAP_KEY_NUMPAD5, Integer.valueOf(76));
        this.xKeyMap.put(KEYMAP_KEY_NUMPAD6, Integer.valueOf(77));
        this.xKeyMap.put(KEYMAP_KEY_NUMPAD7, Integer.valueOf(71));
        this.xKeyMap.put(KEYMAP_KEY_NUMPAD8, Integer.valueOf(72));
        this.xKeyMap.put(KEYMAP_KEY_NUMPAD9, Integer.valueOf(73));
        this.xKeyMap.put(KEYMAP_KEY_NUMLOCK, Integer.valueOf(69));
        this.xKeyMap.put(KEYMAP_KEY_SCROLL, Integer.valueOf(70));
        this.xKeyMap.put(KEYMAP_KEY_SUBTRACT, Integer.valueOf(74));
        this.xKeyMap.put(KEYMAP_KEY_ADD, Integer.valueOf(78));
        this.xKeyMap.put(KEYMAP_KEY_DECIMAL, Integer.valueOf(83));
        this.xKeyMap.put(KEYMAP_KEY_NUMPADENTER, Integer.valueOf(96));
        this.xKeyMap.put(KEYMAP_KEY_DIVIDE, Integer.valueOf(98));
        this.xKeyMap.put(KEYMAP_KEY_MULTIPLY, Integer.valueOf(55));
        this.xKeyMap.put(KEYMAP_KEY_PRINT, valueOf5);
        this.xKeyMap.put(MouseMap.MOUSEMAP_BUTTON_LEFT, valueOf6);
        this.xKeyMap.put(MouseMap.MOUSEMAP_BUTTON_RIGHT, valueOf2);
        this.xKeyMap.put(MouseMap.MOUSEMAP_BUTTON_MIDDLE, valueOf);
        this.xKeyMap.put(MouseMap.MOUSEMAP_WHEEL_UP, valueOf3);
        this.xKeyMap.put(MouseMap.MOUSEMAP_WHEEL_DOWN, valueOf4);
    }

    public Object translate(Object obj) {
        if (this.xKeyMap.containsKey(obj)) {
            return this.xKeyMap.get(obj);
        }
        return Integer.valueOf(-1);
    }
}
