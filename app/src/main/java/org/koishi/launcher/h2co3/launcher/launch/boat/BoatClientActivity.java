package org.koishi.launcher.h2co3.launcher.launch.boat;

import static cosine.boat.utils.CHTools.LAUNCHER_FILE_DIR;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.client.Client;
import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.controller.HardwareController;
import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.controller.VirtualController;
import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.definitions.id.key.KeyEvent;
import com.cainiaohh.module.h2co3customkeyboard.utils.DisplayUtils;
import com.google.android.material.navigation.NavigationView;

import org.koishi.launcher.h2co3.R;
import org.koishi.launcher.h2co3.tools.launch.MCOptionUtils;
import org.koishi.launcher.h2co3.tools.launch.MinecraftVersion;

import java.io.File;
import java.util.Timer;
import java.util.Vector;

import cosine.boat.BoatActivity;
import cosine.boat.BoatInput;
import cosine.boat.function.BoatCallback;
import cosine.boat.utils.CHTools;

public class BoatClientActivity extends BoatActivity implements View.OnClickListener,Client {

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
    private DrawerLayout drawerLayout;
    private NavigationView navDrawer;
    private NavigationView.OnNavigationItemSelectedListener gameActionListener;
    private boolean grabbed = false;

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
        drawerLayout = findViewById(cosine.boat.R.id.overlay_drawer_options);
        navDrawer = findViewById(cosine.boat.R.id.main_navigation_view);
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
        gameActionListener = menuItem -> {
            if (menuItem.getItemId() == cosine.boat.R.id.menu_ctrl_custom) {
            }/* else if (menuItem.getItemId() == cosine.boat.R.id.menu_ctrl_vi) {
                //h2co3CustomManager.HideCustomButton(h2CO3CrossingKeyboard);
                openH2CO3CustomControls();
            }*/
            drawerLayout.closeDrawers();
            return true;
        };
        navDrawer.setNavigationItemSelectedListener(gameActionListener);

        //cv1=(CardView)base.findViewById(R.id.cv1);

        //Control the 2/3 screen

        getWindow().getDecorView().findViewById(android.R.id.content).post(() -> {
            width = getSurfaceLayerView().getWidth();
            height = getResources().getDisplayMetrics().heightPixels;
        });
        mouseCursor = new ImageView(this);
        mouseCursor.setLayoutParams(new ViewGroup.LayoutParams(DisplayUtils.getPxFromDp(this, CURSOR_SIZE), DisplayUtils.getPxFromDp(this, CURSOR_SIZE)));
        mouseCursor.setImageResource(cosine.boat.R.drawable.cursor5);
        this.addView(mouseCursor);
        //popupWindow.setContentView(base);


        drawerLayout.closeDrawers();
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

                    Vector<String> args = LauncherConfig.getMcArgs(gameLaunchSetting, BoatClientActivity.this, (int) (width * scaleFactor), (int) (height * scaleFactor), "server");
                    runOnUiThread(() -> {
                        BoatActivity.setBoatNativeWindow(new Surface(surface));
                        BoatInput.setEventPipe();

                        String runtimePath = CHTools.getBoatCfg("runtimePath", "");
                        String javaPath = null;
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
                        String lwjglPath = CHTools.getBoatCfg("runtimePath", "") + "/boat";
                        boatRenderer = "libGL114";
                        System.out.println(args);
                        MinecraftVersion mcVersion = MinecraftVersion.fromDirectory(new File(CHTools.getBoatCfg("currentVersion", "")));
                        ;
                        boolean isHighVersion = mcVersion.minimumLauncherVersion >= 21;
                        startGame(javaPath,
                                LAUNCHER_FILE_DIR,
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
    public void onBackPressed() {
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
                mouseCursor.setX(x);
                mouseCursor.setY(y);
            });
            grabbedPointer[0] = x;
            grabbedPointer[1] = y;
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
        boolean Grabbed = this.cursorMode == BoatInput.CursorDisabled;
        this.grabbed = Grabbed;
        if (!Grabbed) {
            setPointer(grabbedPointer[0], grabbedPointer[1]);
            mouseCursor.post(() -> mouseCursor.setVisibility(View.VISIBLE));
        } else if (mouseCursor.getVisibility() == View.VISIBLE) {
            mouseCursor.post(() -> mouseCursor.setVisibility(View.INVISIBLE));
        }
    }
    @SuppressLint("HandlerLeak")
    public final Handler cursorModeHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == BoatInput.CursorDisabled) {
                BoatClientActivity.this.setGrabCursor(true);
                virtualController.setGrabCursor(true);
                hardwareController.setGrabCursor(true);
                BoatClientActivity.this.mouseCursor.setVisibility(View.INVISIBLE);
                BoatClientActivity.this.cursorMode = BoatInput.CursorDisabled;
            }
            if (msg.what == BoatInput.CursorEnabled) {
                BoatClientActivity.this.setGrabCursor(false);
                virtualController.setGrabCursor(false);
                hardwareController.setGrabCursor(false);
                BoatClientActivity.this.mouseCursor.setVisibility(View.VISIBLE);
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
            public boolean dispatchKeyEvent(android.view.KeyEvent event) {
                return hardwareController.dispatchKeyEvent(event);
            }

            @Override
            public boolean dispatchGenericMotionEvent(MotionEvent event) {
                return hardwareController.dispatchMotionKeyEvent(event);
            }
        };
    }
}
