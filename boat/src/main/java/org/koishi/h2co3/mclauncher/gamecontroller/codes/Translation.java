package org.koishi.h2co3.mclauncher.gamecontroller.codes;

public class Translation {
    private final CoKeyMap xKeyMap = new XKeyMap();

    public Translation(int i) {
    }

    public int trans(String str) {
        return ((Integer) this.xKeyMap.translate(str)).intValue();
    }
}
