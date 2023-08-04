package org.koishi.launcher.h2co3.launcher.control.gamecontroller.codes;

import org.koishi.launcher.h2co3.launcher.control.gamecontroller.definitions.map.KeyMap;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;

public class LwjglKeyMap implements KeyMap, CoKeyMap {
    private final HashMap<String, Integer> lwjglMap = new HashMap();

    public LwjglKeyMap() {
        init();
    }

    private void init() {
        this.lwjglMap.put(KEYMAP_KEY_0, Integer.valueOf(11));
        this.lwjglMap.put(KEYMAP_KEY_1, Integer.valueOf(2));
        this.lwjglMap.put(KEYMAP_KEY_2, Integer.valueOf(3));
        this.lwjglMap.put(KEYMAP_KEY_3, Integer.valueOf(4));
        this.lwjglMap.put(KEYMAP_KEY_4, Integer.valueOf(5));
        this.lwjglMap.put(KEYMAP_KEY_5, Integer.valueOf(6));
        this.lwjglMap.put(KEYMAP_KEY_6, Integer.valueOf(7));
        this.lwjglMap.put(KEYMAP_KEY_7, Integer.valueOf(8));
        this.lwjglMap.put(KEYMAP_KEY_8, Integer.valueOf(9));
        this.lwjglMap.put(KEYMAP_KEY_9, Integer.valueOf(10));
        this.lwjglMap.put(KEYMAP_KEY_A, Integer.valueOf(30));
        this.lwjglMap.put(KEYMAP_KEY_B, Integer.valueOf(48));
        this.lwjglMap.put(KEYMAP_KEY_C, Integer.valueOf(46));
        this.lwjglMap.put(KEYMAP_KEY_D, Integer.valueOf(32));
        this.lwjglMap.put(KEYMAP_KEY_E, Integer.valueOf(18));
        this.lwjglMap.put(KEYMAP_KEY_F, Integer.valueOf(33));
        this.lwjglMap.put(KEYMAP_KEY_G, Integer.valueOf(34));
        this.lwjglMap.put(KEYMAP_KEY_H, Integer.valueOf(35));
        this.lwjglMap.put(KEYMAP_KEY_I, Integer.valueOf(23));
        this.lwjglMap.put(KEYMAP_KEY_J, Integer.valueOf(36));
        this.lwjglMap.put(KEYMAP_KEY_K, Integer.valueOf(37));
        this.lwjglMap.put(KEYMAP_KEY_L, Integer.valueOf(38));
        this.lwjglMap.put(KEYMAP_KEY_M, Integer.valueOf(50));
        this.lwjglMap.put(KEYMAP_KEY_N, Integer.valueOf(49));
        this.lwjglMap.put(KEYMAP_KEY_O, Integer.valueOf(24));
        this.lwjglMap.put(KEYMAP_KEY_P, Integer.valueOf(25));
        this.lwjglMap.put(KEYMAP_KEY_Q, Integer.valueOf(16));
        this.lwjglMap.put(KEYMAP_KEY_R, Integer.valueOf(19));
        this.lwjglMap.put(KEYMAP_KEY_S, Integer.valueOf(31));
        this.lwjglMap.put(KEYMAP_KEY_T, Integer.valueOf(20));
        this.lwjglMap.put(KEYMAP_KEY_U, Integer.valueOf(22));
        this.lwjglMap.put(KEYMAP_KEY_V, Integer.valueOf(47));
        this.lwjglMap.put(KEYMAP_KEY_W, Integer.valueOf(17));
        this.lwjglMap.put(KEYMAP_KEY_X, Integer.valueOf(45));
        this.lwjglMap.put(KEYMAP_KEY_Y, Integer.valueOf(21));
        this.lwjglMap.put(KEYMAP_KEY_Z, Integer.valueOf(44));
        this.lwjglMap.put(KEYMAP_KEY_MINUS, Integer.valueOf(12));
        this.lwjglMap.put(KEYMAP_KEY_EQUALS, Integer.valueOf(13));
        this.lwjglMap.put(KEYMAP_KEY_LBRACKET, Integer.valueOf(26));
        this.lwjglMap.put(KEYMAP_KEY_RBRACKET, Integer.valueOf(27));
        this.lwjglMap.put(KEYMAP_KEY_SEMICOLON, Integer.valueOf(39));
        this.lwjglMap.put(KEYMAP_KEY_APOSTROPHE, Integer.valueOf(40));
        this.lwjglMap.put(KEYMAP_KEY_GRAVE, Integer.valueOf(41));
        this.lwjglMap.put(KEYMAP_KEY_BACKSLASH, Integer.valueOf(43));
        this.lwjglMap.put(KEYMAP_KEY_COMMA, Integer.valueOf(51));
        this.lwjglMap.put(KEYMAP_KEY_PERIOD, Integer.valueOf(52));
        this.lwjglMap.put("/", Integer.valueOf(53));
        this.lwjglMap.put(KEYMAP_KEY_ESC, Integer.valueOf(1));
        this.lwjglMap.put(KEYMAP_KEY_F1, Integer.valueOf(59));
        this.lwjglMap.put(KEYMAP_KEY_F2, Integer.valueOf(60));
        this.lwjglMap.put(KEYMAP_KEY_F3, Integer.valueOf(61));
        this.lwjglMap.put(KEYMAP_KEY_F4, Integer.valueOf(62));
        this.lwjglMap.put(KEYMAP_KEY_F5, Integer.valueOf(63));
        this.lwjglMap.put(KEYMAP_KEY_F6, Integer.valueOf(64));
        this.lwjglMap.put(KEYMAP_KEY_F7, Integer.valueOf(65));
        this.lwjglMap.put(KEYMAP_KEY_F8, Integer.valueOf(66));
        this.lwjglMap.put(KEYMAP_KEY_F9, Integer.valueOf(67));
        this.lwjglMap.put(KEYMAP_KEY_F10, Integer.valueOf(68));
        this.lwjglMap.put(KEYMAP_KEY_F11, Integer.valueOf(87));
        this.lwjglMap.put(KEYMAP_KEY_F12, Integer.valueOf(88));
        this.lwjglMap.put(KEYMAP_KEY_TAB, Integer.valueOf(15));
        this.lwjglMap.put(KEYMAP_KEY_BACKSPACE, Integer.valueOf(14));
        this.lwjglMap.put(KEYMAP_KEY_SPACE, Integer.valueOf(57));
        this.lwjglMap.put(KEYMAP_KEY_CAPITAL, Integer.valueOf(58));
        this.lwjglMap.put(KEYMAP_KEY_ENTER, Integer.valueOf(28));
        this.lwjglMap.put(KEYMAP_KEY_LSHIFT, Integer.valueOf(42));
        this.lwjglMap.put(KEYMAP_KEY_LCTRL, Integer.valueOf(29));
        this.lwjglMap.put(KEYMAP_KEY_LALT, Integer.valueOf(56));
        this.lwjglMap.put(KEYMAP_KEY_RSHIFT, Integer.valueOf(54));
        this.lwjglMap.put(KEYMAP_KEY_RCTRL, Integer.valueOf(Keyboard.KEY_RCONTROL));
        this.lwjglMap.put(KEYMAP_KEY_RALT, Integer.valueOf(184));
        this.lwjglMap.put(KEYMAP_KEY_UP, Integer.valueOf(200));
        this.lwjglMap.put(KEYMAP_KEY_DOWN, Integer.valueOf(Keyboard.KEY_DOWN));
        this.lwjglMap.put(KEYMAP_KEY_LEFT, Integer.valueOf(Keyboard.KEY_LEFT));
        this.lwjglMap.put(KEYMAP_KEY_RIGHT, Integer.valueOf(Keyboard.KEY_RIGHT));
        this.lwjglMap.put(KEYMAP_KEY_PAGEUP, Integer.valueOf(Keyboard.KEY_PRIOR));
        this.lwjglMap.put(KEYMAP_KEY_PAGEDOWN, Integer.valueOf(Keyboard.KEY_NEXT));
        this.lwjglMap.put(KEYMAP_KEY_HOME, Integer.valueOf(Keyboard.KEY_HOME));
        this.lwjglMap.put(KEYMAP_KEY_END, Integer.valueOf(Keyboard.KEY_END));
        this.lwjglMap.put(KEYMAP_KEY_INSERT, Integer.valueOf(Keyboard.KEY_INSERT));
        this.lwjglMap.put(KEYMAP_KEY_DELETE, Integer.valueOf(Keyboard.KEY_DELETE));
        this.lwjglMap.put(KEYMAP_KEY_PAUSE, Integer.valueOf(Keyboard.KEY_PAUSE));
        this.lwjglMap.put(KEYMAP_KEY_NUMPAD0, Integer.valueOf(82));
        this.lwjglMap.put(KEYMAP_KEY_NUMPAD1, Integer.valueOf(79));
        this.lwjglMap.put(KEYMAP_KEY_NUMPAD2, Integer.valueOf(80));
        this.lwjglMap.put(KEYMAP_KEY_NUMPAD3, Integer.valueOf(81));
        this.lwjglMap.put(KEYMAP_KEY_NUMPAD4, Integer.valueOf(75));
        this.lwjglMap.put(KEYMAP_KEY_NUMPAD5, Integer.valueOf(76));
        this.lwjglMap.put(KEYMAP_KEY_NUMPAD6, Integer.valueOf(77));
        this.lwjglMap.put(KEYMAP_KEY_NUMPAD7, Integer.valueOf(71));
        this.lwjglMap.put(KEYMAP_KEY_NUMPAD8, Integer.valueOf(72));
        this.lwjglMap.put(KEYMAP_KEY_NUMPAD9, Integer.valueOf(73));
        this.lwjglMap.put(KEYMAP_KEY_NUMLOCK, Integer.valueOf(69));
        this.lwjglMap.put(KEYMAP_KEY_SCROLL, Integer.valueOf(70));
        this.lwjglMap.put(KEYMAP_KEY_SUBTRACT, Integer.valueOf(74));
        this.lwjglMap.put(KEYMAP_KEY_ADD, Integer.valueOf(78));
        this.lwjglMap.put(KEYMAP_KEY_DECIMAL, Integer.valueOf(83));
        this.lwjglMap.put(KEYMAP_KEY_NUMPADENTER, Integer.valueOf(Keyboard.KEY_NUMPADENTER));
        this.lwjglMap.put(KEYMAP_KEY_DIVIDE, Integer.valueOf(Keyboard.KEY_DIVIDE));
        this.lwjglMap.put(KEYMAP_KEY_MULTIPLY, Integer.valueOf(55));
        this.lwjglMap.put(KEYMAP_KEY_PRINT, Integer.valueOf(183));
        this.lwjglMap.put(KEYMAP_KEY_LWIN, Integer.valueOf(219));
        this.lwjglMap.put(KEYMAP_KEY_RWIN, Integer.valueOf(220));
    }

    public Object translate(Object obj) {
        if (this.lwjglMap.containsKey(obj)) {
            return this.lwjglMap.get(obj);
        }
        return Integer.valueOf(-1);
    }
}
