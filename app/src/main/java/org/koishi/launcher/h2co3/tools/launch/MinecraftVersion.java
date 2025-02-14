package org.koishi.launcher.h2co3.tools.launch;

import android.util.ArrayMap;
import android.util.Log;

import com.google.gson.Gson;

import org.koishi.launcher.h2co3.launcher.launch.boat.LauncherConfig;
import org.koishi.launcher.h2co3.tools.Utils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.koishi.launcher.h2co3core.utils.cainiaohh.CHTools;

public class MinecraftVersion {
    public AssetsIndex assetIndex;
    public String assets;
    public HashMap<String, Download> downloads;
    public String id;
    public Library[] libraries;
    public String mainClass;
    public String minecraftArguments;
    public int minimumLauncherVersion;
    public String releaseTime;
    public String time;
    public String type;
    public Arguments arguments;
    // forge
    public String inheritsFrom;
    public String minecraftPath;
    private Map<String, String> SHAs;

    public static MinecraftVersion fromDirectory(File file) {

        String json = new String(Utils.readFile(new File(file, file.getName() + ".json")), StandardCharsets.UTF_8);
        MinecraftVersion result = new Gson().fromJson(json, MinecraftVersion.class);
        if (new File(file, file.getName() + ".jar").exists()) {
            result.minecraftPath = new File(file, file.getName() + ".jar").getAbsolutePath();
        } else {
            result.minecraftPath = "";
        }

        if (result.inheritsFrom != null && !result.inheritsFrom.equals("")) {

            MinecraftVersion self = result;
            result = MinecraftVersion.fromDirectory(new File(file.getParentFile(), self.inheritsFrom));

            if (self.assetIndex != null) {
                result.assetIndex = self.assetIndex;
            }
            if (self.assets != null && !self.assets.equals("")) {
                result.assets = self.assets;
            }
            if (self.downloads != null && !self.downloads.isEmpty()) {

                if (result.downloads == null) {
                    result.downloads = new HashMap<>();
                }

                result.downloads.putAll(self.downloads);
            }
            if (self.libraries != null && self.libraries.length > 0) {
                Library[] newLibs = new Library[result.libraries.length + self.libraries.length];
                int i = 0;
                for (Library lib : self.libraries) {
                    newLibs[i] = lib;
                    i++;
                }
                for (Library lib : result.libraries) {
                    newLibs[i] = lib;
                    i++;
                }


                result.libraries = newLibs;
            }

            if (self.mainClass != null && !self.mainClass.equals("")) {
                result.mainClass = self.mainClass;
            }
            if (self.minecraftArguments != null && !self.minecraftArguments.equals("")) {
            }

            if (self.minimumLauncherVersion > result.minimumLauncherVersion) {
                result.minimumLauncherVersion = self.minimumLauncherVersion;
            }
            if (self.releaseTime != null && !self.releaseTime.equals("")) {
                result.releaseTime = self.releaseTime;
            }
            if (self.time != null && !self.time.equals("")) {
                result.time = self.time;
            }
            if (self.type != null && !self.type.equals("")) {
                result.type = self.type;
            }

            if (self.minecraftPath != null && !self.minecraftPath.equals("")) {
                result.minecraftPath = self.minecraftPath;
            }

        }
        result.minecraftArguments = "--username ${auth_player_name} --version ${version_name} --gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} --uuid ${auth_uuid} --accessToken ${auth_access_token} --userProperties ${user_properties} --userType ${user_type} --versionType ${version_type}";

        return result;

    }

    public String getClassPath(LauncherConfig config, boolean high, boolean isJava17) {
        StringBuilder cp = new StringBuilder();
        int count = 0;

        String libraries_path = CHTools.getBoatCfg("game_directory", "") + "/libraries/";

        for (Library lib : this.libraries) {
            if (lib.name == null || lib.name.equals("") || lib.name.contains("org.lwjgl") || lib.name.contains("natives") || (isJava17 && lib.name.contains("java-objc-bridge"))) {
                continue;
            }

            String[] names = lib.name.split(":");
            String packageName = names[0];
            String mainName = names[1];
            String versionName = names[2];

            String path = "";

            path = path + libraries_path;

            path = path + packageName.replaceAll("\\.", "/");

            path = path + "/";

            path = path + mainName;

            path = path + "/";

            path = path + versionName;

            path = path + "/" + mainName + "-" + versionName + ".jar";
            Log.e("路径", path);


            if (count > 0) {
                cp.append(":");
            }
            cp.append(path);
            count++;
        }
        String split = count > 0 ? ":" : "";
        if (high) {
            cp.append(split).append(minecraftPath);
        } else {
            cp.insert(0, minecraftPath + split);
        }
        cp.insert(0, minecraftPath);

        return cp.toString();
    }

