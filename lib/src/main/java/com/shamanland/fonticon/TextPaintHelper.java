package com.shamanland.fonticon;

import android.text.TextPaint;
import android.util.Log;

import java.lang.reflect.Field;

import static com.shamanland.fonticon.BuildConfig.SNAPSHOT;

class TextPaintHelper {
    private static final Field sShadowColor;
    private static final Field sShadowRadius;
    private static final Field sShadowDx;
    private static final Field sShadowDy;

    static {
        try {
            sShadowColor = TextPaint.class.getDeclaredField("shadowColor");
            sShadowColor.setAccessible(true);

            sShadowRadius = TextPaint.class.getDeclaredField("shadowRadius");
            sShadowRadius.setAccessible(true);

            sShadowDx = TextPaint.class.getDeclaredField("shadowDx");
            sShadowDx.setAccessible(true);

            sShadowDy = TextPaint.class.getDeclaredField("shadowDy");
            sShadowDy.setAccessible(true);
        } catch (Throwable ex) {
            throw new AssertionError(ex);
        }
    }

    private static int getInt(Object object, Field field) {
        try {
            Object result = field.get(object);
            if (result instanceof Integer) {
                return ((Integer) result);
            }
        } catch (Throwable ex) {
            if (SNAPSHOT) {
                Log.e(TextPaintHelper.class.getSimpleName(), field.getName(), ex);
            }
        }

        return 0;
    }

    private static float getFloat(Object object, Field field) {
        try {
            Object result = field.get(object);
            if (result instanceof Float) {
                return ((Float) result);
            }
        } catch (Throwable ex) {
            if (SNAPSHOT) {
                Log.e(TextPaintHelper.class.getSimpleName(), field.getName(), ex);
            }
        }

        return 0f;
    }

    public static int getShadowColor(TextPaint paint) {
        return getInt(paint, sShadowColor);
    }

    public static float getShadowRadius(TextPaint paint) {
        return getFloat(paint, sShadowRadius);
    }

    public static float getShadowDx(TextPaint paint) {
        return getFloat(paint, sShadowDx);
    }

    public static float getShadowDy(TextPaint paint) {
        return getFloat(paint, sShadowDy);
    }
}
