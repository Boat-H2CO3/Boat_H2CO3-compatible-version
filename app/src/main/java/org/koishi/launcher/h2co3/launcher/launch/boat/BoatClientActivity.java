package org.koishi.launcher.h2co3.launcher.launch.boat;

import static org.koishi.launcher.h2co3.tools.CHTools.getBoatCfg;
import static cosine.boat.utils.CHTools.LAUNCHER_FILE_DIR;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.koishi.launcher.h2co3.launcher.control.gamecontroller.codes.BoatKeycodes;
import org.koishi.launcher.h2co3.launcher.control.view.H2CO3CrossingKeyboard;
import org.koishi.launcher.h2co3.launcher.control.view.H2CO3MinecraftBottomBar;
import org.koishi.launcher.h2co3.launcher.control.H2CO3CustomButton;
import org.koishi.launcher.h2co3.launcher.control.H2CO3CustomManager;
import org.koishi.launcher.h2co3.launcher.ui.MainActivity;
import org.koishi.launcher.h2co3.tools.launch.MCOptionUtils;
import org.koishi.launcher.h2co3.tools.launch.MinecraftVersion;
import org.lwjgl.glfw.CallbackBridge;

import java.io.File;
import java.util.Vector;

import cosine.boat.BoatActivity;
import cosine.boat.BoatInput;
import cosine.boat.function.BoatCallback;
import cosine.boat.utils.CHTools;

public class BoatClientActivity extends BoatActivity implements View.OnClickListener, View.OnTouchListener, TextWatcher, TextView.OnEditorActionListener{

    private LauncherConfig gameLaunchSetting;

    public FrameLayout mControlLayout;
    public ImageView mouseCursor;
    public H2CO3CustomManager h2co3CustomManager;
    public H2CO3CrossingKeyboard h2CO3CrossingKeyboard;
    public H2CO3MinecraftBottomBar h2CO3MinecraftBottombar;
    public SharedPreferences msh;
    public SharedPreferences.Editor mshe;
    public int cursorMode = BoatInput.CursorEnabled;
    MyHandler mHandler;
    Button touchPad2, touchPad;
    private DrawerLayout drawerLayout;
    private NavigationView navDrawer;
    private NavigationView.OnNavigationItemSelectedListener gameActionListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameLaunchSetting = new LauncherConfig();

        setContentView(cosine.boat.R.layout.overlay);
        handleCallback();

        msh = getSharedPreferences("Boat_H2CO3", MODE_PRIVATE);
        mshe = msh.edit();
        base = findViewById(cosine.boat.R.id.main_base);
        mouseCursor = base.findViewById(cosine.boat.R.id.mouse_cursor);
        mControlLayout = findViewById(cosine.boat.R.id.content_frame);
        touchPad = this.findButton(cosine.boat.R.id.touch_pad);
        drawerLayout = findViewById(cosine.boat.R.id.overlay_drawer_options);

