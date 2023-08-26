package org.koishi.launcher.h2co3.tools.launch;

import android.content.Context;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public final class Tools {

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
            cacioClasspath.append("-Xbootclasspath/" + "p");
            File cacioDir = new File(context.getDir("runtime", 0).getAbsolutePath() + "/caciocavallo" + "");
            if (cacioDir.exists() && cacioDir.isDirectory()) {
                for (File file : Objects.requireNonNull(cacioDir.listFiles())) {
                    if (file.getName().endsWith(".jar")) {
                        cacioClasspath.append(":").append(file.getAbsolutePath());
                    }
                }
            }
            javaArgList.add(cacioClasspath.toString());
        }
    }

    public static String read(InputStream is) throws IOException {
        StringBuilder out = new StringBuilder();
        int len;
        byte[] buf = new byte[512];
        while ((len = is.read(buf)) != -1) {
            out.append(new String(buf, 0, len));
        }
        return out.toString();
    }

    public static String read(String path) throws IOException {
        return read(new FileInputStream(path));
    }

    public static void write(String path, byte[] content) throws IOException {
        File outPath = new File(path);
        Objects.requireNonNull(outPath.getParentFile()).mkdirs();
        outPath.createNewFile();

        BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(path));
        fos.write(content, 0, content.length);
        fos.close();
    }

    public static void write(String path, String content) throws IOException {
        write(path, content.getBytes());
    }

}