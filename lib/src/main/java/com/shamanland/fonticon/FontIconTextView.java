package com.shamanland.fonticon;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontIconTextView extends TextView {
    public FontIconTextView(Context context) {
        super(context);
    }

    public FontIconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            CompoundDrawables.init(context, attrs, this);
        }
    }

    public FontIconTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (!isInEditMode()) {
            CompoundDrawables.init(context, attrs, this);
        }
    }

    public void updateCompoundDrawables() {
        CompoundDrawables.update(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void updateCompoundDrawablesRelative() {
        CompoundDrawables.updateRelative(this);
    }
}
