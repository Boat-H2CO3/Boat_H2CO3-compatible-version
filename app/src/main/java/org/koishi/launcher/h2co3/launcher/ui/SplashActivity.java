package org.koishi.launcher.h2co3.launcher.ui;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.circularreveal.CircularRevealRelativeLayout;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import org.koishi.launcher.h2co3.R;
import org.koishi.launcher.h2co3.component.H2CO3Activity;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.koishi.launcher.h2co3core.utils.cainiaohh.CHTools;
import org.koishi.launcher.h2co3core.utils.FileUtils;
import org.koishi.launcher.h2co3core.utils.LocaleUtils;
import org.koishi.launcher.h2co3core.utils.RuntimeUtils;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends H2CO3Activity {

    public ConstraintLayout background;
    boolean boat = false;
    boolean java8 = false;
    boolean java17 = false;
    boolean keyboard = false;

    private ProgressBar boatProgress;

    private ProgressBar java8Progress;
    private ProgressBar java17Progress;
    private ProgressBar keyboardProgress;
    private ImageView boatState;
    private ImageView java8State;

    private ImageView java17State;
    private ImageView keyboardState;
    CircularRevealRelativeLayout circularRevealLayout;
    private boolean installing = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splah);
        circularRevealLayout = findViewById(R.id.circularRevealLayout);
        circularRevealLayout.post(() -> {
            int centerX = circularRevealLayout.getWidth() / 2;
            int centerY = circularRevealLayout.getHeight() / 2;
            float startRadius = 0;
            float endRadius = Math.max(circularRevealLayout.getWidth(), circularRevealLayout.getHeight());
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(circularRevealLayout, centerX, centerY, startRadius, endRadius);
            circularReveal.start();
        });

        boatProgress = findViewById(R.id.boat_progress);
        java8Progress = findViewById(R.id.java8_progress);
        java17Progress = findViewById(R.id.java17_progress);
        keyboardProgress = findViewById(R.id.keyboard_progress);

        boatState = findViewById(R.id.boat_state);
        java8State = findViewById(R.id.java8_state);
        java17State = findViewById(R.id.java17_state);
        keyboardState = findViewById(R.id.keyboard_state);


        initState();

        refreshDrawables();

        check();

        checkPermission();
    }

    private void checkPermission() {
        XXPermissions.with(this)
                // 申请单个权限
                .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                // 设置权限请求拦截器（局部设置）
                //.interceptor(new PermissionInterceptor())
                // 设置不触发错误检测机制（局部设置）
                //.unchecked()
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                        init();
                    }

                    @Override
                    public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                        if (doNotAskAgain) {
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(SplashActivity.this, permissions);
                        } else {
                            finish();
                        }
                    }
                });
    }

    private void init() {
        CHTools.loadPaths(this);
        install();
    }


    public void enterLauncher() {
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void initState() {
        try {
            boat = RuntimeUtils.isLatest(CHTools.BOAT_LIBRARY_DIR, "/assets/app_runtime/boat");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            java8 = RuntimeUtils.isLatest(CHTools.JAVA_8_PATH, "/assets/app_runtime/jre_8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            java17 = RuntimeUtils.isLatest(CHTools.JAVA_17_PATH, "/assets/app_runtime/jre_17");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            keyboard = RuntimeUtils.isLatest(CHTools.CONTROLLER_DIR, "/assets/keyboards");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshDrawables() {
        @SuppressLint("UseCompatLoadingForDrawables") Drawable stateUpdate = this.getDrawable(R.drawable.ic_baseline_update_24);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable stateDone = this.getDrawable(R.drawable.ic_baseline_done_24);

        stateUpdate.setTint(Color.GRAY);
        stateDone.setTint(Color.GRAY);

        boatState.setBackgroundDrawable(boat ? stateDone : stateUpdate);
        java8State.setBackgroundDrawable(java8 ? stateDone : stateUpdate);
        java17State.setBackgroundDrawable(java17 ? stateDone : stateUpdate);
        keyboardState.setBackgroundDrawable(keyboard ? stateDone : stateUpdate);
    }

    private boolean isLatest() {
        return boat && java8 && java17 && keyboard ;
    }

    private void check() {
        if (isLatest()) {
            this.enterLauncher();
        }
    }

    private void install() {
        if (installing)
            return;
        installing = true;
        if (!boat) {
            boatState.setVisibility(View.GONE);
            boatProgress.setVisibility(View.VISIBLE);
            new Thread(() -> {
                try {
                    RuntimeUtils.install(this, CHTools.BOAT_LIBRARY_DIR, "app_runtime/boat");
                    boat = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.runOnUiThread(() -> {
                    boatState.setVisibility(View.VISIBLE);
                    boatProgress.setVisibility(View.GONE);
                    refreshDrawables();
                    check();
                });
            }).start();
        }
        if (!java8) {
            java8State.setVisibility(View.GONE);
            java8Progress.setVisibility(View.VISIBLE);
            new Thread(() -> {
                try {
                    RuntimeUtils.installJava(this, CHTools.JAVA_8_PATH, "app_runtime/jre_8");
                    java8 = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.runOnUiThread(() -> {
                    java8State.setVisibility(View.VISIBLE);
                    java8Progress.setVisibility(View.GONE);
                    refreshDrawables();
                    check();
                });
            }).start();
        }
        if (!java17) {
            java17State.setVisibility(View.GONE);
            java17Progress.setVisibility(View.VISIBLE);
            new Thread(() -> {
                try {
                    RuntimeUtils.installJava(this, CHTools.JAVA_17_PATH, "app_runtime/jre_17");
                    if (!LocaleUtils.getSystemLocale().getDisplayName().equals(Locale.CHINA.getDisplayName())) {
                        FileUtils.writeText(new File(CHTools.JAVA_17_PATH + "/resolv.conf"), "nameserver 1.1.1.1\n" + "nameserver 1.0.0.1");
                    } else {
                        FileUtils.writeText(new File(CHTools.JAVA_17_PATH + "/resolv.conf"), "nameserver 8.8.8.8\n" + "nameserver 8.8.4.4");
                    }
                    java17 = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.runOnUiThread(() -> {
                    java17State.setVisibility(View.VISIBLE);
                    java17Progress.setVisibility(View.GONE);
                    refreshDrawables();
                    check();
                });
            }).start();
        }
        if (!keyboard) {
            keyboardState.setVisibility(View.GONE);
            keyboardProgress.setVisibility(View.VISIBLE);
            new Thread(() -> {
                try {
                    RuntimeUtils.install(this, CHTools.CONTROLLER_DIR, "keyboards");
                    keyboard = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.runOnUiThread(() -> {
                    keyboardState.setVisibility(View.VISIBLE);
                    keyboardProgress.setVisibility(View.GONE);
                    refreshDrawables();
                    check();
                });
            }).start();
        }
    }
}
