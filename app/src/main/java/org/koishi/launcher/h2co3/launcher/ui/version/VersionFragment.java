package org.koishi.launcher.h2co3.launcher.ui.version;

import static com.cainiaohh.module.h2co3customkeyboard.gamecontroller.definitions.manifest.CHTools.LAUNCHER_FILE_DIR;
import static com.cainiaohh.module.h2co3customkeyboard.gamecontroller.definitions.manifest.CHTools.boatCfg;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.definitions.manifest.CHTools;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;
import org.koishi.launcher.h2co3.R;
import org.koishi.launcher.h2co3.adapters.BaseRecycleAdapter;
import org.koishi.launcher.h2co3.launcher.ui.ModsActivity;
import org.koishi.launcher.h2co3.launcher.ui.VanillaActivity;
import org.koishi.launcher.h2co3.tools.data.DbDao;
import org.koishi.launcher.h2co3.tools.file.AppExecute;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VersionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class VersionFragment extends Fragment {

    private final String sd1 = LAUNCHER_FILE_DIR + ".minecraft";
    private Button mbtn_serarch;
    private Dialog mDialog;
    private EditText met_search;
    private LinearLayout page;
    private FloatingActionButton dir, ver;
    private TextView mtv_deleteAll;
    private VersionFragment.SearchDirAdapter mAdapter;
    private String getBoatDir;
    private List<String> verList;

    private DbDao mDbDao;
    @SuppressLint("HandlerLeak")
    final
    Handler han = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                mDialog.dismiss();
            }
            if (msg.what == 1) {
                mDialog.dismiss();
                mDbDao.insertData(getBoatDir);
                mAdapter.updata(mDbDao.queryData(""));
                Snackbar.make(page, getResources().getString(R.string.ver_add_done), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            if (msg.what == 2) {
                //mVerRecyclerView.setAdapter(null);
                //initVers();
                Snackbar.make(page, getResources().getString(R.string.ver_add_done), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        }
    };
    private RecyclerView mRecyclerView, mVerRecyclerView;

    public static VersionFragment newInstance(String param1, String param2) {
        VersionFragment fragment = new VersionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_versions, container, false);
        page = root.findViewById(R.id.dir_layout);

        dir = root.findViewById(R.id.ver_new_dir);
        dir.setOnClickListener(v -> {
            showDirDialog();
        });
        ver = root.findViewById(R.id.ver_new_ver);
        ver.setOnClickListener(v -> {
            startActivity(new Intent(requireActivity(), VanillaActivity.class));
        });
        mRecyclerView = root.findViewById(R.id.mRecyclerView);
        mVerRecyclerView = root.findViewById(R.id.mVerRecyclerView);
        ver.hide();
        initViews();
        initVers();
        //private TabItem rb1,rb2;
        TabLayout tab = root.findViewById(R.id.ver_tab);
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                //  tab.getPosition()  返回数字，从0开始
                // tab.getText()  返回字符串类型，从0开始
                if (tab.getPosition() == 0) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mVerRecyclerView.setVisibility(View.GONE);
                    ver.hide();
                    dir.show();
                }
                if (tab.getPosition() == 1) {
                    mRecyclerView.setVisibility(View.GONE);
                    mVerRecyclerView.setVisibility(View.VISIBLE);
                    dir.hide();
                    ver.show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return root;
    }

    public void initViews() {
        mDbDao = new DbDao(requireActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mAdapter = new VersionFragment.SearchDirAdapter(mDbDao.queryData(""), requireActivity());
        mAdapter.setRvItemOnclickListener(position -> {
            mDbDao.delete(mDbDao.queryData("").get(position));
            mAdapter.updata(mDbDao.queryData(""));
        });
        if (!mDbDao.hasData(sd1)) {
            mDbDao.insertData(sd1);
            mAdapter.updata(mDbDao.queryData(""));
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    public void initVers() {
        File versionlist = new File(CHTools.getBoatCfg("game_directory", "null") + "/versions");
        if (versionlist.isDirectory() && versionlist.exists()) {
            Comparator<Object> cp = Collator.getInstance(Locale.CHINA);
            String[] getVer = versionlist.list();
            List<String> verList = Arrays.asList(Objects.requireNonNull(getVer));  //此集合无法操作添加元素
            verList.sort(cp);
            mVerRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));//设置布局管理器
            VersionFragment.VersionRecyclerAdapter mVerAdapter;
            mVerRecyclerView.setAdapter(mVerAdapter = new VersionFragment.VersionRecyclerAdapter(requireActivity(), verList));
        } else {
            mVerRecyclerView.setAdapter(null);
        }
    }

    public void showDirDialog() {
        mDialog = new Dialog(requireActivity());
        View dialogView = requireActivity().getLayoutInflater().inflate(R.layout.custom_dialog_directory, null);
        mDialog.setContentView(dialogView);
        //mDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundColor(Color.TRANSPARENT);
        MaterialButton cancel = dialogView.findViewById(R.id.custom_dir_cancel);
        MaterialButton start = dialogView.findViewById(R.id.custom_dir_ok);
        TextInputLayout lay = dialogView.findViewById(R.id.dialog_dir_lay);
        lay.setError(getString(R.string.ver_input_hint));
        start.setEnabled(false);
        TextInputEditText et = dialogView.findViewById(R.id.dialog_dir_name);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void afterTextChanged(Editable p1) {
                String value = Objects.requireNonNull(et.getText()).toString();
                if (value.matches("(/storage/emulated/0|/sdcard|/mnt/sdcard).*")) {
                    lay.setErrorEnabled(false);
                    start.setEnabled(true);
                } else {
                    lay.setError(getString(R.string.ver_input_hint));
                    start.setEnabled(false);
                }
            }
        });

        cancel.setOnClickListener(v -> mDialog.dismiss());
        start.setOnClickListener(v -> {
            if (Objects.requireNonNull(et.getText()).toString().trim().length() != 0) {
                boolean hasData = mDbDao.hasData(et.getText().toString().trim());
                if (!hasData) {
                    File f = new File(et.getText().toString().trim());
                    if (f.exists()) {
                        if (f.isDirectory()) {
                            getBoatDir = et.getText().toString();
                            newDir();
                        } else {
                            Snackbar.make(page, getResources().getString(R.string.ver_not_dir), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    } else {
                        getBoatDir = et.getText().toString();
                        newDir();
                    }
                } else {
                    Snackbar.make(page, getResources().getString(R.string.ver_already_exists), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                //
                mAdapter.updata(mDbDao.queryData(""));

            } else {
                Snackbar.make(page, "Please input", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        });

        mDialog.setContentView(dialogView);
        WindowManager windowManager = requireActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 0.9); //设置宽度 dialog.getWindow().setAttributes(lp);
        mDialog.show();
    }

    public void newDir() {
        new Thread(() -> {
            try {
                AppExecute.output(requireActivity(), "pack.zip", getBoatDir);
                //Snackbar.make(getView(), getResources().getString(R.string.install_done), Snackbar.LENGTH_LONG)
                //.setAction("Action", null).show();
                han.sendEmptyMessage(1);
            } catch (IOException e) {
                Snackbar.make(page, getResources().getString(R.string.ver_not_right_dir) + e, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                han.sendEmptyMessage(0);
            }
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        //updateDirList();
        //initViews();
        //mRecyclerView.post(() -> updateDirList());

        String currentDir = CHTools.getBoatCfg("game_directory", "Null");
        File f = new File(currentDir);
        //if (mRecyclerView.isComputingLayout()) {
        //updateDirList();
        //if (f.exists()&&f.isDirectory()){
        //if (mDbDao.hasData(dir.trim())){
        //mDbDao.delete(dir);
        //updateDirList();
        //}else{
//
        // }
        //}
        // }
        if (f.exists() && f.isDirectory()) {
            initVers();
        } else {
            setDir(sd1);
            Snackbar.make(page, getResources().getString(R.string.ver_null_dir), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            mDbDao.delete(currentDir);
            mAdapter.updata(mDbDao.queryData(""));
            initVers();
        }
    }

    public void setDir(String dir) {
        try {
            FileInputStream in = new FileInputStream(boatCfg);
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();
            String str = new String(b);
            JSONObject json = new JSONObject(str);
            json.remove("game_directory");
            json.remove("game_assets");
            json.remove("assets_root");
            json.remove("currentVersion");
            json.put("game_directory", dir);
            json.put("game_assets", dir + "/assets/virtual/legacy");
            json.put("assets_root", dir + "/assets");
            json.put("currentVersion", dir + "/versions");
            FileWriter fr = new FileWriter(boatCfg);
            fr.write(json.toString());
            fr.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    class SearchDirAdapter extends BaseRecycleAdapter<String> {
        public SearchDirAdapter(List<String> datas, Context mContext) {
            super(datas, mContext);
        }

        @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
        @Override
        protected void bindData(BaseViewHolder holder, final int position) {

            TextView textView = (TextView) holder.getView(R.id.tv_record);
            TextView textView1 = (TextView) holder.getView(R.id.tv_name);
            MaterialCardView lay = (MaterialCardView) holder.getView(R.id.ver_item);
            ImageView check = (ImageView) holder.getView(R.id.ver_check_icon);
            MaterialButton del = (MaterialButton) holder.getView(R.id.tv_remove_dir);
            MaterialButton delDir = (MaterialButton) holder.getView(R.id.tv_del_dir);
            textView.setText(datas.get(position));
            if (datas.get(position).equals(CHTools.getBoatCfg("game_directory", "null"))) {
                //lay.setSelected(true);
                //check.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_check_file_blue_true));
                lay.setStrokeWidth(15);
                lay.setStrokeColor(getResources().getColor(android.R.color.darker_gray));
                lay.setElevation(5);
            } else {
                //lay.setSelected(false);
                //check.setImageDrawable(getResources().getDrawable(R.drawable.cv_shape));
                lay.setStrokeWidth(0);
            }
            String sd3 = LAUNCHER_FILE_DIR + ".minecraft";
            String sd2 = LAUNCHER_FILE_DIR + ".minecraft";
            if (datas.get(position).equals(sd1) || datas.get(position).equals(sd2) || datas.get(position).equals(sd3)) {
                del.setVisibility(View.GONE);
                delDir.setVisibility(View.GONE);
            } else {
                del.setVisibility(View.VISIBLE);
                delDir.setVisibility(View.VISIBLE);
            }

            String str1 = textView.getText().toString();
            str1 = str1.substring(0, str1.lastIndexOf("/"));
            int idx = str1.lastIndexOf("/");
            str1 = str1.substring(idx + 1);
            textView1.setText(str1);

            File f = new File(textView.getText().toString());
            if (f.isDirectory() && f.exists()) {

            } else {
                check.setImageDrawable(getResources().getDrawable(R.drawable.xicon_red));
                delDir.setVisibility(View.VISIBLE);
            }
            lay.setOnClickListener(v -> {
                if (f.exists() && f.isDirectory()) {
                    setDir(textView.getText().toString());
                    //finish();
                    //startActivity(new Intent(VersionFragment.this,VersionFragment.class));
                    mAdapter.updata(mDbDao.queryData(""));
                    mVerRecyclerView.setAdapter(null);
                    initVers();
                } else {
                    if (null != mRvItemOnclickListener) {
                        mRvItemOnclickListener.RvItemOnclick(position);
                        mAdapter.updata(mDbDao.queryData(""));
                        Snackbar.make(page, getResources().getString(R.string.ver_null_dir), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        mVerRecyclerView.setAdapter(null);
                    }
                }

            });
            //
            del.setOnClickListener(view -> {
                //if (null != mRvItemOnclickListener) {
                //if (datas.get(position).equals(CHTools.getBoatCfg("game_directory", "null"))) {
                //setDir(sd1);
                //} else {

                //}
                // }
                if (null != mRvItemOnclickListener) {
                    mRvItemOnclickListener.RvItemOnclick(position);
                }
            });

            delDir.setOnClickListener(view -> {
                if (null != mRvItemOnclickListener) {
                    if (datas.get(position).equals(CHTools.getBoatCfg("game_directory", "null"))) {
                        setDir(sd1);
                    }
                    //添加"Yes"按钮
                    //添加"Yes"按钮
                    AlertDialog alertDialog1 = new MaterialAlertDialogBuilder(requireActivity())
                            .setTitle(getResources().getString(R.string.action))//标题
                            .setIcon(R.drawable.ic_boat)//图标
                            .setMessage(R.string.ver_if_del)
                            .setPositiveButton("Yes Yes Yes", (dialogInterface, i) -> {
                                File f1 = new File(datas.get(position));
                                //TODO
                                mRvItemOnclickListener.RvItemOnclick(position);
                                //finish();
                                //startActivity(new Intent(VersionFragment.this,VersionFragment.class));
                                mAdapter.updata(mDbDao.queryData(""));
                                new Thread(() -> {
                                    //String file2= "/data/data/org.koishi.launcher.h2co3/app_runtime";
                                    deleteDirWihtFile(f1);
                        /*
                         File file = new File(file2);
                         if(file.isDirectory()){
                         deleteDirectory(file2);
                         }
                         if(file.isFile()){
                         deleteFile(file2);
                         }
                         */
                                }).start();

                            })
                            .setNegativeButton("No No No", (dialogInterface, i) -> {
                                //TODO
                            })
                            .create();

                    alertDialog1.show();
                }
            });

        }


        public void deleteDirWihtFile(File dir) {
            if (dir == null || !dir.exists() || !dir.isDirectory())
                return;
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                if (file.isFile())
                    file.delete(); // 删除所有文件
                else if (file.isDirectory())
                    deleteDirWihtFile(file); // 递规的方式删除文件夹
            }
            dir.delete();// 删除目录本身
        }

        @Override
        public int getLayoutId() {
            return R.layout.dir_item;
        }

        public void setDir(String dir) {
            try {
                FileInputStream in = new FileInputStream(boatCfg);
                byte[] b = new byte[in.available()];
                in.read(b);
                in.close();
                String str = new String(b);
                JSONObject json = new JSONObject(str);
                json.remove("game_directory");
                json.remove("game_assets");
                json.remove("assets_root");
                json.remove("currentVersion");
                json.put("game_directory", dir);
                json.put("game_assets", dir + "/assets/virtual/legacy");
                json.put("assets_root", dir + "/assets");
                json.put("currentVersion", dir + "/versions");
                FileWriter fr = new FileWriter(boatCfg);
                fr.write(json.toString());
                fr.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    class VersionRecyclerAdapter extends RecyclerView.Adapter<VersionFragment.VersionRecyclerAdapter.MyViewHolder> {
        private final List<String> datas;
        private final LayoutInflater inflater;

        public VersionRecyclerAdapter(Context context, List<String> datas) {
            inflater = LayoutInflater.from(context);
            this.datas = datas;
        }

        //创建每一行的View 用RecyclerView.ViewHolder包装
        @NonNull
        @Override
        public VersionFragment.VersionRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.version_local_item, null);
            return new VersionFragment.VersionRecyclerAdapter.MyViewHolder(itemView);
        }

        //给每一行View填充数据
        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public void onBindViewHolder(VersionFragment.VersionRecyclerAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.textview.setText(datas.get(position));
            File f = new File(CHTools.getBoatCfg("game_directory", "Null") + "/versions/" + datas.get(position));
            if (f.isDirectory() && f.exists()) {
            } else {
                holder.rl.setEnabled(false);
                holder.ic.setImageDrawable(getResources().getDrawable(R.drawable.xicon_red));
            }
            holder.rl.setOnClickListener(v -> {
                holder.dirs = datas.get(position);
                showExecDialog(holder.dirs);
            });
            holder.btn.setOnClickListener(v -> {
                //添加"Yes"按钮
                //添加"Yes"按钮
                @SuppressLint("UseCompatLoadingForDrawables") AlertDialog alertDialog1 = new AlertDialog.Builder(requireActivity())
                        .setTitle(getResources().getString(R.string.action))//标题
                        .setIcon(R.drawable.ic_boat)//图标
                        .setMessage(R.string.ver_if_del)
                        .setPositiveButton("Yes Yes Yes", (dialogInterface, i) -> {
                            holder.ic.setImageDrawable(getResources().getDrawable(R.drawable.xicon_red));
                            holder.btn.setVisibility(View.INVISIBLE);
                            holder.textview.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                            holder.rl.setEnabled(false);
                            File f1 = new File(CHTools.getBoatCfg("game_directory", "Null") + "/versions/" + datas.get(position));
                            //TODO
                            new Thread(() -> {
                                //String file2= "/data/data/org.koishi.launcher.h2co3/app_runtime";
                                if (f1.isDirectory()) {
                                    deleteDirWihtFile(f1);
                                } else {
                                    deleteFile(CHTools.getBoatCfg("game_directory", "Null") + "/versions/" + datas.get(position));
                                }
                        /*
                         File file = new File(file2);
                         if(file.isDirectory()){
                         deleteDirectory(file2);
                         }
                         if(file.isFile()){
                         deleteFile(file2);
                         }
                         */
                                han.sendEmptyMessage(2);
                            }).start();

                        })
                        .setNegativeButton("No No No", (dialogInterface, i) -> {
                            //TODO
                        })
                        .create();

                alertDialog1.show();
            });
        }

        public void showExecDialog(String dir) {
            mDialog = new Dialog(requireActivity());
            View dialogView = requireActivity().getLayoutInflater().inflate(R.layout.custom_dialog_choose_exec, null);
            mDialog.setContentView(dialogView);
            String load = CHTools.getAppCfg("allVerLoad", "false");
            String loadDir;
            if (load.equals("true")) {
                loadDir = CHTools.getBoatCfg("game_directory", "Null") + "/versions/" + dir;
            } else {
                loadDir = CHTools.getBoatCfg("game_directory", "Null");
            }
            LinearLayout lay = dialogView.findViewById(R.id.ver_exec_mod);
            lay.setOnClickListener(v -> {
                Intent i = new Intent(requireActivity(), ModsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("mod", loadDir);
                i.putExtras(bundle);
                //i.putExtra("dat",c);
                requireActivity().startActivity(i);
            });
            //mDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundColor(Color.TRANSPARENT);

            mDialog.setContentView(dialogView);
            WindowManager windowManager = requireActivity().getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
            lp.width = (int) (display.getWidth() * 0.9); //设置宽度 dialog.getWindow().setAttributes(lp);
            mDialog.show();
        }

        //数据源的数量
        @Override
        public int getItemCount() {
            return datas.size();
        }

        public void deleteDirWihtFile(File dir) {
            if (dir == null || !dir.exists() || !dir.isDirectory())
                return;
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                if (file.isFile())
                    file.delete(); // 删除所有文件
                else if (file.isDirectory())
                    deleteDirWihtFile(file); // 递规的方式删除文件夹
            }
            dir.delete();// 删除目录本身
        }

        public void deleteFile(String filePath) {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                file.delete();
            }
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private final TextView textview;
            private final MaterialButton btn;
            private final ImageView ic;
            private final MaterialCardView rl;
            private String dirs;

            public MyViewHolder(View itemView) {
                super(itemView);
                textview = itemView.findViewById(R.id.ver_name);
                btn = itemView.findViewById(R.id.ver_remove);
                rl = itemView.findViewById(R.id.ver_item);
                ic = itemView.findViewById(R.id.ver_icon);
            }
        }
    }
}