package org.koishi.launcher.h2co3.control.ckb.support;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import org.koishi.launcher.h2co3.R;

public class QwertButton extends androidx.appcompat.widget.AppCompatButton {

    private String button_name;

    public QwertButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        @SuppressLint("CustomViewStyleable") TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BaseButton);
        setButtonName(array.getString(R.styleable.BaseButton_button_name));
        array.recycle();
    }

    public String getButtonName() {
        return button_name;
    }

    public void setButtonName(String buttonName) {
        button_name = buttonName;
    }
}