        navDrawer = findViewById(cosine.boat.R.id.main_navigation_view);
        init();
        initUI();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initUI() {

        InitCustomButton();

        gameActionListener = menuItem -> {
            if (menuItem.getItemId() == cosine.boat.R.id.menu_ctrl_custom) {
                openH2CO3CustomControls();
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

        touchPad2 = this.findButton(cosine.boat.R.id.touch_pad2);
        touchPad2.setOnTouchListener(new View.OnTouchListener() {
            private long currentMS;
            private float itialY, itialX;
            private int moveX, moveY;

            @SuppressLint("SetTextI18n")
            @Override
            public boolean onTouch(View p1, MotionEvent p2) {
                if (cursorMode == BoatInput.CursorDisabled) {
                    if (getResources().getString(cosine.boat.R.string.touch_mode_2).equals(getResources().getString(cosine.boat.R.string.touch_mode_2))) {
                        switch (p2.getActionMasked()) {
                            case MotionEvent.ACTION_DOWN:
                                moveX = 0;
                                moveY = 0;
                                initialX = (int) p2.getX();
                                initialY = (int) p2.getY();
                                itialX = p2.getX();
                                itialY = p2.getY();
                                currentMS = System.currentTimeMillis();
                            case MotionEvent.ACTION_MOVE:
                                moveX += Math.abs(p2.getX() - itialX);
                                moveY += Math.abs(p2.getY() - itialY);
                                long movesTime = System.currentTimeMillis() - currentMS;//移动时间
                                if (movesTime > 400 && moveX < 3 && moveY < 3) {
                                    BoatInput.pushEventMouseButton(BoatInput.Button1, true);
                                    BoatInput.pushEventPointer(baseX + (int) p2.getX() - initialX, baseY + (int) p2.getY() - initialY);
                                    return false;
                                }
                                BoatInput.pushEventPointer(baseX + (int) p2.getX() - initialX, baseY + (int) p2.getY() - initialY);
                                break;
                            case MotionEvent.ACTION_UP:
                                baseX += ((int) p2.getX() - initialX);
                                baseY += ((int) p2.getY() - initialY);
                                BoatInput.pushEventMouseButton(BoatInput.Button1, false);
                                BoatInput.pushEventMouseButton(BoatInput.Button3, false);
                                BoatInput.pushEventPointer(baseX, baseY);
                                long moveTime = System.currentTimeMillis() - currentMS;
                                if (moveTime < 200 && (moveX < 2 || moveY < 2)) {
                                    BoatInput.pushEventMouseButton(BoatInput.Button3, true);
                                    BoatInput.pushEventMouseButton(BoatInput.Button3, false);
                                    return false;
                                }
                                break;
                            default:
                                break;
                        }
                    } else {
                        switch (p2.getActionMasked()) {
                            case MotionEvent.ACTION_DOWN:
                                moveX = 0;
                                moveY = 0;
                                initialX = (int) p2.getX();
                                initialY = (int) p2.getY();
                                itialX = p2.getX();
                                itialY = p2.getY();
                                currentMS = System.currentTimeMillis();
                            case MotionEvent.ACTION_MOVE:
                                moveX += Math.abs(p2.getX() - itialX);
                                moveY += Math.abs(p2.getY() - itialY);
                                long movesTime = System.currentTimeMillis() - currentMS;//移动时间
                                if (movesTime > 400 && moveX < 3 && moveY < 3) {
                                    BoatInput.pushEventMouseButton(BoatInput.Button3, true);
                                    BoatInput.pushEventPointer(baseX + (int) p2.getX() - initialX, baseY + (int) p2.getY() - initialY);
                                    return false;
                                }
                                BoatInput.pushEventPointer(baseX + (int) p2.getX() - initialX, baseY + (int) p2.getY() - initialY);
                                break;
                            case MotionEvent.ACTION_UP:
                                baseX += ((int) p2.getX() - initialX);
                                baseY += ((int) p2.getY() - initialY);
                                BoatInput.pushEventMouseButton(BoatInput.Button3, false);
                                BoatInput.pushEventMouseButton(BoatInput.Button1, false);
                                BoatInput.pushEventPointer(baseX, baseY);
                                long moveTime = System.currentTimeMillis() - currentMS;
                                if (moveTime < 200 && (moveX < 2 || moveY < 2)) {
                                    BoatInput.pushEventMouseButton(BoatInput.Button1, true);
                                    BoatInput.pushEventMouseButton(BoatInput.Button1, false);
                                    return false;
                                }

                                break;
                            default:
                                break;
                        }
                    }
                } else if (cursorMode == BoatInput.CursorEnabled) {
                    baseX = (int) p2.getX();
                    baseY = (int) p2.getY();
                    BoatInput.pushEventPointer(baseX, baseY);
                    if (p2.getActionMasked() == MotionEvent.ACTION_DOWN) {
                        BoatInput.pushEventMouseButton(BoatInput.Button1, true);

                    }
                    if (p2.getActionMasked() == MotionEvent.ACTION_UP) {
                        BoatInput.pushEventMouseButton(BoatInput.Button1, false);
                    }
                }


                mouseCursor.setX(p2.getX());
                mouseCursor.setY(p2.getY());
                return true;
            }
        });

        int height = getWindowManager().getDefaultDisplay().getHeight();
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int scale = 1;
        while (width / (scale + 1) >= 320 && height / (scale + 1) >= 240) {
            scale++;
        }
        //popupWindow.setContentView(base);

        mHandler = new MyHandler();

        drawerLayout.closeDrawers();
    }

    private void handleCallback() {
        setBoatCallback(new BoatCallback() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                surface.setDefaultBufferSize((int) (width * scaleFactor), (int) (height * scaleFactor));
                MCOptionUtils.load(CHTools.getBoatCfg("game_directory",""));
                MCOptionUtils.set("overrideWidth", String.valueOf((int) (width * scaleFactor)));
                MCOptionUtils.set("overrideHeight", String.valueOf((int) (height * scaleFactor)));
                MCOptionUtils.set("fullscreen", "false");
                MCOptionUtils.save(CHTools.getBoatCfg("game_directory",""));
                new Thread(() -> {

                    Vector<String> args = LauncherConfig.getMcArgs(gameLaunchSetting, BoatClientActivity.this, (int) (width * scaleFactor), (int) (height * scaleFactor), "server");
                    runOnUiThread(() -> {
                        BoatActivity.setBoatNativeWindow(new Surface(surface));
                        BoatInput.setEventPipe();

                        String runtimePath = CHTools.getBoatCfg("runtimePath","");
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
                        String lwjglPath = CHTools.getBoatCfg("runtimePath","") + "/boat";
                        boatRenderer = "libGL114";
                        System.out.println(args);
                        MinecraftVersion mcVersion = MinecraftVersion.fromDirectory(new File(CHTools.getBoatCfg("currentVersion","")));;
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

    private void InitCustomButton() {
        h2co3CustomManager = new H2CO3CustomManager();
        h2co3CustomManager.InitCustomButton(this, (this.mControlLayout), getBoatCfg("currentVersion", org.koishi.launcher.h2co3.tools.CHTools.LAUNCHER_FILE_DIR) + "/H2CO3KeyBoard.json");
        h2co3CustomManager.setCustomButtonCallback(new H2CO3CustomManager.CustomButtonCallback() {
            @Override
            public void CommandReceived(String command) {

            }

            @Override
            public void KeyReceived(int Key, boolean Pressed) {
                sendKeyPress(Key, 0, Pressed);
            }

            @Override
            public void ControlsMousePointerMovement(int x, int y) {
                if (cursorMode == BoatInput.CursorDisabled) {
                    baseX = x;
                    baseY = y;
                    BoatInput.pushEventPointer(baseX, baseY);
                }
            }

            @Override
            public void MouseCallback(int Key, boolean Pressed) {
                sendMouseButton(Key, Pressed);
            }

            @Override
            public void Pressed() {

            }

            @Override
            public void unPressed() {

            }
        });

        h2CO3CrossingKeyboard = findViewById(cosine.boat.R.id.h2co3_keyboard);
        float xxx = msh.getFloat("CrossingKeyBoardX", (float) 0.09 * (CallbackBridge.windowWidth));
        float yyy = msh.getFloat("CrossingKeyBoardY", (float) 0.49 * (CallbackBridge.windowHeight));
        if (xxx != 0 && yyy != 0) {
            h2CO3CrossingKeyboard.setX(xxx);
            h2CO3CrossingKeyboard.setY(yyy);
        }
        h2CO3CrossingKeyboard.setScale(msh.getFloat("CrossingKeyBoardResize", 1.0f));
        h2CO3CrossingKeyboard.setListener(new H2CO3CrossingKeyboard.H2CO3Listener() {
            private boolean upFromCenter = false;
            private boolean upToCenter = false;
            private boolean isShift = false;

            @Override
            public void onLeftUp() {
                BoatInput.pushEventKey(BoatKeycodes.KEY_W, 0, true);
                BoatInput.pushEventKey(BoatKeycodes.KEY_A, 0, true);
                BoatInput.pushEventKey(BoatKeycodes.KEY_S, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_D, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_SPACE, 0, false);
            }

            @Override
            public void onUp() {
                BoatInput.pushEventKey(BoatKeycodes.KEY_W, 0, true);
                BoatInput.pushEventKey(BoatKeycodes.KEY_A, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_S, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_D, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_SPACE, 0, false);
            }

            @Override
            public void onRightUp() {
                BoatInput.pushEventKey(BoatKeycodes.KEY_W, 0, true);
                BoatInput.pushEventKey(BoatKeycodes.KEY_D, 0, true);
                BoatInput.pushEventKey(BoatKeycodes.KEY_A, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_S, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_SPACE, 0, false);
            }

            @Override
            public void onLeft() {
                BoatInput.pushEventKey(BoatKeycodes.KEY_A, 0, true);
                BoatInput.pushEventKey(BoatKeycodes.KEY_W, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_S, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_D, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_SPACE, 0, false);
            }

            @Override
            public void onCenter(boolean direct) {
                if (direct) {
                    isShift = !isShift;
                    BoatInput.pushEventKey(BoatKeycodes.KEY_LEFTSHIFT, 0, true);
                    BoatInput.pushEventKey(BoatKeycodes.KEY_W, 0, false);
                    BoatInput.pushEventKey(BoatKeycodes.KEY_A, 0, false);
                    BoatInput.pushEventKey(BoatKeycodes.KEY_S, 0, false);
                    BoatInput.pushEventKey(BoatKeycodes.KEY_D, 0, false);
                    BoatInput.pushEventKey(BoatKeycodes.KEY_SPACE, 0, false);
                } else {
                    upFromCenter = true;
                }
            }

            @Override
            public void onRight() {
                BoatInput.pushEventKey(BoatKeycodes.KEY_D, 0, true);
                BoatInput.pushEventKey(BoatKeycodes.KEY_W, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_A, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_S, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_SPACE, 0, false);
            }

            @Override
            public void onLeftDown() {
                BoatInput.pushEventKey(BoatKeycodes.KEY_A, 0, true);
                BoatInput.pushEventKey(BoatKeycodes.KEY_S, 0, true);
                BoatInput.pushEventKey(BoatKeycodes.KEY_W, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_D, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_SPACE, 0, false);
            }

            @Override
            public void onDown() {
                BoatInput.pushEventKey(BoatKeycodes.KEY_S, 0, true);
                BoatInput.pushEventKey(BoatKeycodes.KEY_W, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_A, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_D, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_SPACE, 0, false);
            }

            @Override
            public void onRightDown() {
                BoatInput.pushEventKey(BoatKeycodes.KEY_S, 0, true);
                BoatInput.pushEventKey(BoatKeycodes.KEY_D, 0, true);
                BoatInput.pushEventKey(BoatKeycodes.KEY_W, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_A, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_SPACE, 0, false);
            }

            @Override
            public void onSlipLeftUp() {
                System.out.println("左上");
            }

            @Override
            public void onSlipUp() {
                System.out.println("上");
            }

            @Override
            public void onSlipRightUp() {
                System.out.println("右上");
                BoatInput.pushEventKey(BoatKeycodes.KEY_E, 0, true);
                BoatInput.pushEventKey(BoatKeycodes.KEY_E, 0, false);
            }

            @Override
            public void onSlipLeft() {
                System.out.println("左");
            }

            @Override
            public void onSlipRight() {
                System.out.println("右");
            }

            @Override
            public void onSlipLeftDown() {
                System.out.println("左下");
                showKeyboard();
            }

            @Override
            public void onSlipDown() {
                System.out.println("下");
            }

            @Override
            public void onSlipRightDown() {
                System.out.println("右下");
            }

            @Override
            public void onUpToCenter() {
//            if (!禁用手势){
//
//            }
                upToCenter = true;
                BoatInput.pushEventKey(BoatKeycodes.KEY_SPACE, 0, true);
            }

            @Override
            public void onFingerUp() {
                BoatInput.pushEventKey(BoatKeycodes.KEY_W, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_A, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_S, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_D, 0, false);
                BoatInput.pushEventKey(BoatKeycodes.KEY_LEFTSHIFT, 0, false);
                if (upToCenter) {
                    BoatInput.pushEventKey(BoatKeycodes.KEY_SPACE, 0, false);
                    upToCenter = false;
                }

            }
        });
    }

    public void showKeyboard() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        mainTextureView.requestFocusFromTouch();
        mainTextureView.requestFocus();
    }

    private void openH2CO3CustomControls() {
        h2co3CustomManager.isEditCustomButton();
        h2CO3CrossingKeyboard.Custom();
        navDrawer.getMenu().clear();
        navDrawer.inflateMenu(cosine.boat.R.menu.menu_h2co3);
        navDrawer.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == cosine.boat.R.id.menu_h2co3_add) {
                if (h2co3CustomManager.getSelectCustomButtonMode()) {
                    h2co3CustomManager.setSelectCustomButtonMode(false);
                    if (h2co3CustomManager.getEditCustomButtonMode()) {
                        h2co3CustomManager.CustomButtonDialog(false);
                        if (h2co3CustomManager.getCurrentKeyPress().getButtonKeyType().equals("显隐控制按键")) {
                            for (String ID : h2co3CustomManager.getCurrentKeyPress().getTargetKeyID()) {
                                H2CO3CustomButton b = h2co3CustomManager.getButton(ID);
                                b.setTextColor(Color.parseColor(b.getButtonTextColor()));
                                b.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        h2co3CustomManager.CustomButtonDialog(true);
                    }
                } else {
                    h2co3CustomManager.CustomButtonDialog(true);
                }
            } else if (item.getItemId() == cosine.boat.R.id.menu_h2co3_save) {
                h2co3CustomManager.isEditCustomButton();
                h2CO3CrossingKeyboard.Custom();
                mshe.putFloat("CrossingKeyBoardX", h2CO3CrossingKeyboard.getX());
                mshe.putFloat("CrossingKeyBoardY", h2CO3CrossingKeyboard.getY());
                mshe.putFloat("CrossingKeyBoardResize", h2CO3CrossingKeyboard.mScale);
                mshe.commit();
                navDrawer.getMenu().clear();
                navDrawer.inflateMenu(cosine.boat.R.menu.menu_boat_overlay);
                navDrawer.setNavigationItemSelectedListener(gameActionListener);
            }
            return true;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public Button findButton(int id) {
        Button b = (Button) findViewById(id);
        b.setOnTouchListener(this);
        return b;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View p1, MotionEvent p2) {
        if (p1 == touchPad) {
            if (cursorMode == BoatInput.CursorDisabled) {
                switch (p2.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = (int) p2.getX();
                        initialY = (int) p2.getY();
                    case MotionEvent.ACTION_MOVE:
                        BoatInput.pushEventPointer(baseX + (int) p2.getX() - initialX, baseY + (int) p2.getY() - initialY);
                        break;
                    case MotionEvent.ACTION_UP:
                        baseX += ((int) p2.getX() - initialX);
                        baseY += ((int) p2.getY() - initialY);
                        BoatInput.pushEventPointer(baseX, baseY);
                        break;
                    default:
                        break;
                }
            } else if (cursorMode == BoatInput.CursorEnabled) {
                baseX = (int) p2.getX();
                baseY = (int) p2.getY();
                BoatInput.pushEventPointer(baseX, baseY);
                if (p2.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    BoatInput.pushEventMouseButton(BoatInput.Button1, true);
                }
                if (p2.getActionMasked() == MotionEvent.ACTION_UP) {
                    BoatInput.pushEventMouseButton(BoatInput.Button1, false);
                }
            }
            mouseCursor.setX(p2.getX());
            mouseCursor.setY(p2.getY());
            return true;
        }
        return false;

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }


    @SuppressLint("HandlerLeak")
    private final Handler cursorModeHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == BoatInput.CursorDisabled) {
                BoatClientActivity.this.h2CO3CrossingKeyboard.setVisibility(View.VISIBLE);
                BoatClientActivity.this.mouseCursor.setVisibility(View.INVISIBLE);
                BoatClientActivity.this.cursorMode = BoatInput.CursorDisabled;
                BoatClientActivity.this.touchPad.setVisibility(View.INVISIBLE);
                BoatClientActivity.this.touchPad2.setVisibility(View.VISIBLE);
            }
            if (msg.what == BoatInput.CursorEnabled) {
                BoatClientActivity.this.h2CO3CrossingKeyboard.setVisibility(View.INVISIBLE);
                BoatClientActivity.this.mouseCursor.setVisibility(View.VISIBLE);
                BoatClientActivity.this.cursorMode = BoatInput.CursorEnabled;
                BoatClientActivity.this.touchPad.setVisibility(View.VISIBLE);
                BoatClientActivity.this.touchPad2.setVisibility(View.INVISIBLE);
            }
        }
    };
    @SuppressLint("HandlerLeak")
    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                default:
                    BoatClientActivity.this.finish();
                    startActivity(new Intent(BoatClientActivity.this, MainActivity.class));
                    break;
            }
        }
    }

    @Override
    public void onClick(View p1) {
        // TODO: Implement this method
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onBackPressed() {
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
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
        Intent virGLService = new Intent(this, VirGLService.class);
        stopService(virGLService);
        super.onDestroy();
    }

}

