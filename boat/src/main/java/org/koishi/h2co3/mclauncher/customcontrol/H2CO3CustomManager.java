package org.koishi.h2co3.mclauncher.customcontrol;
/*
 * Copyright 2023.  ShirosakiMio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.koishi.h2co3.mclauncher.gamecontroller.codes.Translation;
import org.koishi.h2co3.mclauncher.gamecontroller.definitions.id.key.KeyEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import cosine.boat.R;


public class H2CO3CustomManager implements OnTouchListener, CompoundButton.OnCheckedChangeListener, OnClickListener {
    public static int screenHeight, scrennWidth;
    private boolean isInit = false;
    private String CustomButtonStoragePath;
    private Context context;
    private Gson mGson;
    private ViewGroup Container;

    private Map<String, H2CO3CustomButton> CustomButtonGroup;
    private boolean isEditCustomButton = false;
    private Translation KeyConverters;

    private AlertDialog CustomButtonDialog;
    private AlertDialog ColorSelectDialog;
    private View CustomButtonMenuLayoutContainer;
    private EditText alert_h2co3custom_main_edit_name;
    private TextView alert_h2co3custom_main_tv_height_left;
    private EditText alert_h2co3custom_main_edit_height;
    private TextView alert_h2co3custom_main_tv_height_right;
    private TextView alert_h2co3custom_main_tv_width_left;
    private EditText alert_h2co3custom_main_edit_width;
    private TextView alert_h2co3custom_main_tv_width_right;
    private TextView alert_h2co3custom_main_tv_textsize_left;
    private EditText alert_h2co3custom_main_edit_textsize;
    private TextView alert_h2co3custom_main_tv_textsize_right;
    private TextView alert_h2co3custom_main_tv_round_left;
    private EditText alert_h2co3custom_main_edit_round;
    private TextView alert_h2co3custom_main_tv_round_right;
    private EditText alert_h2co3custom_main_edit_strokecolor;
    private EditText alert_h2co3custom_main_edit_textcolor;
    private RadioButton alert_h2co3custom_main_radio_normalbtn;
    private RadioButton alert_h2co3custom_main_radio_cmdbtn;
    private RadioButton alert_h2co3custom_main_radio_vsbbtn;
    private LinearLayout alert_h2co3custom_main_layout_normalbtn;
    private EditText alert_h2co3custom_main_edit_key1;
    private EditText alert_h2co3custom_main_edit_key2;
    private EditText alert_h2co3custom_main_edit_key3;
    private final Runnable PressTimeTask = new Runnable() {
        @Override
        public void run() {
            AlertDialog dialog = new MaterialAlertDialogBuilder(context)
                    .setTitle("警告")
                    .setMessage("是否删除该按键?")
                    .setPositiveButton("Ok", (dia, which) -> {
                        Container.removeView(CurrentKeyPress);
                        CustomButtonGroup.remove(CurrentKeyPress.getID());
                    })
                    .setNegativeButton("取消", null)
                    .create();
            dialog.show();
        }
    };
    private CheckBox alert_h2co3custom_main_check_autokeep;
    private CheckBox alert_h2co3custom_main_check_mousectrl;
    private LinearLayout alert_h2co3custom_main_layout_cmdbtn;
    private EditText alert_h2co3custom_main_edit_cmd;
    private LinearLayout alert_h2co3custom_main_layout_vsbbtn;
    private Button alert_h2co3custom_main_btn_btnselect;
    private Button alert_h2co3custom_main_btn_ok;
    private Button alert_h2co3custom_main_btn_cancle;
    private View alert_h2co3custom_main_view_strokecolor;
    private View alert_h2co3custom_main_view_textcolor;
    //
    private boolean SelectCustomButtonMode = false;

    private ContainerCallBack ContainerCallBack;
    private CustomButtonCallback CustomButtonCallback;

    private boolean EditCustomButtonMode = false;

    private int PointerX;
    private int PointerY;

    private H2CO3DialogSelectKey KeySelectDialog;
    private final Handler ContainerCallBackPressTimer = new Handler();
    private final Runnable ContainerCallBackLongTouchTask = new Runnable() {
        @Override
        public void run() {
            ContainerCallBack.LongTouchListener();
        }
    };
    private List<String> TargetKeyID;
    private boolean CreateOrEdit = true;//创建true
    private String CurrentColor;
    private View CurrentView;
    private EditText CurrentEditBox;
    private int OffsetX, OffsetY;
    private int TouchPointX, TouchPointy;
    private int MarginTop, MarginDown, MarginLeft, MarginRight;
    private long PressDuration;
    private final Handler PressTimer = new Handler();
    private H2CO3CustomButton CurrentKeyPress;
    private EditText alert_h2co3custom_main_edit_key4;
    private boolean vi = true;
    private float downX, downY;

    public static void setWindowSize(int g, int k) {
        scrennWidth = k;
        screenHeight = g;
    }

    public String getStoragePath() {
        return CustomButtonStoragePath;
    }

    public void setStoragePath(String CustomButtonStoragePath) {
        this.CustomButtonStoragePath = CustomButtonStoragePath;
    }

    public void InitCustomButton(Context context, ViewGroup Container, String CustomButtonStoragePath) {
        if (!isInit) {
            this.context = context;
            this.Container = Container;
            setWindowSize(Container.getHeight(), Container.getWidth());
            KeyConverters = new Translation(KeyEvent.KEYMAP_TO_X);
            setStoragePath(CustomButtonStoragePath);
            Deserialization();
            AddButtonToContainer();
            isInit = true;
            TargetKeyID = new ArrayList<>();
        }


    }

    public void setContainerCallBack(ContainerCallBack ContainerCallBack) {
        this.ContainerCallBack = ContainerCallBack;
    }

    public void setCustomButtonCallback(CustomButtonCallback CustomButtonCallback) {
        this.CustomButtonCallback = CustomButtonCallback;
    }

    public void setKeyConversionType(int Type) {
        KeyConverters.setMode(Type);
        Log.d("键值转换器模式改变", Type + "");
    }

    @SuppressLint("ClickableViewAccessibility")
    private void Deserialization() {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.registerTypeAdapter(H2CO3CustomButton.class, new H2CO3Deserializer(context));
        mGson = builder.setPrettyPrinting().create();
        CustomButtonGroup = new ArrayMap<>();
        try {
            FileReader reader = new FileReader(new File(CustomButtonStoragePath, "H2CO3KeyBoard.json"));
            BufferedReader bfr = new BufferedReader(reader);
            String tmp;
            StringBuilder result = new StringBuilder();
            while ((tmp = bfr.readLine()) != null) {
                result.append(tmp);
            }
            bfr.close();
            reader.close();
            JSONArray mJSONArray = new JSONArray(result.toString());
            for (int i = 0; i < mJSONArray.length(); i++) {
                H2CO3CustomButton mH2CO3CustomButton = mGson.fromJson(mJSONArray.get(i).toString(), H2CO3CustomButton.class);
                CustomButtonGroup.put(mH2CO3CustomButton.getID(), mH2CO3CustomButton);
                mH2CO3CustomButton.setOnTouchListener(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void AddButtonToContainer() {
        if (Container != null && CustomButtonGroup != null) {
            Set<String> IDGroup = CustomButtonGroup.keySet();
            for (String ID : IDGroup) {
                Container.addView(CustomButtonGroup.get(ID));
                Objects.requireNonNull(CustomButtonGroup.get(ID)).InitCustomButton();
            }
            for (String ID : IDGroup) {
                try {
                    if (Objects.requireNonNull(CustomButtonGroup.get(ID)).getButtonKeyType().equals("显隐控制按键")) {
                        for (String ID_ : Objects.requireNonNull(CustomButtonGroup.get(ID)).getTargetKeyID()) {
                            getButton(ID_).setVisibility(View.GONE);
                        }

                        Objects.requireNonNull(CustomButtonGroup.get(ID)).setTextColor(Color.RED);
                    }
                } catch (Exception ignored) {

                }

            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void CreateButton() {
        try {
            JSONObject json = new JSONObject();
            json.put("String", getText(alert_h2co3custom_main_edit_name));
            json.put("ButtonSize", new JSONArray().put(getText(alert_h2co3custom_main_edit_height)).put(getText(alert_h2co3custom_main_edit_width)));
            json.put("ButtonTextSize", getText(alert_h2co3custom_main_edit_textsize));
            json.put("ButtonTextColor", getText(alert_h2co3custom_main_edit_textcolor));
            json.put("BorderColor", getText(alert_h2co3custom_main_edit_strokecolor));
            json.put("CornerRadius", getText(alert_h2co3custom_main_edit_round));
            json.put("ButtonLocation", new JSONArray().put(0.5).put(0.5));
            json.put("ID", String.valueOf(System.currentTimeMillis()));
            json.put("ButtonHideByDefault", true);
            if (alert_h2co3custom_main_radio_normalbtn.isChecked()) {
                json.put("ButtonKeyType", "普通按键");
            } else if (alert_h2co3custom_main_radio_cmdbtn.isChecked()) {
                json.put("ButtonKeyType", "命令按键");
            } else if (alert_h2co3custom_main_radio_vsbbtn.isChecked()) {
                json.put("ButtonKeyType", "显隐控制按键");
            }
            json.put("ButtonAutoHold", alert_h2co3custom_main_check_autokeep.isChecked());
            json.put("ButtonControlsMousePointer", alert_h2co3custom_main_check_mousectrl.isChecked());
            json.put("ButtonHideByDefault", true);
            json.put("command", getText(alert_h2co3custom_main_edit_cmd));
            JSONArray mJ = new JSONArray();
            if (TargetKeyID.size() > 0) {
                for (String s : TargetKeyID) {
                    mJ.put(s);
                }
            }
            json.put("TargetKeyID", mJ);
            json.put("ButtonKeyValueGroups", new JSONArray().put(getText(alert_h2co3custom_main_edit_key1)).put(getText(alert_h2co3custom_main_edit_key2)).put(getText(alert_h2co3custom_main_edit_key3)).put(getText(alert_h2co3custom_main_edit_key4)));
            H2CO3CustomButton tmp = mGson.fromJson(json.toString(), H2CO3CustomButton.class);
            CustomButtonGroup.put(tmp.getID(), tmp);
            Container.addView(tmp);
            tmp.InitCustomButton();
            tmp.setOnTouchListener(this);
            CustomButtonDialog.dismiss();
            if (tmp.getButtonKeyType().equals("显隐控制按键")) {
                for (String ID : tmp.getTargetKeyID()) {
                    H2CO3CustomButton b = getButton(ID);
                    b.setTextColor(Color.parseColor(b.getButtonTextColor()));
                }
            }
        } catch (JSONException ignored) {

        }

    }

    public void HideCustomButton(View cross) {
        Set<String> IDGroup = CustomButtonGroup.keySet();
        if (vi) {
            vi = false;
            for (String ID : IDGroup) {
                H2CO3CustomButton b = CustomButtonGroup.get(ID);
                assert b != null;
                b.setVisibility(View.INVISIBLE);
                cross.setVisibility(View.INVISIBLE);
            }
        } else {
            vi = true;
            for (String ID : IDGroup) {
                H2CO3CustomButton b = CustomButtonGroup.get(ID);
                assert b != null;
                b.setVisibility(View.VISIBLE);
                cross.setVisibility(View.VISIBLE);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void EditCustomButton() {
        CustomButtonGroup.remove(CurrentKeyPress.getID());
        try {
            JSONObject json = new JSONObject();
            json.put("String", getText(alert_h2co3custom_main_edit_name));
            json.put("ButtonSize", new JSONArray().put(getText(alert_h2co3custom_main_edit_height)).put(getText(alert_h2co3custom_main_edit_width)));
            json.put("ButtonTextSize", getText(alert_h2co3custom_main_edit_textsize));
            json.put("ButtonTextColor", getText(alert_h2co3custom_main_edit_textcolor));
            json.put("BorderColor", getText(alert_h2co3custom_main_edit_strokecolor));
            json.put("CornerRadius", getText(alert_h2co3custom_main_edit_round));
            json.put("ButtonLocation", new JSONArray().put(CurrentKeyPress.getButtonLocation().get(0)).put(CurrentKeyPress.getButtonLocation().get(1)));
            json.put("ID", String.valueOf(System.currentTimeMillis()));
            json.put("ButtonHideByDefault", true);
            if (alert_h2co3custom_main_radio_normalbtn.isChecked()) {
                json.put("ButtonKeyType", "普通按键");
            } else if (alert_h2co3custom_main_radio_cmdbtn.isChecked()) {
                json.put("ButtonKeyType", "命令按键");
            } else if (alert_h2co3custom_main_radio_vsbbtn.isChecked()) {
                json.put("ButtonKeyType", "显隐控制按键");
            }
            json.put("ButtonAutoHold", alert_h2co3custom_main_check_autokeep.isChecked());
            json.put("ButtonControlsMousePointer", alert_h2co3custom_main_check_mousectrl.isChecked());
            json.put("ButtonHideByDefault", true);
            json.put("command", getText(alert_h2co3custom_main_edit_cmd));
            JSONArray mJ = new JSONArray();
            if (TargetKeyID.size() > 0) {
                for (String s : TargetKeyID) {
                    mJ.put(s);
                }
            }
            json.put("TargetKeyID", mJ);
            json.put("ButtonKeyValueGroups", new JSONArray().put(getText(alert_h2co3custom_main_edit_key1)).put(getText(alert_h2co3custom_main_edit_key2)).put(getText(alert_h2co3custom_main_edit_key3)).put(getText(alert_h2co3custom_main_edit_key4)));
            H2CO3CustomButton tmp = mGson.fromJson(json.toString(), H2CO3CustomButton.class);
            CustomButtonGroup.put(tmp.getID(), tmp);
            Container.addView(tmp);
            tmp.InitCustomButton();
            tmp.setOnTouchListener(this);
            CustomButtonDialog.dismiss();
            Container.removeView(CurrentKeyPress);
            if (tmp.getButtonKeyType().equals("显隐控制按键")) {
                for (String ID : tmp.getTargetKeyID()) {
                    H2CO3CustomButton b = getButton(ID);
                    b.setTextColor(Color.parseColor(b.getButtonTextColor()));
                }
            }
        } catch (JSONException ignored) {

        }
    }

    public void RemoveCustomButton() {
        Set<String> IDGroup = CustomButtonGroup.keySet();
        for (String ID : IDGroup) {
            Container.removeView(CustomButtonGroup.get(ID));
        }
        CustomButtonGroup.clear();
    }

    public void SaveCustomButton() {
        try {
            FileOutputStream out = new FileOutputStream(new File(CustomButtonStoragePath, "H2CO3KeyBoard.json"));
            int i = 1;
            StringBuilder builder = new StringBuilder();
            builder.append("[\n");
            Set<String> IDGroup = CustomButtonGroup.keySet();
            for (String ID : IDGroup) {
                if (CustomButtonGroup.size() > 1 && i > 1) {
                    builder.append(",");
                }
                builder.append(mGson.toJson(CustomButtonGroup.get(ID), H2CO3CustomButton.class));
                builder.append("\n");
                i++;
            }
            builder.append("\n]");
            out.write(builder.toString().getBytes(StandardCharsets.UTF_8));
            out.flush();
            out.close();
            System.out.println(builder);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String getText(EditText edit) {
        return edit.getText().toString();
    }

    public void isEditCustomButton() {
        if (isEditCustomButton) {
            isEditCustomButton = false;
            SaveCustomButton();
            Set<String> AllButtonID = CustomButtonGroup.keySet();
            for (String ID : AllButtonID) {
                H2CO3CustomButton button = CustomButtonGroup.get(ID);
                assert button != null;
                if (button.getButtonKeyType().equals("显隐控制按键")) {
                    for (String ID2 : button.getTargetKeyID()) {
                        H2CO3CustomButton b = getButton(ID2);
                        if (b != null) {
                            b.setVisibility(View.GONE);
                        }
                    }
                }
            }
            Toast.makeText(context, "已退出自定义模式", Toast.LENGTH_SHORT).show();
        } else {
            isEditCustomButton = true;
            Set<String> AllButtonID = CustomButtonGroup.keySet();
            for (String ID : AllButtonID) {
                H2CO3CustomButton button = CustomButtonGroup.get(ID);
                assert button != null;
                if (button.getButtonKeyType().equals("显隐控制按键")) {
                    for (String ID2 : button.getTargetKeyID()) {
                        H2CO3CustomButton b = getButton(ID2);
                        if (b != null) {
                            b.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
            Toast.makeText(context, "已进入自定义模式", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("InflateParams")
    public void CustomButtonDialog(boolean CreateOrEdit) {
        if (!isEditCustomButton) {
            return;
        }
        this.CreateOrEdit = CreateOrEdit;
        if (alert_h2co3custom_main_edit_name == null) {
            CustomButtonMenuLayoutContainer = LayoutInflater.from(context).inflate(R.layout.alert_h2co3custom_main, null);
            initCustomButtonDialog();
            CustomButtonDialog = new MaterialAlertDialogBuilder(context)
                    .setView(CustomButtonMenuLayoutContainer)
                    .create();
            CustomButtonDialog.setCancelable(false);
            CustomButtonDialog.setCanceledOnTouchOutside(false);
        }
        CustomButtonDialog.show();
    }

    private void ColorSelect(View vi, EditText edit) {
        CurrentView = vi;
        CurrentEditBox = edit;
        if (ColorSelectDialog == null) {
            View v = LayoutInflater.from(context).inflate(R.layout.dialog_colorpicker, null);
            ColorPicker colorpicker = v.findViewById(R.id.picker);
            OpacityBar opacityBar = v.findViewById(R.id.opacitybar);
            SVBar svbar = v.findViewById(R.id.svbar);
            colorpicker.addOpacityBar(opacityBar);
            colorpicker.addSVBar(svbar);
            colorpicker.setShowOldCenterColor(false);

            CurrentColor = "#" + Integer.toHexString(colorpicker.getColor());
            colorpicker.setOnColorChangedListener(p1 -> {
                CurrentColor = Integer.toHexString(p1).length() < 8 ? "10ffffff" : Integer.toHexString(p1);
                Log.e("CurrentColor", CurrentColor);
            });
            ColorSelectDialog = new MaterialAlertDialogBuilder(context)
                    .setTitle("ColorSelect")//提示框标题
                    .setView(v)
                    .setPositiveButton("Ok",//提示框的两个按钮
                            (dialog, which) -> {
                                //事件
                                CurrentEditBox.setText(String.format("#%s", CurrentColor));
                                CurrentView.setBackgroundColor(Color.parseColor("#" + CurrentColor));
                            }).setNegativeButton("取消", (di, p2) -> {
                        // TODO: Implement this method

                    }).create();
        }
        ColorSelectDialog.show();
    }

    private void initCustomButtonDialog() {
        alert_h2co3custom_main_edit_name = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_edit_name);
        alert_h2co3custom_main_tv_height_left = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_tv_height_left);
        alert_h2co3custom_main_edit_height = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_edit_height);
        alert_h2co3custom_main_tv_height_right = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_tv_height_right);
        alert_h2co3custom_main_tv_width_left = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_tv_width_left);
        alert_h2co3custom_main_edit_width = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_edit_width);
        alert_h2co3custom_main_tv_width_right = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_tv_width_right);
        alert_h2co3custom_main_tv_textsize_left = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_tv_textsize_left);
        alert_h2co3custom_main_edit_textsize = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_edit_textsize);
        alert_h2co3custom_main_tv_textsize_right = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_tv_textsize_right);
        alert_h2co3custom_main_tv_round_left = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_tv_round_left);
        alert_h2co3custom_main_edit_round = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_edit_round);
        alert_h2co3custom_main_tv_round_right = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_tv_round_right);
        alert_h2co3custom_main_edit_strokecolor = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_edit_strokecolor);
        alert_h2co3custom_main_edit_textcolor = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_edit_textcolor);
        alert_h2co3custom_main_radio_normalbtn = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_radio_normalbtn);
        alert_h2co3custom_main_radio_cmdbtn = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_radio_cmdbtn);
        alert_h2co3custom_main_radio_vsbbtn = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_radio_vsbbtn);
        alert_h2co3custom_main_layout_normalbtn = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_layout_normalbtn);
        alert_h2co3custom_main_edit_key1 = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_edit_key1);
        alert_h2co3custom_main_edit_key2 = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_edit_key2);
        alert_h2co3custom_main_edit_key3 = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_edit_key3);
        alert_h2co3custom_main_edit_key4 = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_edit_key4);
        alert_h2co3custom_main_check_autokeep = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_check_autokeep);
        alert_h2co3custom_main_check_mousectrl = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_check_mousectrl);
        alert_h2co3custom_main_layout_cmdbtn = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_layout_cmdbtn);
        alert_h2co3custom_main_edit_cmd = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_edit_cmd);
        alert_h2co3custom_main_layout_vsbbtn = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_layout_vsbbtn);
        alert_h2co3custom_main_btn_btnselect = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_btn_btnselect);
        alert_h2co3custom_main_btn_ok = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_btn_ok);
        alert_h2co3custom_main_btn_cancle = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_btn_cancle);
        alert_h2co3custom_main_view_strokecolor = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_view_strokecolor);
        alert_h2co3custom_main_view_textcolor = CustomButtonMenuLayoutContainer.findViewById(R.id.alert_h2co3custom_main_view_textcolor);

        alert_h2co3custom_main_radio_normalbtn.setOnCheckedChangeListener(this);
        alert_h2co3custom_main_radio_vsbbtn.setOnCheckedChangeListener(this);
        alert_h2co3custom_main_radio_cmdbtn.setOnCheckedChangeListener(this);
        alert_h2co3custom_main_tv_height_left.setOnClickListener(this);
        alert_h2co3custom_main_tv_height_right.setOnClickListener(this);
        alert_h2co3custom_main_tv_width_left.setOnClickListener(this);
        alert_h2co3custom_main_tv_width_right.setOnClickListener(this);
        alert_h2co3custom_main_tv_textsize_left.setOnClickListener(this);
        alert_h2co3custom_main_tv_textsize_right.setOnClickListener(this);
        alert_h2co3custom_main_tv_round_left.setOnClickListener(this);
        alert_h2co3custom_main_tv_round_right.setOnClickListener(this);
        alert_h2co3custom_main_view_strokecolor.setOnClickListener(this);
        alert_h2co3custom_main_view_textcolor.setOnClickListener(this);
        alert_h2co3custom_main_btn_btnselect.setOnClickListener(this);

        alert_h2co3custom_main_layout_cmdbtn.setVisibility(View.GONE);
        alert_h2co3custom_main_layout_vsbbtn.setVisibility(View.GONE);

        alert_h2co3custom_main_btn_ok.setOnClickListener(this);
        alert_h2co3custom_main_btn_cancle.setOnClickListener(this);

        alert_h2co3custom_main_edit_key1.setOnClickListener(this);
        alert_h2co3custom_main_edit_key2.setOnClickListener(this);
        alert_h2co3custom_main_edit_key3.setOnClickListener(this);
        alert_h2co3custom_main_edit_key4.setOnClickListener(this);


    }

    private void reSetCustomButtonDialog() {
        alert_h2co3custom_main_edit_name.setText("");
        alert_h2co3custom_main_edit_key1.setText("");
        alert_h2co3custom_main_edit_key2.setText("");
        alert_h2co3custom_main_edit_key3.setText("");
        alert_h2co3custom_main_edit_key4.setText("");
        alert_h2co3custom_main_edit_cmd.setText("");
        alert_h2co3custom_main_radio_normalbtn.setChecked(true);
        alert_h2co3custom_main_view_strokecolor.setBackgroundColor(Color.WHITE);
        alert_h2co3custom_main_view_textcolor.setBackgroundColor(Color.WHITE);
        TargetKeyID.clear();
        EditCustomButtonMode = false;
    }

    @Override
    public void onClick(View v) {
        if (v == alert_h2co3custom_main_tv_height_left) {
            int tmp = Integer.parseInt(getText(alert_h2co3custom_main_edit_height));
            alert_h2co3custom_main_edit_height.setText(String.valueOf(tmp -= 1));
        } else if (v == alert_h2co3custom_main_tv_height_right) {
            int tmp = Integer.parseInt(getText(alert_h2co3custom_main_edit_height));
            alert_h2co3custom_main_edit_height.setText(String.valueOf(tmp += 1));
        } else if (v == alert_h2co3custom_main_tv_width_left) {
            int tmp = Integer.parseInt(getText(alert_h2co3custom_main_edit_width));
            alert_h2co3custom_main_edit_width.setText(String.valueOf(tmp -= 1));
        } else if (v == alert_h2co3custom_main_tv_width_right) {
            int tmp = Integer.parseInt(getText(alert_h2co3custom_main_edit_width));
            alert_h2co3custom_main_edit_width.setText(String.valueOf(tmp += 1));
        } else if (v == alert_h2co3custom_main_tv_textsize_left) {
            int tmp = Integer.parseInt(getText(alert_h2co3custom_main_edit_textsize));
            alert_h2co3custom_main_edit_textsize.setText(String.valueOf(tmp -= 1));
        } else if (v == alert_h2co3custom_main_tv_textsize_right) {
            int tmp = Integer.parseInt(getText(alert_h2co3custom_main_edit_textsize));
            alert_h2co3custom_main_edit_textsize.setText(String.valueOf(tmp += 1));
        } else if (v == alert_h2co3custom_main_tv_round_left) {
            int tmp = Integer.parseInt(getText(alert_h2co3custom_main_edit_round));
            alert_h2co3custom_main_edit_textsize.setText(String.valueOf(tmp -= 1));
        } else if (v == alert_h2co3custom_main_tv_round_right) {
            int tmp = Integer.parseInt(getText(alert_h2co3custom_main_edit_round));
            alert_h2co3custom_main_edit_textsize.setText(String.valueOf(tmp += 1));
        } else if (v == alert_h2co3custom_main_btn_ok) {
            if (!alert_h2co3custom_main_edit_name.getText().toString().trim().equals("")) {
                if (CreateOrEdit) {
                    CreateButton();
                } else {
                    EditCustomButton();
                }
            } else {
                Toast.makeText(context, "属性不能为空。", Toast.LENGTH_LONG).show();
            }
            reSetCustomButtonDialog();
        } else if (v == alert_h2co3custom_main_btn_cancle) {
            CustomButtonDialog.dismiss();
            reSetCustomButtonDialog();
        } else if (v == alert_h2co3custom_main_view_strokecolor) {
            ColorSelect(v, alert_h2co3custom_main_edit_strokecolor);
        } else if (v == alert_h2co3custom_main_view_textcolor) {
            ColorSelect(v, alert_h2co3custom_main_edit_textcolor);
        } else if (v == alert_h2co3custom_main_btn_btnselect) {
            CustomButtonDialog.dismiss();
            SelectCustomButtonMode = true;
            try {
                if (CurrentKeyPress.getButtonKeyType().equals("显隐控制按键") && CurrentKeyPress.isButtonHideByDefault()) {
                    for (String ID : CurrentKeyPress.getTargetKeyID()) {
                        H2CO3CustomButton b = getButton(ID);
                        b.setTextColor(Color.RED);
                        b.setVisibility(View.VISIBLE);
                    }
                }
            } catch (Exception ignored) {

            }
            Toast.makeText(context, "请点击需要隐藏的按键。点击黑色区域退出结束按键选择。", Toast.LENGTH_SHORT).show();
        } else if (v == alert_h2co3custom_main_edit_key1 || v == alert_h2co3custom_main_edit_key2 || v == alert_h2co3custom_main_edit_key3 || v == alert_h2co3custom_main_edit_key4) {
            EditText tmpEdit = (EditText) v;
            if (KeySelectDialog == null) {
                KeySelectDialog = new H2CO3DialogSelectKey(context);
            }
            KeySelectDialog.setEdit(tmpEdit);
            KeySelectDialog.show();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton v, boolean isChecked) {
        if (isChecked) {
            if (alert_h2co3custom_main_radio_normalbtn == v) {
                alert_h2co3custom_main_layout_normalbtn.setVisibility(View.VISIBLE);
                alert_h2co3custom_main_layout_cmdbtn.setVisibility(View.GONE);
                alert_h2co3custom_main_layout_vsbbtn.setVisibility(View.GONE);
            } else if (alert_h2co3custom_main_radio_cmdbtn == v) {
                alert_h2co3custom_main_layout_normalbtn.setVisibility(View.GONE);
                alert_h2co3custom_main_layout_cmdbtn.setVisibility(View.VISIBLE);
                alert_h2co3custom_main_layout_vsbbtn.setVisibility(View.GONE);
            } else if (alert_h2co3custom_main_radio_vsbbtn == v) {
                alert_h2co3custom_main_layout_normalbtn.setVisibility(View.GONE);
                alert_h2co3custom_main_layout_cmdbtn.setVisibility(View.GONE);
                alert_h2co3custom_main_layout_vsbbtn.setVisibility(View.VISIBLE);
            }
        }

    }

    public boolean getSelectCustomButtonMode() {
        return SelectCustomButtonMode;
    }

    public void setSelectCustomButtonMode(boolean a) {
        this.SelectCustomButtonMode = a;
    }

    public boolean getEditCustomButtonMode() {
        return EditCustomButtonMode;
    }

    public void setEditCustomButtonMode(boolean a) {
        this.EditCustomButtonMode = a;
    }

    public H2CO3CustomButton getCurrentKeyPress() {
        return CurrentKeyPress;
    }

    public boolean getSwitchState() {
        return isEditCustomButton;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO: Implement this method
        if (!SelectCustomButtonMode) {
            CurrentKeyPress = (H2CO3CustomButton) v;
        }
        if (isEditCustomButton && !SelectCustomButtonMode) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                TouchPointX = (int) event.getX();
                TouchPointy = (int) event.getY();
                PressDuration = System.currentTimeMillis();
                PressTimer.postDelayed(PressTimeTask, 500);
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                OffsetX = (int) event.getX() - TouchPointX;
                OffsetY = (int) event.getY() - TouchPointy;
                MarginLeft = v.getLeft() + OffsetX;
                MarginTop = v.getTop() + OffsetY;
                MarginRight = v.getRight() + OffsetX;
                MarginDown = v.getBottom() + OffsetY;
                v.layout(MarginLeft, MarginTop, MarginRight, MarginDown);
                CurrentKeyPress.Refresh();
                if (Math.abs(event.getX() - TouchPointX) >= 3 || Math.abs(event.getY() - TouchPointy) >= 3) {
                    PressTimer.removeCallbacks(PressTimeTask);
                }

            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if ((System.currentTimeMillis() - PressDuration) < 100) {
                    CustomButtonDialog(false);
                    EditCustomButtonMode = true;
                    alert_h2co3custom_main_edit_name.setText(CurrentKeyPress.getString());
                    alert_h2co3custom_main_edit_cmd.setText(CurrentKeyPress.getCommand());
                    alert_h2co3custom_main_edit_height.setText(String.format("%d", CurrentKeyPress.getButtonSize().get(0)));
                    alert_h2co3custom_main_edit_width.setText(String.format("%d", CurrentKeyPress.getButtonSize().get(1)));
                    alert_h2co3custom_main_edit_textsize.setText(String.format("%d", CurrentKeyPress.getButtonTextSize()));
                    alert_h2co3custom_main_edit_textcolor.setText(CurrentKeyPress.getButtonTextColor());
                    alert_h2co3custom_main_edit_strokecolor.setText(CurrentKeyPress.getBorderColor());
                    alert_h2co3custom_main_edit_round.setText(String.format("%d", CurrentKeyPress.getCornerRadius()));
                    alert_h2co3custom_main_check_autokeep.setChecked(CurrentKeyPress.isButtonAutoHold());
                    alert_h2co3custom_main_check_mousectrl.setChecked(CurrentKeyPress.isButtonControlsMousePointer());
                    alert_h2co3custom_main_edit_key1.setText(CurrentKeyPress.getButtonKeyValueGroups().get(0));
                    alert_h2co3custom_main_edit_key2.setText(CurrentKeyPress.getButtonKeyValueGroups().get(1));
                    alert_h2co3custom_main_edit_key3.setText(CurrentKeyPress.getButtonKeyValueGroups().get(2));
                    alert_h2co3custom_main_edit_key4.setText(CurrentKeyPress.getButtonKeyValueGroups().get(3));
                    TargetKeyID.clear();
                    TargetKeyID.addAll(CurrentKeyPress.getTargetKeyID());
                    if (CurrentKeyPress.getButtonKeyType().contentEquals(alert_h2co3custom_main_radio_normalbtn.getHint())) {
                        alert_h2co3custom_main_radio_normalbtn.setChecked(true);
                    } else if (CurrentKeyPress.getButtonKeyType().contentEquals(alert_h2co3custom_main_radio_cmdbtn.getHint())) {
                        alert_h2co3custom_main_radio_cmdbtn.setChecked(true);
                    } else if (CurrentKeyPress.getButtonKeyType().contentEquals(alert_h2co3custom_main_radio_vsbbtn.getHint())) {
                        alert_h2co3custom_main_radio_vsbbtn.setChecked(true);
                    }
                    alert_h2co3custom_main_view_strokecolor.setBackgroundColor(Color.parseColor(CurrentKeyPress.getBorderColor()));
                    alert_h2co3custom_main_view_textcolor.setBackgroundColor(Color.parseColor(CurrentKeyPress.getButtonTextColor()));
//					if(CurrentKeyPress.getButtonKeyType().equals("显隐控制按键")&&CurrentKeyPress.isButtonHideByDefault()){
//						for(String ID:CurrentKeyPress.getTargetKeyID()){
//							H2CO3CustomButton b=getButton(ID);
//							b.setTextColor(Color.RED);
//							b.setVisibility(View.VISIBLE);
//						}
//					}
                }
                PressTimer.removeCallbacks(PressTimeTask);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) CurrentKeyPress.getLayoutParams();
                params.setMargins(MarginLeft, MarginTop, MarginRight, MarginDown);
                CurrentKeyPress.setLayoutParams(params);
            }
        } else if (SelectCustomButtonMode) {
            H2CO3CustomButton SelectButton = (H2CO3CustomButton) v;
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (TargetKeyID.contains(SelectButton.getID())) {
                    TargetKeyID.remove(SelectButton.getID());
                    SelectButton.setTextColor(Color.parseColor(SelectButton.getButtonTextColor()));
                    Toast.makeText(context, "已取消选择按键:" + SelectButton.getString(), Toast.LENGTH_SHORT).show();
                } else {
                    TargetKeyID.add(SelectButton.getID());
                    SelectButton.setTextColor(Color.RED);
                    Toast.makeText(context, "已选择按键:" + SelectButton.getString(), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            if (event.getHistorySize() > 1) {
                downX = (int) event.getHistoricalX(0);
                downY = (int) event.getHistoricalY(0);
            } else {
                downX = (int) event.getX();
                downY = (int) event.getY();
            }
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (CurrentKeyPress.getButtonKeyType().equals("普通按键")) {
                    for (String key : CurrentKeyPress.getButtonKeyValueGroups()) {
                        if (CurrentKeyPress.isButtonControlsMousePointer()) {
                            CustomButtonCallback.Pressed();
                        }
                        if (!key.equals("")) {
                            int Key = KeyConverters.trans(key);
                            if (key.contains("MOUSE")) {
                                CustomButtonCallback.MouseCallback(Key, true);
                                Log.d("鼠标", key);
                            } else {
                                CustomButtonCallback.KeyReceived(Key, true);
                            }
                        }
                    }
                }

                TouchPointX = (int) event.getX();
                TouchPointy = (int) event.getY();
                PressDuration = System.currentTimeMillis();
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                if (CurrentKeyPress.isButtonControlsMousePointer()) {
                    float xx = event.getX() - downX;
                    float yy = event.getY() - downY;
                    CustomButtonCallback.ControlsMousePointerMovement((int) xx, (int) yy);
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (CurrentKeyPress.getButtonKeyType().equals("普通按键")) {
                    if (CurrentKeyPress.isButtonControlsMousePointer()) {
                        CustomButtonCallback.unPressed();
                    }
                    if (CurrentKeyPress.isButtonAutoHold()) {
                        if (!CurrentKeyPress.isisAutoHold()) {
                            CurrentKeyPress.setisAutoHold(true);
                        } else {
                            CurrentKeyPress.setisAutoHold(false);
                            for (String key : CurrentKeyPress.getButtonKeyValueGroups()) {
                                if (!key.equals("")) {
                                    int Key = KeyConverters.trans(key);
                                    if (key.contains("MOUSE")) {
                                        CustomButtonCallback.MouseCallback(Key, false);
                                    } else {
                                        CustomButtonCallback.KeyReceived(Key, false);
                                    }
                                }
                            }
                        }
                    } else {
                        for (String key : CurrentKeyPress.getButtonKeyValueGroups()) {
                            if (!key.equals("")) {
                                int Key = KeyConverters.trans(key);
                                if (key.contains("MOUSE")) {
                                    CustomButtonCallback.MouseCallback(Key, false);
                                } else {
                                    CustomButtonCallback.KeyReceived(Key, false);
                                }
                            }
                        }
                    }

                }

                if ((System.currentTimeMillis() - PressDuration) < 100 && Math.abs(event.getX() - TouchPointX) <= 20 && Math.abs(event.getY() - TouchPointy) <= 20) {
                    if (CurrentKeyPress.getButtonKeyType().equals("命令按键")) {
                        if (CustomButtonCallback != null) {
                            CustomButtonCallback.CommandReceived(CurrentKeyPress.getCommand());
                        }

                    } else if (CurrentKeyPress.getButtonKeyType().equals("显隐控制按键")) {
                        try {
                            if (getButton(CurrentKeyPress.getTargetKeyID().get(0)).getVisibility() == View.VISIBLE) {
                                for (String ID : CurrentKeyPress.getTargetKeyID()) {
                                    getButton(ID).setVisibility(View.GONE);
                                }
                                CurrentKeyPress.setTextColor(Color.RED);
                            } else {
                                for (String ID : CurrentKeyPress.getTargetKeyID()) {
                                    getButton(ID).setVisibility(View.VISIBLE);
                                }
                                CurrentKeyPress.setTextColor(Color.parseColor(CurrentKeyPress.getButtonTextColor()));
                            }
                        } catch (Exception ignored) {

                        }
                    }
                }
            }

        }
        return false;
    }

    public H2CO3CustomButton getButton(String ID) {
        try {
            if (CustomButtonGroup.size() != 0) {
                return CustomButtonGroup.get(ID);
            }
        } catch (Exception ignored) {

        }
        Toast.makeText(context, "未找到相应按键", Toast.LENGTH_SHORT).show();
        return new H2CO3CustomButton(context);
    }

    public int getPointerX() {
        return PointerX;
    }

    public void setPointerX(int PointerX) {
        this.PointerX = PointerX;
    }

    public int getPointerY() {
        return PointerY;
    }

    public void setPointerY(int PointerY) {
        this.PointerY = PointerY;
    }

    public interface ContainerCallBack {
        /*void Pressed(MotionEvent 事件);
        void Move(MotionEvent 事件);
        void unPressed(MotionEvent 事件);*/
        void TouchListener();

        void LongTouchListener();

        void ControlsMousePointerMovement(int x, int y);

        void onTouch(MotionEvent event);
    }

    public interface CustomButtonCallback {
        void CommandReceived(String command);

        void KeyReceived(int Key, boolean Pressed);

        void ControlsMousePointerMovement(int x, int y);

        void MouseCallback(int Key, boolean Pressed);

        void Pressed();

        void unPressed();
    }

    public interface RadialMenuCallback {

    }
}

