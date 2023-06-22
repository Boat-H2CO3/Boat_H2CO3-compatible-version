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

package org.koishi.h2co3.mclauncher.customcontrol;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class H2CO3CustomButton extends AppCompatButton {

    @Expose
    private String ID;

    @Expose
    private String String;

    @Expose
    private String ButtonKeyType;//显隐控制按键 命令按键 普通按键

    @Expose
    private List<String> ButtonKeyValueGroups;

    @Expose
    private int ButtonTextSize;

    @Expose
    private List<Double> ButtonLocation;

    @Expose
    private List<Integer> ButtonSize;

    @Expose
    private String ButtonTextColor;

    @Expose
    private int CornerRadius;

    @Expose
    private String BorderColor;

    @Expose
    private boolean ButtonAutoHold;

    @Expose
    private boolean ButtonHideByDefault;

    @Expose
    private boolean ButtonControlsMousePointer;

    @Expose
    private String Command;

    @Expose
    private List<String> TargetKeyID;

    private boolean isAutoHold = false;

    private final Context context;

    public H2CO3CustomButton(Context context) {
        super(context);
        this.context = context;
    }

    public void InitCustomButton() {
        setText(String);
        setTextSize(ButtonTextSize);
        setTextColor(Color.parseColor(ButtonTextColor));
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(Color.parseColor("#2083b2ff"));
        drawable.setCornerRadius(CornerRadius);
        drawable.setStroke(1, Color.parseColor(BorderColor));
        setBackgroundDrawable(drawable);
        H2CO3CustomUtils.setViewSize(context, this, ButtonSize.get(0), ButtonSize.get(1));
        setAllCaps(false);
        setX(H2CO3CustomUtils.percentTopx(ButtonLocation.get(0), H2CO3CustomManager.scrennWidth));
        setY(H2CO3CustomUtils.percentTopx(ButtonLocation.get(1), H2CO3CustomManager.screenHeight));
    }


    public void Refresh() {
        List<Double> ButtonLocation = new ArrayList<Double>();
        ButtonLocation.add(H2CO3CustomUtils.pxToPercent(this.getX(), H2CO3CustomManager.scrennWidth));
        ButtonLocation.add(H2CO3CustomUtils.pxToPercent(this.getY(), H2CO3CustomManager.screenHeight));
        this.ButtonLocation.clear();
        this.ButtonLocation.addAll(ButtonLocation);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getString() {
        return String;
    }

    public void setString(String String) {
        this.String = String;
    }

    public String getButtonKeyType() {
        return ButtonKeyType;
    }

    public void setButtonKeyType(String ButtonKeyType) {
        this.ButtonKeyType = ButtonKeyType;
    }

    public List<String> getButtonKeyValueGroups() {
        return ButtonKeyValueGroups;
    }

    public void setButtonKeyValueGroups(List<String> ButtonKeyValueGroups) {
        this.ButtonKeyValueGroups = ButtonKeyValueGroups;
    }

    public int getButtonTextSize() {
        return ButtonTextSize;
    }

    public void setButtonTextSize(int ButtonTextSize) {
        this.ButtonTextSize = ButtonTextSize;
    }

    public List<Double> getButtonLocation() {
        return ButtonLocation;
    }

    public void setButtonLocation(List<Double> ButtonLocation) {
        this.ButtonLocation = ButtonLocation;
    }

    public List<Integer> getButtonSize() {
        return ButtonSize;
    }

    public void setButtonSize(List<Integer> ButtonSize) {
        this.ButtonSize = ButtonSize;
    }

    public String getButtonTextColor() {
        return ButtonTextColor;
    }

    public void setButtonTextColor(String ButtonTextColor) {
        this.ButtonTextColor = ButtonTextColor;
    }

    public int getCornerRadius() {
        return CornerRadius;
    }

    public void setCornerRadius(int CornerRadius) {
        this.CornerRadius = CornerRadius;
    }

    public String getBorderColor() {
        return BorderColor;
    }

    public void setBorderColor(String BorderColor) {
        this.BorderColor = BorderColor;
    }

    public boolean isButtonAutoHold() {
        return ButtonAutoHold;
    }

    public void setButtonAutoHold(boolean ButtonAutoHold) {
        this.ButtonAutoHold = ButtonAutoHold;
    }

    public boolean isButtonControlsMousePointer() {
        return ButtonControlsMousePointer;
    }

    public void setButtonControlsMousePointer(boolean ButtonControlsMousePointer) {
        this.ButtonControlsMousePointer = ButtonControlsMousePointer;
    }

    public String getCommand() {
        return Command;
    }

    public void setCommand(String Command) {
        this.Command = Command;
    }

    public List<String> getTargetKeyID() {
        return TargetKeyID;
    }

    public void setTargetKeyID(List<String> TargetKeyID) {
        this.TargetKeyID = TargetKeyID;
    }

    public boolean isButtonHideByDefault() {
        return ButtonHideByDefault;
    }

    public void setButtonHideByDefault(boolean ButtonHideByDefault) {
        this.ButtonHideByDefault = ButtonHideByDefault;
    }

    public void setisAutoHold(boolean isAutoHold) {
        this.isAutoHold = isAutoHold;
        if (isAutoHold) {
            this.setTextColor(Color.RED);
        } else {
            this.setTextColor(Color.parseColor(ButtonTextColor));
        }
    }

    public boolean isisAutoHold() {
        return isAutoHold;
    }

    @NonNull
    @Override
    public String toString() {
        return "[" + getClass().getCanonicalName() + "]"
                + "\nID=" + ID
                + ",\nString=" + String
                + ",\nButtonKeyType=" + ButtonKeyType
                + ",\nButtonKeyValueGroups=" + ButtonKeyValueGroups
                + ",\nButtonTextSize=" + ButtonTextSize
                + ",\nButtonLocation=" + ButtonLocation.get(0)
                + ",\nButtonSize=" + ButtonSize
                + ",\nButtonTextColor=" + ButtonTextColor
                + ",\nCornerRadius=" + CornerRadius
                + ",\nBorderColor=" + BorderColor
                + ",\nButtonAutoHold=" + ButtonAutoHold
                + ",\nButtonControlsMousePointer=" + ButtonControlsMousePointer
                + ",\nCommand=" + Command
                + ",\nTargetKeyID=" + TargetKeyID;
    }
}
