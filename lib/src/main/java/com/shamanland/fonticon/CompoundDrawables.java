package com.shamanland.fonticon;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import static com.shamanland.fonticon.BuildConfig.DEBUG;

public class CompoundDrawables {
    public static boolean init(Context context, AttributeSet attrs, TextView view) {
        if (view == null) {
            if (DEBUG) {
                Log.e(FontIconView.class.getSimpleName(), "init: view is null");
            }

            return false;
        }

        Resources.Theme theme = context.getTheme();
        if (theme == null) {
            if (DEBUG) {
                Log.w(FontIconView.class.getSimpleName(), "init: context.getTheme() returns null");
            }

            return false;
        }

        TypedArray a = theme.obtainStyledAttributes(attrs, R.styleable.CompoundDrawables, 0, 0);
        if (a == null) {
            if (DEBUG) {
                Log.w(FontIconView.class.getSimpleName(), "init: theme.obtainStyledAttributes() returns null");
            }

            return false;
        }

        try {
            int[] ids = new int[4];
            ids[0] = a.getResourceId(R.styleable.CompoundDrawables_iconLeft, 0);
            ids[1] = a.getResourceId(R.styleable.CompoundDrawables_iconTop, 0);
            ids[2] = a.getResourceId(R.styleable.CompoundDrawables_iconRight, 0);
            ids[3] = a.getResourceId(R.styleable.CompoundDrawables_iconBottom, 0);

            Drawable[] compound = view.getCompoundDrawables();
            if (compound == null) {
                compound = new Drawable[4];
            }

            boolean update = false;

            for (int i = 0; i < 4; ++i) {
                if (ids[i] != 0) {
                    compound[i] = FontIconDrawable.inflate(context.getResources(), ids[i]);
                    update = true;
                }
            }

            if (update) {
                view.setCompoundDrawables(compound[0], compound[1], compound[2], compound[3]);
            }
        } finally {
            a.recycle();
        }

        return true;
    }

    public static void update(TextView view) {
        if (view == null) {
            if (DEBUG) {
                Log.e(FontIconView.class.getSimpleName(), "update: view is null");
            }

            return;
        }

        Drawable[] drawables = view.getCompoundDrawables();
        if (drawables != null) {
            view.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        }
    }
}
