package org.koishi.launcher.h2co3.launcher.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import org.koishi.launcher.h2co3.R;
import org.koishi.launcher.h2co3.component.H2CO3Activity;
import org.koishi.launcher.h2co3.launcher.ui.splash.SplashFragment;

import java.util.List;

import cosine.boat.utils.CHTools;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends H2CO3Activity {

    public ConstraintLayout background;
    private SplashFragment runtimeFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splah);

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
        initFragments();
        start();
    }

    private void initFragments() {
        runtimeFragment = new SplashFragment();
    }

    public void start() {
        SharedPreferences sharedPreferences = getSharedPreferences("launcher", MODE_PRIVATE);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.fragment, runtimeFragment).commit();
    }

    public void enterLauncher() {
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
