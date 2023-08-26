package com.mistake.revision;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class BUG extends Application implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(@NonNull Activity p1, Bundle p2) {
        // TODO: Implement this method

    }

    @Override
    public void onActivityStarted(@NonNull Activity p1) {
        // TODO: Implement this method
    }

    @Override
    public void onActivityResumed(@NonNull Activity p1) {
        // TODO: Implement this method

    }

    @Override
    public void onActivityPaused(@NonNull Activity p1) {
        // TODO: Implement this method

    }

    @Override
    public void onActivityStopped(@NonNull Activity p1) {
        // TODO: Implement this method
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity p1, @NonNull Bundle p2) {
        // TODO: Implement this method
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity p1) {
        // TODO: Implement this method
    }


    @Override
    public void onCreate() {

        super.onCreate();


        this.registerActivityLifecycleCallbacks(this);
        //设置默认的未捕获的异常处理程序
        Thread.setDefaultUncaughtExceptionHandler((p1, p2) -> {


            Writer i = new StringWriter();
            p2.printStackTrace(new PrintWriter(i));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            p2.printStackTrace(new PrintStream(baos));
            byte[] bug = baos.toByteArray();

            try {
                FileOutputStream f = new FileOutputStream("/sdcard/boat/error.log");

                //f.write(i.toString().getBytes());


                f.write(bug);


                f.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Utils.writeFile("/sdcard/boat/err.log",i.toString());
        });

        //instance = this;
        // 在设置文件名参数时，不要带 “.xml” 后缀，android会自动添加
        //SpUtils.getInstance(this,"setting");
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);


    }
}
