package com.cainiaohh.module.h2co3customkeyboard.gamecontroller.input.screen.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.cainiaohh.module.h2co3customkeyboard.R;

public class BaseButton extends AppCompatButton {

    public BaseButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BaseButton);
        setButtonName(array.getString(R.styleable.BaseButton_button_name));
        array.recycle();
    }

    private String button_name;

    public String getButtonName() {
        return button_name;
    }

    public void setButtonName(String buttonName) {
        button_name = buttonName;
    }
}
