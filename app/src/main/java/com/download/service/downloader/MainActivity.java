package com.download.service.downloader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.koishi.launcher.h2co3.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String MANIFEST_URL = "https://launchermeta.mojang.com/mc/game/version_manifest.json";
    private static final String DOWNLOAD_BASE_URL = "https://resources.download.minecraft.net/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new DownloadManifestTask().execute();
    }

    private void downloadResource(String url) {
        // 实现资源下载逻辑，你可以使用其他第三方库或原生 HttpURLConnection 进行下载
        // 这里只提供一个示例

        // 省略下载逻辑
        Log.d("",url);
    }

    private class DownloadManifestTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... voids) {
            List<String> resourceUrls = new ArrayList<>();

            try {
                URL manifestUrl = new URL(MANIFEST_URL);
                HttpURLConnection connection = (HttpURLConnection) manifestUrl.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject manifestJson = new JSONObject(response.toString());
                JSONArray versionsArray = manifestJson.getJSONArray("versions");
                // 获取最新版本的 manifest 文件 URL
                String latestManifestUrl = versionsArray.getJSONObject(0).getString("url");

                URL versionManifestUrl = new URL(latestManifestUrl);
                connection = (HttpURLConnection) versionManifestUrl.openConnection();
                connection.setRequestMethod("GET");

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject versionManifestJson = new JSONObject(response.toString());
                JSONArray filesArray = versionManifestJson.getJSONObject("assetIndex").getJSONArray("files");

                for (int i = 0; i < filesArray.length(); i++) {
                    JSONObject fileObj = filesArray.getJSONObject(i);
                    String resourceHash = fileObj.getString("hash");
                    String resourceUrl = DOWNLOAD_BASE_URL + resourceHash.substring(0, 2) + "/" + resourceHash;
                    resourceUrls.add(resourceUrl);
                }

            } catch (IOException | JSONException e) {
                Log.e("DownloadManifestTask", "Error: " + e.getMessage());
            }

            return resourceUrls;
        }

        @Override
        protected void onPostExecute(List<String> resourceUrls) {
            super.onPostExecute(resourceUrls);

            // 下载资源
            for (String url : resourceUrls) {
                downloadResource(url);
            }
        }
    }
}