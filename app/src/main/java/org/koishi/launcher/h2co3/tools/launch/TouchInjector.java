package org.koishi.launcher.h2co3.tools.launch;


import org.koishi.launcher.h2co3.launcher.launch.boat.LauncherConfig;

import java.util.Vector;

import org.koishi.launcher.h2co3core.utils.cainiaohh.CHTools;

public class TouchInjector {

    public static Vector<String> rebaseArguments(LauncherConfig gameLaunchSetting, Vector<String> args) {
        if (!gameLaunchSetting.touchInjector) {
            return args;
        }
        Vector<String> newArgs = new Vector<>();
        if (args.contains("Forge") || args.contains("cpw.mods.fml.common.launcher.FMLTweaker") || args.contains("fmlclient") || args.contains("forgeclient")) {
            if (args.contains("cpw.mods.bootstraplauncher.BootstrapLauncher")) {
                String version = "unknown";
                boolean hit = false;
                for (String arg : args) {
                    if (hit) {
                        if (arg.startsWith("--")) {
                            // arg doesn't seem to be a value
                            // maybe the previous argument is a value, but we wrongly recognized it as an option
                            hit = false;
                        } else {
                            if (arg.startsWith("1.17")) {
                                version = "1.17";
                            } else if (arg.startsWith("1.18")) {
                                version = "1.18";
                            } else if (arg.startsWith("1.19")) {
                                version = "1.19";
                            }
                            break;
                        }
                    }
                    if ("--assetIndex".equals(arg)) {
                        hit = true;
                    }
                }
                hit = false;
                for (int i = 0; i < args.size(); i++) {
                    if (hit) {
                        newArgs.add(args.get(i) + ":" + CHTools.APP_DATA_PATH + "/boat" + "/plugin/touch/TouchInjector-forge.jar");
                        hit = false;
                    } else if (args.get(i).startsWith("-Xms")) {
                        newArgs.add("-Dtouchinjector.version=" + version);
                        newArgs.add(args.get(i));
                    } else if (args.get(i).equals("-cp")) {
                        hit = true;
                        newArgs.add(args.get(i));
                    } else {
                        newArgs.add(args.get(i));
                    }
                }
            } else {
                for (int i = 0; i < args.size(); i++) {
                    if (args.get(i).startsWith("-Xms")) {
                        newArgs.add("-javaagent:" + CHTools.APP_DATA_PATH + "/boat" + "/plugin/touch/TouchInjector.jar=forge");
                    }
                    newArgs.add(args.get(i));
                }
            }
            return newArgs;
        } else if (args.contains("optifine.OptiFineTweaker") || args.contains("com.mumfrey.liteloader.launch.LiteLoaderTweaker")) {
            for (int i = 0; i < args.size(); i++) {
                if (args.get(i).startsWith("-Xms")) {
                    newArgs.add("-javaagent:" + CHTools.APP_DATA_PATH + "/boat" + "/plugin/touch/TouchInjector.jar=optifine");
                }
                newArgs.add(args.get(i));
            }
            return newArgs;
        } else if (args.contains("net.fabricmc.loader.impl.launch.knot.KnotClient")) {
            boolean hit = false;
            for (int i = 0; i < args.size(); i++) {
                if (hit) {
                    newArgs.add(args.get(i) + ":" + CHTools.APP_DATA_PATH + "/boat" + "/plugin/touch/TouchInjector.jar");
                    hit = false;
                } else if (args.get(i).equals("net.fabricmc.loader.impl.launch.knot.KnotClient")) {
                    newArgs.add("com.tungsten.touchinjector.launch.FabricKnotClient");
                } else if (args.get(i).equals("-cp")) {
                    hit = true;
                    newArgs.add(args.get(i));
                } else {
                    newArgs.add(args.get(i));
                }
            }
            return newArgs;
        } else if (args.contains("org.quiltmc.loader.impl.launch.knot.KnotClient")) {
            boolean hit = false;
            for (int i = 0; i < args.size(); i++) {
                if (hit) {
                    newArgs.add(args.get(i) + ":" + CHTools.APP_DATA_PATH + "/boat" + "/plugin/touch/TouchInjector.jar");
                    hit = false;
                } else if (args.get(i).equals("org.quiltmc.loader.impl.launch.knot.KnotClient")) {
                    newArgs.add("com.tungsten.touchinjector.launch.QuiltKnotClient");
                } else if (args.get(i).equals("-cp")) {
                    hit = true;
                    newArgs.add(args.get(i));
                } else {
                    newArgs.add(args.get(i));
                }
            }
            return newArgs;
        } else {
            for (int i = 0; i < args.size(); i++) {
                if (args.get(i).startsWith("-Xms")) {
                    newArgs.add("-javaagent:" + CHTools.APP_DATA_PATH + "/boat" + "/plugin/touch/TouchInjector.jar=vanilla");
                }
                newArgs.add(args.get(i));
            }
            return newArgs;
        }
    }

}