    public String[] getJVMArguments(LauncherConfig gameLaunchSetting) {
        StringBuilder test = new StringBuilder();
        if (arguments != null && arguments.jvm != null) {
            Object[] jvmObjs = this.arguments.jvm;
            for (Object obj : jvmObjs) {
                if (obj instanceof String && !((String) obj).startsWith("-Djava.library.path") && !((String) obj).startsWith("-cp") && !((String) obj).startsWith("${classpath}")) {
                    test.append(obj).append(" ");
                }
            }
        } else {
            return new String[0];
        }
        StringBuilder result = new StringBuilder();

        int state = 0;
        int start = 0;
        int stop;
        for (int i = 0; i < test.length(); i++) {
            if (state == 0) {
                if (test.charAt(i) != '$') {
                    result.append(test.charAt(i));

                } else {
                    if (i + 1 < test.length() && test.charAt(i + 1) == '{') {
                        state = 1;
                        start = i;
                    } else {
                        result.append(test.charAt(i));
                    }
                }
            } else {
                if (test.charAt(i) == '}') {
                    stop = i;

                    String key = test.substring(start + 2, stop);

                    String value;


                    switch (key) {
                        case "version_name":
                            value = id;
                            break;
                        case "launcher_name":
                            value = "Boat";
                            break;
                        case "launcher_version":
                            value = "1.0.0";
                            break;
                        case "version_type":
                            value = type;
                            break;
                        case "assets_index_name":
                            if (assetIndex != null) {
                                value = assetIndex.id;
                            } else {
                                value = assets;
                            }
                            break;
                        case "game_directory":
                            value = CHTools.getBoatCfg("game_directory", "Null");
                            break;
                        case "assets_root":
                            value = CHTools.getBoatCfg("assets_root", "Null");
                            break;
                        case "user_properties":
                            value = "{}";
                            break;
                        case "auth_player_name":
                            value = CHTools.getBoatCfg("auth_player_name", "Null");
                            break;
                        case "auth_session":
                            value = CHTools.getBoatCfg("auth_session", "Null");
                            break;
                        case "auth_uuid":
                            value = CHTools.getBoatCfg("auth_uuid", "Null");
                            break;
                        case "auth_access_token":
                            value = CHTools.getBoatCfg("auth_access_token", "Null");
                            break;
                        case "user_type":
                            value = CHTools.getBoatCfg("user_type", "Null");
                            break;
                        case "primary_jar_name":
                            value = CHTools.getBoatCfg("currentVersion", "Null") + CHTools.getBoatCfg("MinecraftVersion", "Null") + ".jar";
                            break;
                        case "library_directory":
                            value = CHTools.getBoatCfg("game_directory", "Null") + "/libraries";
                            break;
                        case "classpath_separator":
                            value = ":";
                            break;
                        default:
                            value = "";
                            break;
                    }
                    result.append(value);
                    state = 0;
                }
            }
        }
        Log.e("路径", Arrays.toString(result.toString().split(" ")));
        return result.toString().split(" ");
    }

