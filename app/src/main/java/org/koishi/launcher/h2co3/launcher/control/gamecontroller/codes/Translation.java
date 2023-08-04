package org.koishi.launcher.h2co3.launcher.control.gamecontroller.codes;

public class Translation {
    private final CoKeyMap xKeyMap = new XKeyMap();

    public Translation(int i) {
    }

    public int trans(String str) {
        return ((Integer) this.xKeyMap.translate(str)).intValue();
    }
}
