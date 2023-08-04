package org.koishi.launcher.h2co3.launcher.control.view;
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
import android.content.res.TypedArray;
import android.util.AttributeSet;

import cosine.boat.R;

public class H2CO3SelectButton extends androidx.appcompat.widget.AppCompatButton {

    private String key;

    public H2CO3SelectButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        @SuppressLint("CustomViewStyleable") TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SelectButton);
        pushEventKey(array.getString(R.styleable.SelectButton_key));
        array.recycle();
    }

    public String getKey() {
        return key;
    }

    public void pushEventKey(String key) {
        this.key = key;
    }


}
