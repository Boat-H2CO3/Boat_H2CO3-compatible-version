package org.koishi.launcher.h2co3.tools;

import org.koishi.launcher.h2co3.launcher.control.gamecontroller.event.BaseKeyEvent;

public interface Controller {

    void sendKey(BaseKeyEvent event);
}
