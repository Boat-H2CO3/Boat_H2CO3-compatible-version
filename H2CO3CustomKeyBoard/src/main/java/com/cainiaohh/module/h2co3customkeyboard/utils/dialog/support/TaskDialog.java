package com.cainiaohh.module.h2co3customkeyboard.utils.dialog.support;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cainiaohh.module.h2co3customkeyboard.R;

public class TaskDialog extends Dialog {

    private final TextView textTotalTaskName;
    private final TextView textCurrentTaskName;

    public TaskDialog(@NonNull Context context, boolean cancelable) {
        super(context);
        setContentView(R.layout.dialog_task);
        textTotalTaskName = findViewById(R.id.dialog_task_text_total_task_name);
        textCurrentTaskName = findViewById(R.id.dialog_task_text_current_task_name);
        setCancelable(cancelable);
    }

    public TaskDialog setTotalTaskName(String taskname) {
        textTotalTaskName.setText(taskname);
        return this;
    }

    public TaskDialog setCurrentTaskName(String taskname) {
        textCurrentTaskName.setText(taskname);
        return this;
    }

    public TextView getTextTotalTaskName() {
        return textTotalTaskName;
    }

    public TextView getTextCurrentTaskName() {
        return textCurrentTaskName;
    }
}

