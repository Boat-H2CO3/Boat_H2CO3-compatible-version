package org.koishi.launcher.h2co3.ui.custom;

import static org.koishi.h2co3.tools.CHTools.LAUNCHER_DATA_DIR;
import static org.koishi.h2co3.tools.CHTools.LAUNCHER_FILE_DIR;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.koishi.h2co3.tools.CHTools;
import org.koishi.launcher.h2co3.LogcatActivity;
import org.koishi.launcher.h2co3.MainActivity;
import org.koishi.launcher.h2co3.R;
import org.koishi.launcher.h2co3.SplashActivity;

import java.io.File;
import java.util.Objects;

import rikka.material.preference.MaterialSwitchPreference;

public class LauncherSettingsFragment extends PreferenceFragmentCompat {

    @SuppressLint("HandlerLeak")
    final
    Handler han = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                Intent intent1 = new Intent(getActivity(), MainActivity.class);
                intent1.putExtra("fragment", getResources().getString(R.string.app_name));
                startActivity(intent1);
                Toast.makeText(getActivity(), getResources().getString(R.string.delete), Toast.LENGTH_SHORT).show();
                requireActivity().finish();
            }
        }
    };
    public Preference delRun, delCfg, crash, logView, langTr;
    public EditTextPreference etId, editJvm, editMcf;
    public MaterialSwitchPreference appTheme;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.launcher_settings_preferences, rootKey);

        delRun = findPreference("set_reset_cfg");
        if (delRun != null) {
            delRun.setOnPreferenceClickListener(preference -> {
                del();
                return true;
            });
        }
        delCfg = findPreference("set_reset_run");
        if (delCfg != null) {
            delCfg.setOnPreferenceClickListener(preference -> {
                reset();
                return true;
            });
        }
        appTheme = findPreference("material");
        assert appTheme != null;
        if (Build.VERSION.SDK_INT >= 31) {
            appTheme.setEnabled(true);
        } else {
            appTheme.setSummary(getResources().getString(R.string.can_not_material_you));
            appTheme.setEnabled(false);
        }
        langTr = findPreference("set_lang_tr");
        if (langTr != null) {
            langTr.setOnPreferenceClickListener(preference -> {
                CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
                intent.launchUrl(requireActivity(), Uri.parse("https://wwa.lanzoui.com/iGyPkvlgqdc\n"));
                return true;
            });
        }
        logView = findPreference("set_log");
        if (logView != null) {
            logView.setOnPreferenceClickListener(preference -> {
                Intent i = (new Intent(requireActivity(), LogcatActivity.class));
                startActivity(i);
                return true;
            });
        }
        crash = findPreference("set_crash");
        if (crash != null) {
            crash.setOnPreferenceClickListener(preference -> {
                crash();
                return true;
            });
        }
    }

    public void reset() {
        //添加"Yes"按钮
        AlertDialog alertDialog1 = new MaterialAlertDialogBuilder(getActivity())
                .setTitle(getResources().getString(R.string.action))//标题
                .setIcon(R.drawable.ic_boat)//图标
                .setMessage(getResources().getString(R.string.resetwarn))
                .setPositiveButton("Yes Yes Yes", (dialogInterface, i) -> {
                    delRun.setEnabled(false);
                    delCfg.setEnabled(false);
                    //TODO
                    new Thread(() -> {
                        //String file2= "/data/data/org.koishi.launcher.h2co3/app_runtime";
                        deleteFile(LAUNCHER_FILE_DIR + "h2co3Cfg.json");
                        deleteFile(CHTools.H2CO3CfgPath());
                        requireActivity().finish();
                        startActivity(new Intent(requireActivity(), SplashActivity.class));
                    }).start();

                })
                .setNegativeButton("No No No", (dialogInterface, i) -> {
                    //TODO
                })
                .create();

        alertDialog1.show();
        //添加"Yes"按钮
    }

    public void del() {
        //添加"Yes"按钮
        //添加"Yes"按钮
        AlertDialog alertDialog1 = new MaterialAlertDialogBuilder(getActivity())
                .setTitle(getResources().getString(R.string.action))//标题
                .setIcon(R.drawable.ic_boat)//图标
                .setMessage(getResources().getString(R.string.resetwarn))
                .setPositiveButton("Yes Yes Yes", (dialogInterface, i) -> {
                    killlist();
                    Intent intent = new Intent(getActivity(), SplashActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
//杀掉以前进
                    android.os.Process.killProcess(android.os.Process.myPid());

                })
                .setNegativeButton("No No No", (dialogInterface, i) -> {
                    //TODO
                })
                .create();

        alertDialog1.show();
    }

    private void killlist() {
        File file2 = new File(LAUNCHER_DATA_DIR);
        deleteDirWihtFile(file2);
    }

    public void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }

    private void deleteDirWihtFile(File file2) {
        if (file2 == null || !file2.exists() || !file2.isDirectory())
            return;
        for (File file : Objects.requireNonNull(file2.listFiles())) {
            if (file.isFile())
                file.delete();
            else if (file.isDirectory())
                deleteDirWihtFile(file);

        }
        file2.delete();
    }

    private void crash() {
        throw new RuntimeException("Crash test from SettingsActivity. 这是从设置里点进来的崩溃，给我发这个是没用的！！请在log.txt或者client_output.txt中找原因。路径是/sdcard/games/org.koishi.launcher/h2co3。");
    }
}
