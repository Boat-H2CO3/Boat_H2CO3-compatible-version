package org.koishi.launcher.h2co3.launcher.ui;

import static com.cainiaohh.module.h2co3customkeyboard.gamecontroller.definitions.manifest.CHTools.LAUNCHER_DATA_DIR;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

import org.koishi.launcher.h2co3.H2CO3Activity;
import org.koishi.launcher.h2co3.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class InitialActivity extends H2CO3Activity {

    @SuppressLint("HandlerLeak")
    final
    Handler han = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Intent intent1 = new Intent(InitialActivity.this, MainActivity.class);
            intent1.putExtra("fragment", getResources().getString(R.string.app_name));
            startActivity(intent1);
            InitialActivity.this.finish();

        }
    };
    public ConstraintLayout initial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.Theme_Boat_H2CO3_Custom_GREEN);
        setContentView(R.layout.activity_initial);
        initial = findViewById(R.id.initial_view);
        new Thread(() -> {
            try {
                unZip(InitialActivity.this, "boat.zip", LAUNCHER_DATA_DIR + "/app_runtime/");
                unZip(InitialActivity.this, "app_runtime.zip", LAUNCHER_DATA_DIR + "/app_runtime/");
                han.sendEmptyMessage(0);
            } catch (IOException e) {
                Snackbar.make(initial, e.toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        }).start();
    }

    private void unZip(Context context, String assetName, String outputDirectory) throws IOException {
        File file = new File(outputDirectory);
        if (!file.exists()) {
            file.mkdirs();
        }

        InputStream inputStream;
        inputStream = context.getAssets().open(assetName);
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        byte[] buffer = new byte[1024 * 1024];
        int count;
        while (zipEntry != null) {
            //如果是一个目录
            if (zipEntry.isDirectory()) {
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                file.mkdir();
            } else {
                //如果是文件
                file = new File(outputDirectory + File.separator
                        + zipEntry.getName());
                //创建该文件
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                while ((count = zipInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, count);
                }

                fileOutputStream.close();
            }
            //定位到下一个文件入口
            zipEntry = zipInputStream.getNextEntry();

        }
        zipInputStream.close();
    }

}