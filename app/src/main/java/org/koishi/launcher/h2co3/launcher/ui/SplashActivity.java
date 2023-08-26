package org.koishi.launcher.h2co3.launcher.ui;

import static com.cainiaohh.module.h2co3customkeyboard.gamecontroller.definitions.manifest.CHTools.LAUNCHER_DATA_DIR;
import static com.cainiaohh.module.h2co3customkeyboard.gamecontroller.definitions.manifest.CHTools.LAUNCHER_FILE_DIR;
import static com.cainiaohh.module.h2co3customkeyboard.gamecontroller.definitions.manifest.CHTools.boatCfg;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.definitions.manifest.CHTools;
import com.google.android.material.snackbar.Snackbar;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import org.koishi.launcher.h2co3.H2CO3Activity;
import org.koishi.launcher.h2co3.R;
import org.koishi.launcher.h2co3.tools.AssetsUtils;
import org.koishi.launcher.h2co3.tools.file.AppExecute;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SplashActivity extends H2CO3Activity {

    final boolean existMcConfig = FileExists(boatCfg);
    final boolean existH2oConfig = FileExists(CHTools.H2CO3CfgPath());
    //boolean existH2oMd = FileExists(LAUNCHER_FILE_DIR+"info.md");
    final boolean existRuntime = FileExists(LAUNCHER_DATA_DIR + "/app_runtime/boat/libopenal.so.1");
    final boolean existGame = FileExists(CHTools.getBoatCfg("game_directory", LAUNCHER_FILE_DIR + ".minecraft"));
    public LinearLayout splash;
    public TextView splashCheck;

    public static boolean FileExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.Theme_Boat_H2CO3_Custom_GREEN);
        setContentView(R.layout.activity_splah);
        splash = findViewById(R.id.splash_view);
        splashCheck = findViewById(R.id.splash_check);
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
                        start();
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

    public void start() {
        new Handler(Looper.getMainLooper()).postDelayed(this::startApp, 500);
    }

    public void updateMarkDown() {
        File md = new File(LAUNCHER_FILE_DIR + "markdown");
        if (md.exists() && md.isDirectory()) {
        } else {
            md.mkdir();
        }
        copyData();
    }

    public void copyData() {
        InputStream in = null;

        FileOutputStream out = null;

        String path = LAUNCHER_FILE_DIR + "markdown/info.md";

        File file = new File(path);
        try {
            in = this.getAssets().open("markdown/info.md"); // 从assets目录下复制
            out = new FileOutputStream(file);
            int length;
            byte[] buf = new byte[1024];
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
    }

    public void updateAuthlib() {
        AssetsUtils.getInstance(SplashActivity.this).copyAssetsToSD("authlib", LAUNCHER_FILE_DIR + "authlib").setFileOperateCallback(new AssetsUtils.FileOperateCallback() {
            @Override
            public void onSuccess() {
                // TODO: 文件复制成功时，主线程回调
            }

            @Override
            public void onFailed(String error) {
                // TODO: 文件复制失败时，主线程回调
            }
        });
    }

    public void startApp() {
        updateMarkDown();
        updateAuthlib();
        if (existRuntime) {
            if (existGame && existMcConfig && existH2oConfig) {
                updateMarkDown();
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("fragment", getResources().getString(R.string.app_name));
                startActivity(i);
                this.finish();
            } else {
                new Thread(() -> {
                    try {
                        AppExecute.output(SplashActivity.this, "h2co3.zip", LAUNCHER_FILE_DIR);
                    } catch (IOException e) {
                        Snackbar.make(splash, e.toString(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }).start();
                splashCheck.setText(getResources().getString(R.string.launcher_initial_install_start));
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    i.putExtra("fragment", getResources().getString(R.string.app_name));
                    startActivity(i);
                    finish();
                }, 1000);
            }
        } else {
            if (existGame && existMcConfig && existH2oConfig) {
                startActivity(new Intent(SplashActivity.this, InitialActivity.class));
                this.finish();
            } else {
                new Thread(() -> {
                    try {
                        AppExecute.output(SplashActivity.this, "h2co3.zip", LAUNCHER_FILE_DIR);
                    } catch (IOException e) {
                        Snackbar.make(splash, e.toString(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }).start();
                updateMarkDown();
                splashCheck.setText(getResources().getString(R.string.launcher_initial_install_start));
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    startActivity(new Intent(SplashActivity.this, InitialActivity.class));
                    finish();
                }, 1000);
            }
        }

    }

}

