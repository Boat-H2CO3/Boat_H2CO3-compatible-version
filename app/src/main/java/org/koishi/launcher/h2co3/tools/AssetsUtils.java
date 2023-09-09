package org.koishi.launcher.h2co3.tools;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by shenhua on 1/17/2017.
 * Email shenhuanet@126.com
 */
public class AssetsUtils {

    private static final int SUCCESS = 1;
    private static final int FAILED = 0;
    private static AssetsUtils instance;
    private final Context context;
    private FileOperateCallback callback;
    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (callback != null) {
                if (msg.what == SUCCESS) {
                    callback.onSuccess();
                }
                if (msg.what == FAILED) {
                    callback.onFailed(msg.obj.toString());
                }
            }
        }
    };
    private volatile boolean isSuccess;
    private String errorStr;

    private AssetsUtils(Context context) {
        this.context = context;
    }

    public static AssetsUtils getInstance(Context context) {
        if (instance == null)
            instance = new AssetsUtils(context);
        return instance;
    }

    public AssetsUtils copyAssetsToSD(final String assetsPath, final String destinationPath) {
        AssetManager assetManager = context.getAssets();

        try {
            String[] files = assetManager.list(assetsPath);
            if (files != null && files.length > 0) {
                // 创建目标文件夹
                File destinationFolder = new File(destinationPath);
                if (!destinationFolder.exists()) {
                    destinationFolder.mkdirs();
                }

                for (String file : files) {
                    String assetsFilePath = assetsPath + File.separator + file;
                    String destinationFilePath = destinationPath + File.separator + file;

                    copyAssetsToSD(assetsFilePath, destinationFilePath);
                }
            } else {
                // 复制文件
                InputStream inputStream = assetManager.open(assetsPath);
                OutputStream outputStream = new FileOutputStream(destinationPath);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                inputStream.close();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void setFileOperateCallback(FileOperateCallback callback) {
        this.callback = callback;
    }

    private void copyAssetsToDst(Context context, String srcPath, String dstPath) {
        try {
            String[] fileNames = context.getAssets().list(srcPath);
            if (fileNames != null && fileNames.length > 0) {
                File file = new File(dstPath);
                if (!file.exists()) file.mkdirs();
                for (String fileName : fileNames) {
                    if (!srcPath.equals("")) { // assets 文件夹下的目录
                        copyAssetsToDst(context, srcPath + File.separator + fileName, dstPath + File.separator + fileName);
                    } else { // assets 文件夹
                        copyAssetsToDst(context, fileName, dstPath + File.separator + fileName);
                    }
                }
            }
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
            errorStr = e.getMessage();
            isSuccess = false;
        }
    }

    public interface FileOperateCallback {
        void onSuccess();

        void onFailed(String error);
    }

}
