package org.koishi.h2co3.mclauncher.view;
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
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import cosine.boat.R;

public class H2CO3MinecraftBottomBar extends LinearLayout implements OnTouchListener, View.OnClickListener {

    public static final float SCALE_MAX = 3.0f;
    public static final float SCALE_MIN = 0.5f;
    private final Context context;
    private final int bt1 = 1, bt2 = 2, bt3 = 3, bt4 = 4, bt5 = 5, bt6 = 6, bt7 = 7, bt8 = 8, bt9 = 9;
    //最大的缩放比例
    public float mScale;
    private H2CO3Listener mListener;
    private LinearLayout main;
    private Button touchPad;
    private LinearLayout layout;
    private Button btn_1,
            btn_2,
            btn_3;
    private Button btn_4,
            btn_5,
            btn_6;
    private Button btn_7,
            btn_8,
            btn_9;
    private final boolean Custom = false;
    private int TouchX;
    private int TouchY;
    /**
     *
     */
    private int OffsetX;
    private final double oldDist = 0;
    private final double moveDist = 0;
    private TouchInfo mInfo;
    private final int lastPos = 0;

    public H2CO3MinecraftBottomBar(Context context) {
        super(context);
        this.context = context;
        init();
    }
    public H2CO3MinecraftBottomBar(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        init();
    }

    public H2CO3MinecraftBottomBar(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        this.context = context;
        init();
    }

    /**
     * 计算两个点的距离
     *
     * @param event 事件
     * @return 返回数值
     */

    /**
     * 设置放大缩小
     *
     * @param scale 缩放值
     */
    public void setScale(float scale) {
        setScaleX(scale);
        setScaleY(scale);
        mScale = scale;
    }

    public void setListener(H2CO3Listener mListener) {
        this.mListener = mListener;
    }

    private final ItemButton[] itemButtons = new ItemButton[9];

    private Context mContext;
    private LinearLayout itemBar;

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    private void init() {
        main = new LinearLayout(context);
        main.setOrientation(LinearLayout.VERTICAL);
        layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f);
        main.addView(layout, params1);
        itemBar = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.minecraft_bottom_bar, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        this.addView(main, params);
        itemButtons[0] = itemBar.findViewById(R.id.itembutton_1);
        itemButtons[1] = itemBar.findViewById(R.id.itembutton_2);
        itemButtons[2] = itemBar.findViewById(R.id.itembutton_3);
        itemButtons[3] = itemBar.findViewById(R.id.itembutton_4);
        itemButtons[4] = itemBar.findViewById(R.id.itembutton_5);
        itemButtons[5] = itemBar.findViewById(R.id.itembutton_6);
        itemButtons[6] = itemBar.findViewById(R.id.itembutton_7);
        itemButtons[7] = itemBar.findViewById(R.id.itembutton_8);
        itemButtons[8] = itemBar.findViewById(R.id.itembutton_9);

        //设定监听器
        for (View v : itemButtons) {
            v.setOnTouchListener(this);
        }

        //计算并设定物品栏大小
        int height = mContext.getResources().getDisplayMetrics().heightPixels;
        int width = mContext.getResources().getDisplayMetrics().widthPixels;
        int scale = 1;
        while (width / (scale + 1) >= 320 && height / (scale + 1) >= 240) {
            scale++;
        }
        ViewGroup.LayoutParams lp = itemBar.getLayoutParams();
        lp.height = 20 * scale;
        lp.width = 20 * scale * 9;
        itemBar.setLayoutParams(lp);
        touchPad = new Button(context);
        touchPad.setOnTouchListener(this);
        touchPad.setAlpha(0);
        this.addView(touchPad, params);

    }

    private Button createButton(Drawable drawable) {
        Button b = new Button(context);
        b.setBackground(drawable);
        b.setAlpha(0.3f);
        b.setOnClickListener(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f);
        b.setLayoutParams(params);

        return b;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }

    @Override
    public void onClick(View v) {
        if (v == btn_1) {
            btn_2.setBackground(getResources().getDrawable(R.drawable.ic_boat));
        }
    }

    public interface H2CO3Listener {
        void Btn_1();

        void Btn_2();

        void Btn_3();

        void Btn_4();

        void Btn_5();

        void Btn_6();

        void Btn_7();

        void Btn_8();

        void Btn_9();
    }

    private static class TouchInfo {
        private int layoutWidth;
        private int layoutHeight;
        private float fingerX;
        private float fingerY;

        private float downX;
        private float downY;

        public float getDownX() {
            return downX;
        }

        public void setDownX(float downX) {
            this.downX = downX;
        }

        public float getDownY() {
            return downY;
        }

        public void setDownY(float downY) {
            this.downY = downY;
        }

        public int getLayoutWidth() {
            return layoutWidth;
        }

        public void setLayoutWidth(int layoutWidth) {
            this.layoutWidth = layoutWidth;
        }

        public int getLayoutHeight() {
            return layoutHeight;
        }

        public void setLayoutHeight(int layoutHeight) {
            this.layoutHeight = layoutHeight;
        }

        public float getFingerX() {
            return fingerX;
        }

        public void setFingerX(float fingerX) {
            this.fingerX = fingerX;
        }

        public float getFingerY() {
            return fingerY;
        }

        public void setFingerY(float fingerY) {
            this.fingerY = fingerY;
        }
    }
}
