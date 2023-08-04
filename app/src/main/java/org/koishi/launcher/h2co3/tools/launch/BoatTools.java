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
            if (isJava8) {
                javaArgList.add("-Dcacio.font.fontmanager=sun.awt.X11FontManager");
                javaArgList.add("-Dawt.toolkit=net.java.openjdk.cacio.ctc.CTCToolkit");
                javaArgList.add("-Djava.awt.graphicsenv=net.java.openjdk.cacio.ctc.CTCGraphicsEnvironment");
            } else {
                javaArgList.add("-Dcacio.font.fontmanager=com.github.caciocavallosilano.cacio.ctc.CTCFontManager");
                javaArgList.add("-Dawt.toolkit=com.github.caciocavallosilano.cacio.ctc.CTCToolkit");
                javaArgList.add("-Djava.awt.graphicsenv=com.github.caciocavallosilano.cacio.ctc.CTCGraphicsEnvironment");
                javaArgList.add("-Djava.system.class.loader=com.github.caciocavallosilano.cacio.ctc.CTCPreloadClassLoader");

                javaArgList.add("--add-exports=java.desktop/java.awt=ALL-UNNAMED");
                javaArgList.add("--add-exports=java.desktop/java.awt.peer=ALL-UNNAMED");
                javaArgList.add("--add-exports=java.desktop/sun.awt.image=ALL-UNNAMED");
                javaArgList.add("--add-exports=java.desktop/sun.java2d=ALL-UNNAMED");
                javaArgList.add("--add-exports=java.desktop/java.awt.dnd.peer=ALL-UNNAMED");
                javaArgList.add("--add-exports=java.desktop/sun.awt=ALL-UNNAMED");
                javaArgList.add("--add-exports=java.desktop/sun.awt.event=ALL-UNNAMED");
                javaArgList.add("--add-exports=java.desktop/sun.awt.datatransfer=ALL-UNNAMED");
                javaArgList.add("--add-exports=java.desktop/sun.font=ALL-UNNAMED");
                javaArgList.add("--add-exports=java.base/sun.security.action=ALL-UNNAMED");
                javaArgList.add("--add-opens=java.base/java.util=ALL-UNNAMED");
                javaArgList.add("--add-opens=java.desktop/java.awt=ALL-UNNAMED");
                javaArgList.add("--add-opens=java.desktop/sun.font=ALL-UNNAMED");
                javaArgList.add("--add-opens=java.desktop/sun.java2d=ALL-UNNAMED");
                javaArgList.add("--add-opens=java.base/java.lang.reflect=ALL-UNNAMED");

                // Opens the java.net package to Arc DNS injector on Java 9+
                javaArgList.add("--add-opens=java.base/java.net=ALL-UNNAMED");
            }

            StringBuilder cacioClasspath = new StringBuilder();
            cacioClasspath.append("-Xbootclasspath/").append(isJava8 ? "p" : "a");
            File cacioDir = new File(context.getDir("runtime",0).getAbsolutePath() + "/boat" + "/plugin/caciocavallo" + (isJava8 ? "" : "17"));
            if (cacioDir.exists() && cacioDir.isDirectory()) {
                for (File file : Objects.requireNonNull(cacioDir.listFiles())) {
                    if (file.getName().endsWith(".jar")) {
                        cacioClasspath.append(":").append(file.getAbsolutePath());
                    }
                }
            }
            Log.d("AAAAAAA",cacioClasspath.toString());
            javaArgList.add(cacioClasspath.toString());
        }
    }
}