    public String[] getMinecraftArguments(LauncherConfig gameLaunchSetting, boolean isHighVer) {
        StringBuilder test = new StringBuilder();
        if (isHighVer) {
            Object[] objs = this.arguments.game;
            for (Object obj : objs) {
                if (obj instanceof String) {
                    test.append(obj).append(" ");
                }
            }
        } else {
            test = new StringBuilder(this.minecraftArguments);
        }
        StringBuilder result = new StringBuilder();

        int state = 0;
        int start = 0;
        int stop;
        for (int i = 0; i < test.length(); i++) {
            if (state == 0) {
                if (test.charAt(i) != '$') {
                    result.append(test.charAt(i));

                } else {
                    if (i + 1 < test.length() && test.charAt(i + 1) == '{') {
                        state = 1;
                        start = i;
                    } else {
                        result.append(test.charAt(i));
                    }
                }
            } else {
                if (test.charAt(i) == '}') {
                    stop = i;

                    String key = test.substring(start + 2, stop);

                    String value;

                    switch (key) {
                        case "version_name":
                            value = id;
                            break;
                        case "launcher_name":
                            value = "Boat";
                            break;
                        case "launcher_version":
                            value = "1.0.0";
                            break;
                        case "version_type":
                            value = type;
                            break;
                        case "assets_index_name":
                            if (assetIndex != null) {
                                value = assetIndex.id;
                            } else {
                                value = assets;
                            }
                            break;
                        case "game_directory":
                            value = CHTools.getBoatCfg("game_directory", "Null");
                            break;
                        case "assets_root":
                            value = CHTools.getBoatCfg("assets_root", "Null");
                            break;
                        case "user_properties":
                            value = "{}";
                            break;
                        case "auth_player_name":
                            value = CHTools.getBoatCfg("auth_player_name", "Null");
                            break;
                        case "auth_session":
                            value = CHTools.getBoatCfg("auth_session", "Null");
                            break;
                        case "auth_uuid":
                            value = CHTools.getBoatCfg("auth_uuid", "Null");
                            break;
                        case "auth_access_token":
                            value = CHTools.getBoatCfg("auth_access_token", "Null");
                            break;
                        case "user_type":
                            value = CHTools.getBoatCfg("user_type", "Null");
                            break;
                        case "primary_jar_name":
                            value = CHTools.getBoatCfg("currentVersion", "Null") + CHTools.getBoatCfg("MinecraftVersion", "Null") + ".jar";
                            break;
                        case "library_directory":
                            value = CHTools.getBoatCfg("game_directory", "Null") + "/libraries";
                            break;
                        case "classpath_separator":
                            value = ":";
                            break;
                        default:
                            value = "";
                            break;
                    }
                    result.append(value);
                    state = 0;
                }
            }
        }
        if (!isHighVer && arguments != null && arguments.game != null) {
            Object[] objs = this.arguments.game;
            for (Object obj : objs) {
                if (obj instanceof String) {
                    result.append(" ").append(obj);
                }
            }
        }
        return result.toString().split(" ");
    }

    public List<String> getLibraries() {
        List<String> libs = new ArrayList<>();
        for (Library lib : this.libraries) {
            if (lib.name == null || lib.name.equals("") || lib.name.contains("net.java.jinput") || lib.name.contains("org.lwjgl") || lib.name.contains("platform")) {
                continue;
            }
            libs.add(parseLibNameToPath(lib.name));
        }
        return libs;
    }

    public String getSHA1(String libName) {
        if (SHAs == null) {
            SHAs = new ArrayMap<>();
            for (Library lib : this.libraries) {
                if (lib.name == null || lib.name.equals("") || lib.name.contains("net.java.jinput") || lib.name.contains("org.lwjgl") || lib.name.contains("platform")) {
                    continue;
                }
                String sha1;
                try {
                    sha1 = Objects.requireNonNull(lib.downloads.get("artifact")).sha1;
                } catch (Exception e) {
                    continue;
                }
                SHAs.put(parseLibNameToPath(lib.name), sha1);
            }
        }
        return SHAs.get(libName);
    }

    public String parseLibNameToPath(String libName) {
        String[] tmp = libName.split(":");
        return tmp[0].replace(".", "/") + "/" + tmp[1] + "/" + tmp[2] + "/" + tmp[1] + "-" + tmp[2] + ".jar";
    }

    public static class AssetsIndex {
        public String id;
        public String sha1;
        public int size;
        public int totalSize;
        public String url;
    }

    public static class Download {
        public String path;
        public String sha1;
        public int size;
        public String url;
    }

    public static class Arguments {
        private Object[] game;
        private Object[] jvm;
    }

    public class Library {
        public String name;
        public HashMap<String, Download> downloads;
    }
}