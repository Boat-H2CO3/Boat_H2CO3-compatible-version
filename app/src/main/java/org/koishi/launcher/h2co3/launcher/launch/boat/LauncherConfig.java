package org.koishi.launcher.h2co3.launcher.launch.boat;

import static cosine.boat.utils.CHTools.LAUNCHER_DATA_USER_DIR;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;
import org.koishi.launcher.h2co3.tools.Utils;
import org.koishi.launcher.h2co3.tools.launch.MinecraftVersion;
import org.koishi.launcher.h2co3.tools.launch.TouchInjector;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.Vector;

import cosine.boat.utils.CHTools;


public class LauncherConfig extends HashMap<String, String> {


    public final boolean touchInjector = false;
    public final String extraJavaFlags;
    public final String extraMinecraftFlags;

    public LauncherConfig() {
        this.extraJavaFlags = "";
        this.extraMinecraftFlags = "";
    }

    public static void toFile(String filePath, LauncherConfig config) {
        try {
            //Log.i("Launcher", "Trying to save config to file.");
            Utils.writeFile(filePath, new Gson().toJson(config, LauncherConfig.class));

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }


    }

    public static LauncherConfig fromFile(String filePath) {
        try {
            return new Gson().fromJson(new String(Utils.readFile(filePath), StandardCharsets.UTF_8), LauncherConfig.class);

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String privateDir() {
        try {
            FileInputStream in = new FileInputStream(CHTools.H2CO3CfgPath());
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();
            String str = new String(b);
            JSONObject json = new JSONObject(str);
            return json.getString("allVerLoad");
        } catch (Exception e) {
            System.out.println(e);
        }
        return "false";
    }

    public static String loadgl() {
        try {
            FileInputStream in = new FileInputStream(CHTools.H2CO3CfgPath());
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();
            String str = new String(b);
            JSONObject json = new JSONObject(str);
            return json.getString("openGL");
        } catch (Exception e) {
            System.out.println(e);
        }
        return "libGL114";
    }

    public static String loadjava() {
        try {
            FileInputStream in = new FileInputStream(CHTools.H2CO3CfgPath());
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();
            String str = new String(b);
            JSONObject json = new JSONObject(str);
            return json.getString("java");
        } catch (Exception e) {
            System.out.println(e);
        }
        return "jre_8";
    }

    public static String api() {
        try {
            FileInputStream in = new FileInputStream(CHTools.H2CO3CfgPath());
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();
            String str = new String(b);
            JSONObject json = new JSONObject(str);
            return json.getString("LoginApi");
        } catch (Exception e) {
            System.out.println(e);
        }
        return "Microsoft";
    }

    public static Vector<String> getMcArgs(LauncherConfig config, Context context, int width, int height) {
        try {
            MinecraftVersion version = MinecraftVersion.fromDirectory(new File(CHTools.getBoatCfg("currentVersion", "Null")));
            String runtimePath = CHTools.getBoatCfg("runtimePath", "");
            String lwjglPath = CHTools.getBoatCfg("runtimePath", "") + "/boat";
            String server = "server";
            // 识别表单
            String javaPath;
            String java = LauncherConfig.loadjava();
            // Java版本选择
            if (java.equals("jre_8")) {
                javaPath = runtimePath + "/jre_8";
                Log.w("JRE", "当前使用JRE8");
            } else {
                javaPath = runtimePath + "/jre_17";
                Log.w("JRE", "当前使用JRE17");
            }
            boolean highVersion = version.minimumLauncherVersion >= 21;
            String libraryPath;
            String classPath = null;
            String r = loadgl().equals("VirGL") ? "virgl" : "gl4es";
            boolean isJava17 = javaPath.endsWith("jre_17");
            if (!highVersion) {
                libraryPath = javaPath + "/lib/aarch64/jli:" + javaPath + "/lib/aarch64:" + lwjglPath + ":" + lwjglPath + "/lwjgl-2:" + lwjglPath + "/" + r;
                classPath = lwjglPath + "/lwjgl-2/lwjgl.jar:" + lwjglPath + "/lwjgl-2/lwjgl_util.jar:" + version.getClassPath(config, false, false);
            } else {
                if (isJava17) {
                    libraryPath = javaPath + "/lib:" + lwjglPath + ":" + lwjglPath + "/lwjgl-3:" + lwjglPath + "/" + r;
                } else {
                    libraryPath = javaPath + "/lib/jli:" + javaPath + "/lib:" + lwjglPath + ":" + lwjglPath + "/lwjgl-3:" + lwjglPath + "/" + r;
                }
                classPath = lwjglPath + "/lwjgl-3/lwjgl-jemalloc.jar:" + lwjglPath + "/lwjgl-3/lwjgl-tinyfd.jar:" + lwjglPath + "/lwjgl-3/lwjgl-opengl.jar:" + lwjglPath + "/lwjgl-3/lwjgl-openal.jar:" + lwjglPath + "/lwjgl-3/lwjgl-glfw.jar:" + lwjglPath + "/lwjgl-3/lwjgl-stb.jar:" + lwjglPath + "/lwjgl-3/lwjgl.jar:" + version.getClassPath(config, true, isJava17);
            }
            Vector<String> args = new Vector<>();
            args.add(javaPath + "/bin/java");
            if (!isJava17) {
                // Caciocavallo config AWT-enabled version
                args.add("-Djava.awt.headless=false");
                args.add("-Dcacio.managed.screensize=" + width + "x" + height);
                //args.add("-Dcacio.font.fontmanager=net.java.openjdk.cacio.ctc.CTCFontManager");
                args.add("-Dcacio.font.fontscaler=sun.font.FreetypeFontScaler");
                args.add("-Dswing.defaultlaf=javax.swing.plaf.metal.MetalLookAndFeel");
                if (!isJava17) {
                    args.add("-Dcacio.font.fontmanager=sun.awt.X11FontManager");
                    args.add("-Dawt.toolkit=net.java.openjdk.cacio.ctc.CTCToolkit");
                    args.add("-Djava.awt.graphicsenv=net.java.openjdk.cacio.ctc.CTCGraphicsEnvironment");
                } else {
                    args.add("-Dcacio.font.fontmanager=com.github.caciocavallosilano.cacio.ctc.CTCFontManager");
                    args.add("-Dawt.toolkit=com.github.caciocavallosilano.cacio.ctc.CTCToolkit");
                    args.add("-Djava.awt.graphicsenv=com.github.caciocavallosilano.cacio.ctc.CTCGraphicsEnvironment");
                    args.add("-Djava.system.class.loader=com.github.caciocavallosilano.cacio.ctc.CTCPreloadClassLoader");
                    args.add("--add-exports=java.desktop/java.awt=ALL-UNNAMED");
                    args.add("--add-exports=java.desktop/java.awt.peer=ALL-UNNAMED");
                    args.add("--add-exports=java.desktop/sun.awt.image=ALL-UNNAMED");
                    args.add("--add-exports=java.desktop/sun.java2d=ALL-UNNAMED");
                    args.add("--add-exports=java.desktop/java.awt.dnd.peer=ALL-UNNAMED");
                    args.add("--add-exports=java.desktop/sun.awt=ALL-UNNAMED");
                    args.add("--add-exports=java.desktop/sun.awt.event=ALL-UNNAMED");
                    args.add("--add-exports=java.desktop/sun.awt.datatransfer=ALL-UNNAMED");
                    args.add("--add-exports=java.desktop/sun.font=ALL-UNNAMED");
                    args.add("--add-exports=java.base/sun.security.action=ALL-UNNAMED");
                    args.add("--add-opens=java.base/java.util=ALL-UNNAMED");
                    args.add("--add-opens=java.desktop/java.awt=ALL-UNNAMED");
                    args.add("--add-opens=java.desktop/sun.font=ALL-UNNAMED");
                    args.add("--add-opens=java.desktop/sun.java2d=ALL-UNNAMED");
                    args.add("--add-opens=java.base/java.lang.reflect=ALL-UNNAMED");

                    // Opens the java.net package to Arc DNS injector on Java 9+
                    args.add("--add-opens=java.base/java.net=ALL-UNNAMED");
                }

                StringBuilder cacioClasspath = new StringBuilder();
                cacioClasspath.append("-Xbootclasspath/" + (!isJava17 ? "p" : "a"));
                File cacioDir = new File(lwjglPath + "/plugin" + "/caciocavallo" + (!isJava17 ? "" : "17"));
                if (cacioDir.exists() && cacioDir.isDirectory()) {
                    for (File file : cacioDir.listFiles()) {
                        if (file.getName().endsWith(".jar")) {
                            cacioClasspath.append(":" + file.getAbsolutePath());
                        }
                    }
                }
                args.add(cacioClasspath.toString());
            }
            args.add("-cp");
            args.add(classPath);
            args.add("-Djava.library.path=" + libraryPath);
            args.add("-Dfml.earlyprogresswindow=false");
            args.add("-Dorg.lwjgl.util.DebugLoader=true");
            args.add("-Dorg.lwjgl.util.Debug=true");
            args.add("-Dos.name=Linux");
            args.add("-Dlwjgl.platform=Boat_H2CO3");
            args.add("-Duser.language=" + System.getProperty("user.language"));
            if (loadgl().equals("VirGL")) {
                args.add("-Dorg.lwjgl.opengl.libname=libGL.so.1");
            } else {
                args.add("-Dorg.lwjgl.opengl.libname=libgl4es_114.so");
            }
            args.add("-Dlwjgl.platform=Boat_H2CO3");
            args.add("-Dos.name=Linux");
            args.add("-Djava.io.tmpdir=" + LAUNCHER_DATA_USER_DIR);
            String[] accountArgs;
            accountArgs = new String[0];
            Collections.addAll(args, accountArgs);
            String[] JVMArgs;
            JVMArgs = version.getJVMArguments(config);
            for (int i = 0; i < JVMArgs.length; i++) {
                if (JVMArgs[i].startsWith("-DignoreList") && !JVMArgs[i].endsWith("," + new File(CHTools.getBoatCfg("currentVersion", "Null")).getName() + ".jar")) {
                    JVMArgs[i] = JVMArgs[i] + "," + new File(CHTools.getBoatCfg("currentVersion", "Null")).getName() + ".jar";
                }
                if (!JVMArgs[i].startsWith("-DFabricMcEmu") && !JVMArgs[i].startsWith("net.minecraft.boat.main.Main")) {
                    args.add(JVMArgs[i]);
                }
            }
            args.add("-Xms" + "1024" + "M");
            args.add("-Xmx" + "6000" + "M");
            if (!config.extraJavaFlags.equals("")) {
                String[] extraJavaFlags = config.extraJavaFlags.split(" ");
                Collections.addAll(args, extraJavaFlags);
            }
            args.add(version.mainClass);
            String[] minecraftArgs;
            minecraftArgs = version.getMinecraftArguments(config, highVersion);
            Collections.addAll(args, minecraftArgs);
            args.add("--width");
            args.add(Integer.toString(width));
            args.add("--height");
            args.add(Integer.toString(height));
            String[] extraMinecraftArgs = config.extraMinecraftFlags.split(" ");
            Collections.addAll(args, extraMinecraftArgs);
            return TouchInjector.rebaseArguments(config, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String get(String key) {
        String value;

        if (super.get(key) == null) {
            value = "";
            Log.w("Boat", "Value required can not found: key=" + key);
        } else {
            value = super.get(key);
        }
        return value;
    }

}