class H2CO3Deserializer implements JsonDeserializer<H2CO3CustomButton> {
    Context mContext;

    public H2CO3Deserializer(Context context) {
        this.mContext = context;
    }

    @Override
    public H2CO3CustomButton deserialize(JsonElement json, Type typeOfT,
                                         JsonDeserializationContext context) throws JsonParseException {
        H2CO3CustomButton target = new H2CO3CustomButton(mContext);
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        Gson mGson = builder.create();
        H2CO3CustomButton tmp = mGson.fromJson(json, H2CO3CustomButton.class);

        return MergeObjects(tmp, target);
    }

    private H2CO3CustomButton MergeObjects(Object sourceBean, Object targetBean) {
        Class<?> sourceBeanClass = sourceBean.getClass();
        Class<?> targetBeanClass = targetBean.getClass();

        Field[] sourceFields = sourceBeanClass.getDeclaredFields();
        Field[] targetFields = sourceBeanClass.getDeclaredFields();
        for (int i = 0; i < sourceFields.length; i++) {
            Field sourceField = sourceFields[i];
            Field targetField = targetFields[i];
            sourceField.setAccessible(true);
            targetField.setAccessible(true);
            try {
                if (!(sourceField.get(sourceBean) == null)) {
                    targetField.set(targetBean, sourceField.get(sourceBean));
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return (H2CO3CustomButton) targetBean;
    }

}
