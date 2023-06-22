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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.koishi.h2co3.mclauncher.gamecontroller.definitions.map.MouseMap;

import cosine.boat.R;

public class H2CO3DialogSelectKey extends MaterialAlertDialogBuilder implements OnClickListener {
    private final Context context;
    private LinearLayout mMainLayout;
    private LinearLayout mKeyboardLayout;
    private AlertDialog mDialog;
    private Button MouseLeft;
    private Button MouseRight;
    private Button MouseCenter;
    private Button RollerUp;
    private Button RollerDown;
    private Button Ok;
    private Button Cancel;
    private TextView Key;
    private EditText edit;
    private OnClickListener ButtonListener;

    public H2CO3DialogSelectKey(Context context) {
        super(context, R.style.FullDialog);
        this.context = context;
        init();
    }

    public void init() {
        mMainLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_selectkey, null);
        mKeyboardLayout = mMainLayout.findViewById(R.id.selecter_keyboard);
        for (int i = 0; i < mKeyboardLayout.getChildCount(); i++) {
            if (mKeyboardLayout.getChildAt(i) instanceof LinearLayout) {
                LinearLayout t = (LinearLayout) mKeyboardLayout.getChildAt(i);
                for (int j = 0; j < t.getChildCount(); j++) {
                    if (t.getChildAt(j) instanceof H2CO3SelectButton) {
                        t.getChildAt(j).setOnClickListener(this);
                    }
                }
            }
        }
        setView(mMainLayout);
        ButtonListener = v -> {
            if (v == MouseLeft) {
                Key.setText(MouseMap.MOUSEMAP_BUTTON_LEFT);
            } else if (v == MouseCenter) {
                Key.setText(MouseMap.MOUSEMAP_BUTTON_MIDDLE);
            } else if (v == MouseRight) {
                Key.setText(MouseMap.MOUSEMAP_BUTTON_RIGHT);
            } else if (v == Ok) {
                edit.setText(Key.getText());
                mDialog.dismiss();
            } else if (v == Cancel) {
                mDialog.dismiss();
            } else if (v == RollerUp) {
                Key.setText(MouseMap.MOUSEMAP_WHEEL_UP);
            } else if (v == RollerDown) {
                Key.setText(MouseMap.MOUSEMAP_WHEEL_DOWN);
            }
        };
        MouseLeft = mMainLayout.findViewById(R.id.dialog_selectkey_mouse_left);
        MouseRight = mMainLayout.findViewById(R.id.dialog_selectkey_mouse_right);
        MouseCenter = mMainLayout.findViewById(R.id.dialog_selectkey_mouse_center);
        RollerUp = mMainLayout.findViewById(R.id.dialog_selectkey_mouse_wheel_up);
        RollerDown = mMainLayout.findViewById(R.id.dialog_selectkey_mouse_wheel_down);
        Ok = mMainLayout.findViewById(R.id.dialog_selectkey_btn_ok);
        Cancel = mMainLayout.findViewById(R.id.dialog_selectkey_btn_cancel);
        Key = mMainLayout.findViewById(R.id.dialog_selectkey_text_key);
        MouseLeft.setOnClickListener(ButtonListener);
        MouseCenter.setOnClickListener(ButtonListener);
        MouseRight.setOnClickListener(ButtonListener);
        RollerUp.setOnClickListener(ButtonListener);
        RollerDown.setOnClickListener(ButtonListener);
        Ok.setOnClickListener(ButtonListener);
        Cancel.setOnClickListener(ButtonListener);
        mDialog = super.create();
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
    }

    public void setEdit(EditText edit) {
        this.edit = edit;
    }

    @Override
    public AlertDialog show() {
        mDialog.show();
        return null;
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    @Override
    public void onClick(View p1) {
        H2CO3SelectButton mBtn = (H2CO3SelectButton) p1;
        Key.setText(mBtn.getKey());
    }
}
