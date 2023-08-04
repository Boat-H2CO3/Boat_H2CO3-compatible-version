package org.koishi.launcher.h2co3.launcher.control.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;

public class ControlLayout extends TextureView {
    private boolean mModifiable;
    private final boolean mControlVisible = false;

    public ControlLayout(Context ctx) {
        super(ctx);
    }

    public ControlLayout(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
    }


    public boolean getModifiable() {
        return mModifiable;
    }
}
