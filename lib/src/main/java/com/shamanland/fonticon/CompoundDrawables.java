package com.shamanland.fonticon;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
            initCompoundDrawables(context, view, a);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                initCompoundDrawablesRelative(context, view, a);
            }
        } finally {
            a.recycle();
        }

        return true;
    }

    private static boolean inflateDrawables(Context context, int[] ids, Drawable[] compound) {
        boolean update = false;

        for (int i = 0; i < 4; ++i) {
            if (ids[i] != 0) {
                compound[i] = FontIconDrawable.inflate(context, ids[i]);
                update = true;
            }
        }

        return update;
    }

    private static void initCompoundDrawables(Context context, TextView view, TypedArray a) {
        int[] ids = new int[4];
        ids[0] = a.getResourceId(R.styleable.CompoundDrawables_iconLeft, 0);
        ids[1] = a.getResourceId(R.styleable.CompoundDrawables_iconTop, 0);
        ids[2] = a.getResourceId(R.styleable.CompoundDrawables_iconRight, 0);
        ids[3] = a.getResourceId(R.styleable.CompoundDrawables_iconBottom, 0);

        Drawable[] compound = view.getCompoundDrawables();
        if (compound == null) {
            compound = new Drawable[4];
        }

        if (inflateDrawables(context, ids, compound)) {
            view.setCompoundDrawables(compound[0], compound[1], compound[2], compound[3]);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void initCompoundDrawablesRelative(Context context, TextView view, TypedArray a) {
        int[] ids = new int[4];
        ids[0] = a.getResourceId(R.styleable.CompoundDrawables_iconStart, 0);
        ids[1] = 0; // this method will be invoked after #initCompoundDrawables()
        ids[2] = a.getResourceId(R.styleable.CompoundDrawables_iconEnd, 0);
        ids[3] = 0; // this method will be invoked after #initCompoundDrawables()

        Drawable[] compound = view.getCompoundDrawablesRelative();
        if (compound == null) {
            compound = new Drawable[4];
        }

        if (inflateDrawables(context, ids, compound)) {
            view.setCompoundDrawablesRelative(compound[0], compound[1], compound[2], compound[3]);
        }
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void updateRelative(TextView view) {
        if (view == null) {
            if (DEBUG) {
                Log.e(FontIconView.class.getSimpleName(), "updateRelative: view is null");
            }

            return;
        }

        Drawable[] drawables = view.getCompoundDrawablesRelative();
        if (drawables != null) {
            view.setCompoundDrawablesRelative(drawables[0], drawables[1], drawables[2], drawables[3]);
        }
    }
}
