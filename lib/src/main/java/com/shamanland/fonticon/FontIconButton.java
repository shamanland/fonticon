package com.shamanland.fonticon;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class FontIconButton extends Button {
    public FontIconButton(Context context) {
        super(context);
    }

    public FontIconButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            CompoundDrawables.init(context, attrs, this);
        }
    }

    public FontIconButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (!isInEditMode()) {
            CompoundDrawables.init(context, attrs, this);
        }
    }
}
