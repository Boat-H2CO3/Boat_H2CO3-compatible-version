package com.cainiaohh.module.h2co3customkeyboard.gamecontroller.ckb.achieve;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.cainiaohh.module.h2co3customkeyboard.R;
import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.ckb.button.GameButton;
import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.ckb.support.CallCustomizeKeyboard;
import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.ckb.support.GameButtonArray;
import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.ckb.support.GameButtonConverter;
import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.ckb.support.GameButtonRecorder;
import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.ckb.support.KeyboardRecorder;
import com.cainiaohh.module.h2co3customkeyboard.gamecontroller.controller.Controller;
import com.cainiaohh.module.h2co3customkeyboard.utils.DisplayUtils;
import com.cainiaohh.module.h2co3customkeyboard.utils.dialog.DialogUtils;
import com.cainiaohh.module.h2co3customkeyboard.utils.dialog.support.DialogSupports;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;

import cosine.boat.utils.CHTools;

public class CkbManager {

    private final static String TAG = "CkbManager";
    public final static int MAX_KEYBOARD_SIZE = 160;
    public final static int MIN_KEYBOARD_SIZE = 0;
    public final static String LAST_KEYBOARD_LAYOUT_NAME = "tmp";
    public final static int SHOW_BUTTON = 1;
    public final static int HIDE_BUTTON = 2;

    private final Context mContext;
    private final CallCustomizeKeyboard mCall;
    private final Controller mController;
    private boolean hasHide = false;

    private GameButtonArray<GameButton> buttonList;

    private int buttonMode = GameButton.MODE_MOVEABLE_EDITABLE;
    private int displayWidth;
    private int displayHeight;

    public CkbManager(@NonNull Context context, @NonNull CallCustomizeKeyboard call, Controller controller) {
        super();
        this.mContext = context;
        this.mCall = call;
        this.mController = controller;
        init();
    }

    public int[] getDisplaySize() {
        return new int[]{displayWidth, displayHeight};
    }

    private void init() {

        //初始化按键列表
        buttonList = new GameButtonArray<>();

        //初始化显示范围
        displayWidth = DisplayUtils.checkDeviceHasNavigationBar(mContext) ? DisplayUtils.getApplicationWindowSize(mContext)[0] + DisplayUtils.getNavigationBarHeight(mContext) : DisplayUtils.getApplicationWindowSize(mContext)[0];
        displayHeight = DisplayUtils.getApplicationWindowSize(mContext)[1];

        //当Manager初始化的时候自动加载键盘布局
        autoLoadKeyboard();
    }

    public Controller getController() {
        return mController;
    }

    public void addGameButton(GameButton button) {
        if (this.containGameButton(button) || this.buttonList.size() >= MAX_KEYBOARD_SIZE) {
            button.unsetFirstAdded();
        } else {
            if (button == null) {
                button = new GameButton(mContext, mCall, mController, this).setButtonMode(this.buttonMode).setFirstAdded();
                (new GameButtonDialog(mContext, button, this)).show();
            }
            this.buttonList.add(button);
            this.addView(button);
        }
    }

    public boolean containGameButton(GameButton g) {
        return buttonList.contains(g);
    }

    public void removeGameButton(GameButton g) {
        if (this.containGameButton(g)) {
            GameButtonArray<GameButton> gl = new GameButtonArray<>();
            for (GameButton gb : buttonList) {
                if (gb != g) {
                    gl.add(gb);
                }
            }
            this.removeView(g);
            this.buttonList = gl;
        } else {
        }
    }

    private void addView(GameButton g) {

        if (g != null && g.getParent() == null) {
            mCall.addView(g);
        } else {
        }

    }

    private void removeView(GameButton g) {

        if (g != null && g.getParent() != null) {
            ViewGroup vg = (ViewGroup) g.getParent();
            vg.removeView(g);
        } else {
        }

    }

    public int getButtonCounts() {
        return buttonList.size();
    }

    public void setButtonsMode(int mode) {
        if (mode == GameButton.MODE_GAME || mode == GameButton.MODE_MOVEABLE_EDITABLE || mode == GameButton.MODE_PREVIEW) {
            for (GameButton g : buttonList) {
                g.setButtonMode(mode);
            }
            this.buttonMode = mode;
        } else {
        }
    }

    public int getButtonsMode() {
        return this.buttonMode;
    }

    public GameButton[] getGameButtons() {
        GameButton[] views = new GameButton[getButtonCounts()];
        for (int i = 0; i < views.length; i++) {
            views[i] = buttonList.get(i);
        }
        return views;
    }

    public GameButton getGameButton(int index) {
        if (index >= 0 && index < buttonList.size()) {
            return buttonList.get(index);
        } else {
            return null;
        }
    }

    public void setInputMode(boolean mode) {
        for (GameButton gb : buttonList) {
            gb.setGrabbed(mode);
        }
    }

