package com.shamanland.fonticon;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ToggleButton;

public class FontIconToggleButton extends ToggleButton {
    public FontIconToggleButton(Context context) {
        super(context);
    }

    public FontIconToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            CompoundDrawables.init(context, attrs, this);
        }
    }

    public FontIconToggleButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (!isInEditMode()) {
            CompoundDrawables.init(context, attrs, this);
        }
    }

    public void updateCompoundDrawables() {
        CompoundDrawables.update(this);
    }
}
