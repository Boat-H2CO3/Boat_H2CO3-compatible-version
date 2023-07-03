package org.koishi.launcher.h2co3.control;
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
import android.view.View;
import android.view.ViewGroup;

public class H2CO3CustomUtils {
    public static void setViewSize(Context context, View view, String height, String width) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = dip2px(context, Integer.parseInt(width));
        params.height = dip2px(context, Integer.parseInt(height));
        view.setLayoutParams(params);
    }

    public static void setViewSize(Context context, View view, int height, int width) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = dip2px(context, (width));
        params.height = dip2px(context, (height));
        view.setLayoutParams(params);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int percentTopx(double percent, int screen) {
        return (int) (((double) screen) * percent);
    }

    public static double pxToPercent(double px, int screen) {
        return px / screen;
    }


}
