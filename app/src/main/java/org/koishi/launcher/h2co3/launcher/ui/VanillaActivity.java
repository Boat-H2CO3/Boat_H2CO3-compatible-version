package org.koishi.launcher.h2co3.launcher.ui;

import static org.koishi.launcher.h2co3core.utils.cainiaohh.CHTools.*;

import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.download.service.downloader.HttpUtil;
import com.download.service.util.VersionUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mistake.revision.Download.DownloadFragment;
import com.mistake.revision.LauncherSettingModel;
import com.mistake.revision.adapter.Version_List_Adpater;
import com.mistake.revision.view.PullListView;

import org.koishi.launcher.h2co3.R;
import org.koishi.launcher.h2co3.component.H2CO3Activity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

import org.koishi.launcher.h2co3core.utils.cainiaohh.CHTools;
import okhttp3.Call;
import okhttp3.Response;

public class VanillaActivity extends H2CO3Activity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    private static final int REQUEST_OVERLAY = 4444;
    private final MyHandler mHandler = new MyHandler(Looper.getMainLooper());
    private LauncherSettingModel settingModel;
    private Spinner spDownloadSourceMode;
    private String download_source;
    private RadioButton rbRelease;
    private RadioButton rbSnapshot;
    private RadioButton rbOldbeta;
    private PullListView list;
    private Version_List_Adpater adapter;

    public static String getDir() {
        try {
            FileInputStream in = new FileInputStream(BOATCFG);
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();
            String str = new String(b);
            org.json.JSONObject json = new org.json.JSONObject(str);
            return json.getString("game_directory");
        } catch (Exception e) {
            System.out.println(e);
        }
        return MINECRAFT_DIR;
    }

    public static void verifyStoragePermissions(H2CO3Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CHTools.loadPaths(this);
        setContentView(R.layout.activity_download);

        //getWindow().setStatusBarColor(getResources().getColor(R.color.material_blue_dark));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        verifyStoragePermissions(this);//读写权限获取
        requestOverlayPermission();//悬浮窗权限获取

        spDownloadSourceMode = findViewById(R.id.sp_download_source_mode);

        RadioGroup rgSelect = findViewById(R.id.rg_select);

        rbRelease = findViewById(R.id.rb_release);
        rbSnapshot = findViewById(R.id.rb_snapshot);
        rbOldbeta = findViewById(R.id.rb_old_beta);

        list = findViewById(R.id.loadingversionFileListView1);

        get("https://launchermeta.mojang.com/mc/game/version_manifest.json");
        rbRelease.setEnabled(false);
        rbSnapshot.setEnabled(false);
        rbOldbeta.setEnabled(false);

        rgSelect.setOnCheckedChangeListener((group, checkedId) -> {
			/*if(adapter==null){
			 get("https://download.mcbbs.net/mc/game/version_manifest.json");
			 }{*/
            switch (checkedId) {
                case R.id.rb_release:
                    adapter.setType(1);
                    //Toast.makeText(MainActivity.this, checkedId , Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rb_snapshot:
                    adapter.setType(0);
                    break;
                case R.id.rb_old_beta:
                    adapter.setType(2);
                    break;
                default:
                    break;
            }
        });
        String[] mItems = getResources().getStringArray(R.array.download_source);
        ArrayAdapter<String> adapter_source = new ArrayAdapter<>(VanillaActivity.this,
                android.R.layout.simple_spinner_dropdown_item, mItems);
        spDownloadSourceMode.setAdapter(adapter_source);
        spDownloadSourceMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /*
             * parent:指示spinner
             * view：显示item的空间，这里指TextView
             * position：被选中的item的位置
             * id：选中项的id
             * */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        list.setAdapter(null);
                        rbRelease.setChecked(true);
                        rbRelease.setEnabled(false);
                        rbSnapshot.setEnabled(false);
                        rbOldbeta.setEnabled(false);
                        get("https://launchermeta.mojang.com/mc/game/version_manifest.json");
                        download_source = "https://launchermeta.mojang.com";
                        //Toast.makeText(VanillaActivity.this, download_source , Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        list.setAdapter(null);
                        rbRelease.setChecked(true);
                        rbRelease.setEnabled(false);
                        rbSnapshot.setEnabled(false);
                        rbOldbeta.setEnabled(false);
                        get("https://download.mcbbs.net/mc/game/version_manifest.json");
                        download_source = "https://download.mcbbs.net";
                        //Toast.makeText(VanillaActivity.this, download_source , Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        list.setAdapter(null);
                        rbRelease.setChecked(true);
                        rbRelease.setEnabled(false);
                        rbSnapshot.setEnabled(false);
                        rbOldbeta.setEnabled(false);
                        get("https://bmclapi2.bangbang93.com/mc/game/version_manifest.json");
                        download_source = "https://bmclapi2.bangbang93.com";
                        //Toast.makeText(VanillaActivity.this, download_source , Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vanilla, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.download_ref) {
            if (spDownloadSourceMode.getSelectedItem().toString().equals(getString(R.string.spinner_official))) {
                list.setAdapter(null);
                rbRelease.setChecked(true);
                get("https://launchermeta.mojang.com/mc/game/version_manifest.json");
                download_source = "https://launchermeta.mojang.com";
                //Toast.makeText(MainActivity.this, download_source , Toast.LENGTH_SHORT).show();
            } else if (spDownloadSourceMode.getSelectedItem().toString().equals(getString(R.string.spinner_mcbbs))) {
                list.setAdapter(null);
                rbRelease.setChecked(true);
                get("https://download.mcbbs.net/mc/game/version_manifest.json");
                download_source = "https://download.mcbbs.net";
                //Toast.makeText(MainActivity.this, download_source , Toast.LENGTH_SHORT).show();
            } else if (spDownloadSourceMode.getSelectedItem().toString().equals(getString(R.string.spinner_bmclapi))) {
                list.setAdapter(null);
                rbRelease.setChecked(true);
                get("https://bmclapi2.bangbang93.com/mc/game/version_manifest.json");
                download_source = "https://bmclapi2.bangbang93.com";
                //Toast.makeText(MainActivity.this, download_source , Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    public void set(String x) {

        Message msg = new Message();
        msg.what = 1;
        msg.obj = x;

        mHandler.sendMessage(msg);
    }

    private void getlist(String json) {

        ArrayList<VersionUtil> listItems = Online_Version_List(json);
        adapter = new Version_List_Adpater(this, listItems, 1);//初始化type1
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onInvalidated() {

            }
        });

        list.setAdapter(adapter);
        adapter.setOnItemDepartment(new Version_List_Adpater.OnItemDepartment() {

            @Override
            public void OnItemDepartmentItem(String type) {
                // TODO: Implement this method
            }

            @Override
            public void OnItemDepartmentItem(String id, String url) {

                DownloadFragment showDialog = new DownloadFragment();
                Bundle bundle = new Bundle();
                bundle.putString("version", id);
                bundle.putString("game", getDir());
                bundle.putString("address", download_source);
                showDialog.setArguments(bundle);
                showDialog.show(getSupportFragmentManager(), "show");

            }
        });
    }

    private void get(String url) {
        HttpUtil.sendOkHttpRequest(url, new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull final IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call p1, @NonNull Response p2) throws IOException {
                final String url = p2.body().string();
                new Thread(() -> set(url)).start();
            }
        });
    }

    public ArrayList<VersionUtil> Online_Version_List(String json) throws JSONException {
        ArrayList<VersionUtil> data_list = new ArrayList<>();
        if (json != null) {

            JSONObject root = JSON.parseObject(json);
            JSONArray branch = root.getJSONArray("versions");
            for (int i = 0; i < branch.size(); i++) {
                VersionUtil util = new VersionUtil();
                JSONObject value = (JSONObject) branch.get(i);
                String brach_id = (String) value.get("id");
                String brach_type = (String) value.get("type");
                String brach_url = ((String) value.get("url"));

                util.id(brach_id);
                util.type(brach_type);
                util.url(brach_url);
                data_list.add(util);
            }

        } else {

            return null;
        }
        return data_list;
    }

    private void requestOverlayPermission() {

    }

    private String ReadString(String sourcePath) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(sourcePath));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
                sb.append("\r\n");
            }
            br.close();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
        return sb.toString();
    }

    private LauncherSettingModel Read(String json) {
        try {
            if (json != null) {
                Gson gson = new Gson();
                //要转化的类型
                //Type导入的是java.lang.reflect.Type的包
                //TypeToken导入的是 com.google.gson.reflect.TypeToken的包
                Type type = new TypeToken<LauncherSettingModel>() {
                }.getType();
                return gson.fromJson(json, type);
            } else {
                return null;
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private class MyHandler extends Handler {
        public MyHandler(Looper mainLooper) {
        }

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1) {
                getlist((String) msg.obj);
                rbRelease.setEnabled(true);
                rbSnapshot.setEnabled(true);
                rbOldbeta.setEnabled(true);
            }
        }
    }
}