    public void exportKeyboard(String fileName) {
        GameButtonRecorder[] gbrs = new GameButtonRecorder[buttonList.size()];
        for (int a = 0; a < buttonList.size(); a++) {
            GameButtonRecorder gbr = new GameButtonRecorder();
            gbr.recordData(buttonList.get(a));
            gbrs[a] = gbr;
        }
        KeyboardRecorder kr = new KeyboardRecorder();
        kr.setScreenArgs(mContext.getResources().getDisplayMetrics().widthPixels, mContext.getResources().getDisplayMetrics().heightPixels);
        kr.setRecorderDatas(gbrs);
        kr.setVersionCode(KeyboardRecorder.VERSION_THIS);

        outputFile(kr, fileName);
    }

    public static boolean outputFile(KeyboardRecorder kr, String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        StringBuilder jsonString = new StringBuilder(gson.toJson(kr));
        jsonString.insert(0, "/*\n *This file is craeted by MCinaBox\n *Please DON'T edit the file if you don't know how it works.\n*/\n");
        try {
            FileWriter jsonWriter = new FileWriter(CHTools.LAUNCHER_FILE_DIR + "/KeyBoards" + "/" + fileName + ".json");
            BufferedWriter out = new BufferedWriter(jsonWriter);
            out.write(jsonString.toString());
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void autoSaveKeyboard() {
        exportKeyboard(LAST_KEYBOARD_LAYOUT_NAME);
    }

    public void autoLoadKeyboard() {
        loadKeyboard(LAST_KEYBOARD_LAYOUT_NAME + ".json");
    }

    public boolean loadKeyboard(File file) {
        if (!file.exists()) {
            return false;
        }
        KeyboardRecorder kr;
        try {
            InputStream inputStream = Files.newInputStream(file.toPath());
            Reader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            kr = gson.fromJson(reader, KeyboardRecorder.class);
        } catch (Exception e) {
            e.printStackTrace();
            //当失败时尝试通过加载旧版的按键
            DialogUtils.createBothChoicesDialog(mContext, mContext.getString(R.string.title_note), mContext.getString(R.string.tips_try_to_convert_keyboard_layout), mContext.getString(R.string.title_ok), mContext.getString(R.string.title_cancel), new DialogSupports() {
                @Override
                public void runWhenPositive() {
                    super.runWhenPositive();
                    if (new GameButtonConverter(mContext).output(file)) {
                        DialogUtils.createSingleChoiceDialog(mContext, mContext.getString(R.string.title_note), String.format(mContext.getString(R.string.tips_successed_to_convert_keyboard_file), file.getName() + "-new.json"), mContext.getString(R.string.title_ok), null);
                    } else {
                        DialogUtils.createSingleChoiceDialog(mContext, mContext.getString(R.string.title_note), mContext.getString(R.string.tips_failed_to_convert_keyboard_file), mContext.getString(R.string.title_ok), null);
                    }
                }
            });
            return false;
        }
        loadKeyboard(kr);
        return true;
    }

    public boolean loadKeyboard(String fileName) {
        File file = new File(CHTools.LAUNCHER_FILE_DIR + "/KeyBoards" + "/" + fileName);
        return loadKeyboard(file);
    }

    public void loadKeyboard(KeyboardRecorder kr) {
        GameButtonRecorder[] gbr;
        if (kr != null) {
            gbr = kr.getRecorderDatas();
        } else {
            return;
        }

        if (kr.getVersionCode() == KeyboardRecorder.VERSION_UNKNOWN) {
            for (GameButtonRecorder tgbr : gbr) {
                tgbr.keyPos[0] = DisplayUtils.getDpFromPx(mContext, tgbr.keyPos[0]);
                tgbr.keyPos[1] = DisplayUtils.getDpFromPx(mContext, tgbr.keyPos[1]);
            }
        }
        //清除全部按键
        clearKeyboard();
        //添加新的按键
        for (GameButtonRecorder tgbr : gbr) {
            addGameButton(tgbr.recoverData(mContext, mCall, mController, this));
        }
    }

    public void clearKeyboard() {
        for (GameButton gb : buttonList) {
            removeView(gb);
        }
        buttonList = new GameButtonArray<>();
    }

    public void showOrHideGameButtons(int i) {
        // 仅仅只是暂时把GameButton对象从显示层中删除
        // 不要更改按键记录
        switch (i) {
            case SHOW_BUTTON:
                if (hasHide) {
                    for (GameButton gb : buttonList) {
                        this.addView(gb);
                    }
                    hasHide = false;
                }
                break;
            case HIDE_BUTTON:
                if (!hasHide) {
                    for (GameButton gb : buttonList) {
                        this.removeView(gb);
                    }
                    hasHide = true;
                }
                break;
        }

    }


}
