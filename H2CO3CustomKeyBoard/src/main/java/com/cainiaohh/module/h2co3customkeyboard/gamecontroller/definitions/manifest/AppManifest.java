package com.cainiaohh.module.h2co3customkeyboard.gamecontroller.definitions.manifest;

import static cosine.boat.utils.CHTools.LAUNCHER_DATA_DIR;
import static cosine.boat.utils.CHTools.LAUNCHER_FILE_DIR;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.util.Objects;

public class AppManifest {

    //文件过滤清单
    public final static String[] BOAT_RUNTIME_FILTERED_LIBRARIES = {
            "lwjgl", "lwjgl_util", "lwjgl-platform", "lwjgl-egl", "lwjgl-glfw",
            "lwjgl-jemalloc", "lwjgl-openal", "lwjgl-opengl",
            "lwjgl-opengles", "lwjgl-stb", "lwjgl-tinyfd", "jinput-platform",
            "twitch-platform", "twitch-external-platform"};
    private final static String TAG = "AppManifest";
    /**
     * 【全局目录清单】
     **/
    public static String APP_NAME;
    public static String DATA_HOME;
    public static String SDCARD_HOME;
    public static String Boat_H2CO3_HOME;
    public static String Boat_H2CO3_TEMP;
    public static String Boat_H2CO3_KEYBOARD;
    public static String Boat_H2CO3_SETTING_JSON;
    public static String Boat_H2CO3_VERSION_NAME;
    public static String Boat_H2CO3_RUNTIME;
    public static String Boat_H2CO3_BACKGROUND;
    public static String BOAT_CACHE_HOME;
    public static String BOAT_LOG_FILE;
    public static String RUNTIME_HOME;
    public static String BOAT_RUNTIME_HOME;
    public static String BOAT_RUNTIME_INFO_JSON;
    public static String FORGE_HOME;
    public static String AUTHLIB_HOME;
    public static String AUTHLIB_INJETOR_JAR;
    public static String MINECRAFT_HOME;
    public static String MINECRAFT_VERSIONS;
    public static String MINECRAFT_ASSETS;
    public static String MINECRAFT_MODS;
    public static String MINECRAFT_SAVES;
    public static String MINECRAFT_LIBRARIES;
    public static String MINECRAFT_LOGS;
    public static String MINECRAFT_RESOURCEPACKS;

    public static void initManifest(Context context, String mchome) {
        APP_NAME = "Boat_H2CO3";
        DATA_HOME = context.getFilesDir().getAbsolutePath();
        SDCARD_HOME = Environment.getExternalStorageDirectory().getAbsolutePath();
        Boat_H2CO3_HOME = LAUNCHER_FILE_DIR;
        Boat_H2CO3_TEMP = LAUNCHER_DATA_DIR + "/temp";
        Boat_H2CO3_KEYBOARD = LAUNCHER_DATA_DIR + "/Keyboards";
        Boat_H2CO3_SETTING_JSON = Boat_H2CO3_HOME + "/h2o3cfg.json";
        Boat_H2CO3_RUNTIME = LAUNCHER_DATA_DIR + "/app_runtime";
        Boat_H2CO3_BACKGROUND = LAUNCHER_DATA_DIR + "/backgrounds";
        try {
            Boat_H2CO3_VERSION_NAME = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Boat_H2CO3_VERSION_NAME = "Unknown";
        }
        BOAT_CACHE_HOME = Boat_H2CO3_HOME + "/boat";
        BOAT_LOG_FILE = BOAT_CACHE_HOME + "/boat_output.txt";
        RUNTIME_HOME = DATA_HOME + "/runtime";
        BOAT_RUNTIME_INFO_JSON = BOAT_RUNTIME_HOME + "/packinfo.json";
        FORGE_HOME = Boat_H2CO3_HOME + "/forge";
        AUTHLIB_HOME = Boat_H2CO3_HOME + "/authlib-injector";
        AUTHLIB_INJETOR_JAR = AUTHLIB_HOME + "/authlib-injector.jar";

        MINECRAFT_HOME = mchome;
        MINECRAFT_ASSETS = MINECRAFT_HOME + "/assets";
        MINECRAFT_LIBRARIES = MINECRAFT_HOME + "/libraries";
        MINECRAFT_LOGS = MINECRAFT_HOME + "/crach-reports";
        MINECRAFT_MODS = MINECRAFT_HOME + "/mods";
        MINECRAFT_RESOURCEPACKS = MINECRAFT_HOME + "/resourcepacks";
        MINECRAFT_SAVES = MINECRAFT_HOME + "/saves";
        MINECRAFT_VERSIONS = MINECRAFT_HOME + "/versions";
    }


    /**
     * 【一个全局目录的数组】
     **/
    public static String[] getAllPath() {
        return new String[]{Boat_H2CO3_RUNTIME, Boat_H2CO3_HOME, Boat_H2CO3_KEYBOARD, Boat_H2CO3_TEMP, BOAT_CACHE_HOME, RUNTIME_HOME, Boat_H2CO3_BACKGROUND, AUTHLIB_HOME};
    }
}
