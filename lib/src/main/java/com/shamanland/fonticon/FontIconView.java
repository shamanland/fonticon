package com.shamanland.fonticon;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.CheckedTextView;

public class FontIconView extends CheckedTextView {
    private boolean mOverridePressed;
    private int mPressedGlowColor;
    private float mPressedGlowRadius;

    private int mPressedOldShadowColor;
    private float mPressedOldShadowRadius;
    private float mPressedOldShadowDx;
    private float mPressedOldShadowDy;

    private final Runnable mOldValuesSaver;

    public FontIconView(Context context) {
        super(context);

        initDefaults();
    }

    public FontIconView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!init(context, attrs)) {
            initDefaults();
        }
    }

    public FontIconView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (!init(context, attrs)) {
            initDefaults();
        }
    }

    private boolean init(Context context, AttributeSet attrs) {
        Resources.Theme theme = context.getTheme();
        if (theme == null) {
            if (BuildConfig.DEBUG) {
                Log.w(FontIconView.class.getSimpleName(), "init failed: context.getTheme() returns null");
            }

            return false;
        }

        TypedArray a = theme.obtainStyledAttributes(attrs, R.styleable.FontIconView, 0, 0);
        if (a == null) {
            if (BuildConfig.DEBUG) {
                Log.w(FontIconView.class.getSimpleName(), "init failed: theme.obtainStyledAttributes() returns null");
            }

            return false;
        }

        try {
            mOverridePressed = a.getBoolean(R.styleable.FontIconView_overridePressed, false);
            mPressedGlowColor = a.getColor(R.styleable.FontIconView_pressedGlowColor, Color.TRANSPARENT);
            mPressedGlowRadius = a.getDimension(R.styleable.FontIconView_pressedGlowRadius, 0);
        } finally {
            a.recycle();
        }

        return true;
    }

    private void initDefaults() {
        mOverridePressed = false;
        mPressedGlowColor = Color.TRANSPARENT;
        mPressedGlowRadius = 0;
    }

    /* instance initialization block */ {
        if (isInEditMode()) {
            mOldValuesSaver = null;
        } else {
            setTypeface(FontIconTypefaceHolder.getTypeface());

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                mOldValuesSaver = new Runnable() {
                    @Override
                    public void run() {
                        saveOldValues();
                    }
                };
            } else {
                mOldValuesSaver = new Runnable() {
                    @Override
                    public void run() {
                        saveOldValuesJB();
                    }
                };
            }
        }
    }

    protected void saveOldValues() {
        mPressedOldShadowColor = TextPaintHelper.getShadowColor(getPaint());
        mPressedOldShadowRadius = TextPaintHelper.getShadowRadius(getPaint());
        mPressedOldShadowDx = TextPaintHelper.getShadowDx(getPaint());
        mPressedOldShadowDy = TextPaintHelper.getShadowDy(getPaint());
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void saveOldValuesJB() {
        mPressedOldShadowColor = getShadowColor();
        mPressedOldShadowRadius = getShadowRadius();
        mPressedOldShadowDx = getShadowDx();
        mPressedOldShadowDy = getShadowDy();
    }

    @Override
    protected void dispatchSetPressed(boolean pressed) {
        super.dispatchSetPressed(pressed);

        if (!mOverridePressed) {
            return;
        }

        if (pressed) {
            mOldValuesSaver.run();
            setShadowLayer(mPressedGlowRadius, 0f, 0f, mPressedGlowColor);
        } else {
            setShadowLayer(mPressedOldShadowRadius, mPressedOldShadowDx, mPressedOldShadowDy, mPressedOldShadowColor);
        }
    }

    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
    }
}
