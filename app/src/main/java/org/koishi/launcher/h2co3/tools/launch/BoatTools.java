package org.koishi.launcher.h2co3.tools.launch;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class BoatTools {
    public static void getCacioJavaArgs(Context context, List<String> javaArgList, boolean isJava8, int width, int height) {
        // Disable caciocavallo 17 for now
        if (isJava8) {
            // Caciocavallo config AWT-enabled version
            javaArgList.add("-Djava.awt.headless=false");
            javaArgList.add("-Dcacio.managed.screensize=" + width + "x" + height);
            // javaArgList.add("-Dcacio.font.fontmanager=net.java.openjdk.cacio.ctc.CTCFontManager");
            javaArgList.add("-Dcacio.font.fontscaler=sun.font.FreetypeFontScaler");
            javaArgList.add("-Dswing.defaultlaf=javax.swing.plaf.metal.MetalLookAndFeel");
            javaArgList.add("-Dcacio.font.fontmanager=sun.awt.X11FontManager");
            javaArgList.add("-Dawt.toolkit=net.java.openjdk.cacio.ctc.CTCToolkit");
            javaArgList.add("-Djava.awt.graphicsenv=net.java.openjdk.cacio.ctc.CTCGraphicsEnvironment");

            StringBuilder cacioClasspath = new StringBuilder();
            cacioClasspath.append("-Xbootclasspath/").append("p");
            File cacioDir = new File(context.getDir("runtime", 0).getAbsolutePath() + "/boat" + "/plugin/caciocavallo" + "");
            if (cacioDir.exists() && cacioDir.isDirectory()) {
                for (File file : Objects.requireNonNull(cacioDir.listFiles())) {
                    if (file.getName().endsWith(".jar")) {
                        cacioClasspath.append(":").append(file.getAbsolutePath());
                    }
                }
            }
            Log.d("AAAAAAA", cacioClasspath.toString());
            javaArgList.add(cacioClasspath.toString());
        }
    }
}
