package org.koishi.launcher.h2co3.launcher.launch.boat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.koishi.launcher.h2co3.control.client.Client;
import org.koishi.launcher.h2co3.control.controller.HardwareController;
import org.koishi.launcher.h2co3.control.controller.VirtualController;
import org.koishi.launcher.h2co3.control.definitions.id.key.KeyEvent;
import org.koishi.launcher.h2co3.tools.DisplayUtils;
import org.koishi.launcher.h2co3.tools.launch.MCOptionUtils;
import org.koishi.launcher.h2co3.tools.launch.MinecraftVersion;

import java.io.File;
import java.util.Timer;
import java.util.Vector;

import cosine.boat.BoatActivity;
import cosine.boat.BoatInput;
import cosine.boat.function.BoatCallback;
import cosine.boat.keycodes.BoatKeycodes;
import cosine.boat.utils.CHTools;

public class BoatClientActivity extends BoatActivity implements View.OnClickListener, Client {

    private final static int CURSOR_SIZE = 16; //dp
    private final int[] grabbedPointer = new int[]{0, 0};

    public FrameLayout mControlLayout;
    public SharedPreferences msh;
    public SharedPreferences.Editor mshe;
    public int cursorMode = BoatInput.CursorEnabled;
    public ImageView mouseCursor;
    int height;
    int width;
    private LauncherConfig gameLaunchSetting;
    private VirtualController virtualController;
    private HardwareController hardwareController;
    private boolean grabbed = false;
    @SuppressLint("HandlerLeak")
    public final Handler cursorModeHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == BoatInput.CursorDisabled) {
                BoatClientActivity.this.setGrabCursor(true);
                virtualController.setGrabCursor(true);
                hardwareController.setGrabCursor(true);
                BoatClientActivity.this.cursorMode = BoatInput.CursorDisabled;
            }
            if (msg.what == BoatInput.CursorEnabled) {
                BoatClientActivity.this.setGrabCursor(false);
                virtualController.setGrabCursor(false);
                hardwareController.setGrabCursor(false);
                BoatClientActivity.this.cursorMode = BoatInput.CursorEnabled;
            }
        }
    };

    public static void attachControllerInterface() {
        BoatClientActivity.boatInterface = new BoatClientActivity.IBoat() {
            private VirtualController virtualController;
            private HardwareController hardwareController;
            private Timer timer;

            public void onActivityCreate(BoatActivity boatActivity) {
                virtualController = new VirtualController((Client) boatActivity, KEYMAP_TO_X);
                hardwareController = new HardwareController((Client) boatActivity, KEYMAP_TO_X);
            }

            @Override
            public void setGrabCursor(boolean isGrabbed) {
                virtualController.setGrabCursor(isGrabbed);
                hardwareController.setGrabCursor(isGrabbed);
            }

            @Override
            public void onStop() {
                virtualController.onStop();
                hardwareController.onStop();
            }

            @Override
            public void onResume() {
                virtualController.onResumed();
                hardwareController.onResumed();
            }

            @Override
            public void onPause() {
                virtualController.onPaused();
                hardwareController.onPaused();
            }

            @Override
            public boolean dispatchGenericMotionEvent(MotionEvent event) {
                return hardwareController.dispatchMotionKeyEvent(event);
            }
        };
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameLaunchSetting = new LauncherConfig();

        setContentView(cosine.boat.R.layout.overlay);
        handleCallback();

        msh = getSharedPreferences("Boat_H2CO3", MODE_PRIVATE);
        mshe = msh.edit();
        base = findViewById(cosine.boat.R.id.main_base);
        mControlLayout = findViewById(cosine.boat.R.id.content_frame);
        virtualController = new VirtualController(this, KeyEvent.KEYMAP_TO_X) {
            @Override
            public void init() {
                super.init();
            }
        };
        hardwareController = new HardwareController(this, KeyEvent.KEYMAP_TO_X);
        init();
        initUI();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initUI() {

        getWindow().getDecorView().findViewById(android.R.id.content).post(() -> {
            width = getSurfaceLayerView().getWidth();
            height = getResources().getDisplayMetrics().heightPixels;
        });
        mouseCursor = new ImageView(this);
        mouseCursor.setLayoutParams(new ViewGroup.LayoutParams(DisplayUtils.getPxFromDp(this, CURSOR_SIZE), DisplayUtils.getPxFromDp(this, CURSOR_SIZE)));
        mouseCursor.setImageResource(cosine.boat.R.drawable.cursor5);
        mouseCursor.post(() -> {
            mouseCursor.setX(width / 2);
            grabbedPointer[0] = width / 2;
            mouseCursor.setY(height / 2);
            grabbedPointer[1] = height / 2;
        });
        mControlLayout.addView(mouseCursor);

        //popupWindow.setContentView(base);

        BoatInput.pushEventWindow(width, height);
    }

    private void handleCallback() {
        setBoatCallback(new BoatCallback() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                surface.setDefaultBufferSize((int) (width * scaleFactor), (int) (height * scaleFactor));
                MCOptionUtils.load(CHTools.getBoatCfg("game_directory", ""));
                MCOptionUtils.set("overrideWidth", String.valueOf((int) (width * scaleFactor)));
                MCOptionUtils.set("overrideHeight", String.valueOf((int) (height * scaleFactor)));
                MCOptionUtils.set("fullscreen", "false");
                MCOptionUtils.save(CHTools.getBoatCfg("game_directory", ""));
                new Thread(() -> {

                    Vector<String> args = LauncherConfig.getMcArgs(gameLaunchSetting, BoatClientActivity.this, (int) (width * scaleFactor), (int) (height * scaleFactor));
                    runOnUiThread(() -> {
                        BoatActivity.setBoatNativeWindow(new Surface(surface));
                        BoatInput.setEventPipe();

                        String runtimePath = CHTools.RUNTIME_DIR;
                        String javaPath;
                        String java = LauncherConfig.loadjava();
                        // Java版本选择
                        if (java.equals("jre_8")) {
                            javaPath = runtimePath + "/jre_8";
                        } else {
                            javaPath = runtimePath + "/jre_17";
                        }
                        String gl = LauncherConfig.loadgl();
                        System.out.print(gl);
                        String boatRenderer;
                        String lwjglPath = CHTools.RUNTIME_DIR + "/boat";
                        if (LauncherConfig.loadgl().equals("VirGL")) {
                            boatRenderer = "VirGL";
                        } else {
                            boatRenderer = "libGL114";
                        }

                        System.out.println(args);
                        MinecraftVersion mcVersion = MinecraftVersion.fromDirectory(new File(CHTools.getBoatCfg("currentVersion", "")));
                        boolean isHighVersion = mcVersion.minimumLauncherVersion >= 21;
                        startGame(javaPath,
                                CHTools.PUBLIC_FILE_PATH,
                                isHighVersion,
                                args,
                                boatRenderer,
                                CHTools.getBoatCfg("game_directory", "Null"));
                    });
                }).start();
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            }

            @Override
            public void onCursorModeChange(int mode) {
                cursorModeHandler.sendEmptyMessage(mode);
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onPicOutput() {
            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onExit(int code) {
                Intent virGLService = new Intent(BoatClientActivity.this, VirGLService.class);
                stopService(virGLService);
            }
        });
    }

    @Override
    public void onClick(View p1) {
        // TODO: Implement this method
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setKey(int keyCode, boolean pressed) {
        this.setKey(keyCode, 0, pressed);
    }

    @Override
    public void setPointerInc(int xInc, int yInc) {
        if (!grabbed) {
            int x, y;
            x = grabbedPointer[0] + xInc;
            y = grabbedPointer[1] + yInc;
            if (x >= 0 && x <= width)
                grabbedPointer[0] += xInc;
            if (y >= 0 && y <= height)
                grabbedPointer[1] += yInc;
            setPointer(grabbedPointer[0], grabbedPointer[1]);
            this.mouseCursor.post(() -> {
                mouseCursor.setX(grabbedPointer[0]);
                mouseCursor.setY(grabbedPointer[1]);
            });
        } else {
            setPointer(getPointer()[0] + xInc, getPointer()[1] + yInc);
        }
    }

    @Override
    public void setPointer(int x, int y) {
        super.setPointer(x, y);
        if (!grabbed) {
            this.mouseCursor.post(() -> {
                if (x >= 0 && x <= width) {
                    mouseCursor.setX(x);
                    grabbedPointer[0] = x;
                } else {
                    mouseCursor.setX(width / 2);
                    grabbedPointer[0] = width / 2;
                }
                if (y >= 0 && y <= height) {
                    mouseCursor.setY(y);
                    grabbedPointer[1] = y;
                } else {
                    mouseCursor.setY(height / 2);
                    grabbedPointer[1] = height / 2;
                }
            });
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void addView(View v) {
        this.addContentView(v, v.getLayoutParams());
    }

    @Override
    public void typeWords(String str) {
        if (str == null) return;
        for (int i = 0; i < str.length(); i++) {
            setKey(0, str.charAt(i), true);
            setKey(0, str.charAt(i), false);
        }
    }

    @Override
    public int[] getGrabbedPointer() {
        return this.grabbedPointer;
    }

    @Override
    public int[] getLoosenPointer() {
        return this.getPointer();
    }

    @Override
    public ViewGroup getViewsParent() {
        return (ViewGroup) mControlLayout.getRootView();
    }

    @Override
    public View getSurfaceLayerView() {
        return findViewById(cosine.boat.R.id.main_surface);
    }

    @Override
    public boolean isGrabbed() {
        return this.grabbed;
    }

    @Override
    public void setGrabCursor(boolean isGrabbed) {
        this.grabbed = isGrabbed;
        if (!isGrabbed) {
            setPointer(grabbedPointer[0], grabbedPointer[1]);
            mouseCursor.post(() -> mouseCursor.setVisibility(View.VISIBLE));
        } else if (mouseCursor.getVisibility() == View.VISIBLE) {
            mouseCursor.post(() -> mouseCursor.setVisibility(View.INVISIBLE));
        }
    }

    @Override
    public void onBackPressed() {
        boolean mouse = false;
        final int[] devices = InputDevice.getDeviceIds();
        for (int j : devices) {
            InputDevice device = InputDevice.getDevice(j);
            if (device != null && !device.isVirtual()) {
                if (device.getName().contains("Mouse")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && device.isExternal()) {
                        mouse = true;
                        break;
                    } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                        mouse = true;
                        break;
                    }
                }
            }
        }
        if (!mouse) {
            BoatInput.setKey(BoatKeycodes.KEY_ESC, 0, true);
            BoatInput.setKey(BoatKeycodes.KEY_ESC, 0, false);
        }
    }
}
