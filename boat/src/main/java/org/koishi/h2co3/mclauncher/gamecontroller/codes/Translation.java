package org.koishi.h2co3.mclauncher.gamecontroller.codes;

import static org.koishi.h2co3.mclauncher.gamecontroller.definitions.id.key.KeyEvent.ANDROID_TO_KEYMAP;
import static org.koishi.h2co3.mclauncher.gamecontroller.definitions.id.key.KeyEvent.KEYMAP_TO_X;

public class Translation {

    private final CoKeyMap lwjglKeyTrans;
    private final CoKeyMap glfwKeyTrans;
    private final CoKeyMap xKeyMap;
    private final CoKeyMap aKeyMap;
    private int mode;

    public Translation(int mode) {
        lwjglKeyTrans = new LwjglKeyMap();
        glfwKeyTrans = new GlfwKeyMap();
        xKeyMap = new XKeyMap();
        aKeyMap = new AndroidKeyMap();
        this.mode = mode;
    }

    public short trans(String s) {
        //            case KEYMAP_TO_LWJGL:
        //                return (int) lwjglKeyTrans.translate(s);
        //            case KEYMAP_TO_GLFW:
        //                return (int) glfwKeyTrans.translate(s);
        if (mode == KEYMAP_TO_X) {
            return (short) xKeyMap.translate(s);
        }
        return -1;
    }

    public String trans(int i) {
        if (mode == ANDROID_TO_KEYMAP) {
            return (String) aKeyMap.translate(i);
        }
        return null;
    }

    public Translation setMode(int mode) {
        this.mode = mode;
        return this;
    }
}
