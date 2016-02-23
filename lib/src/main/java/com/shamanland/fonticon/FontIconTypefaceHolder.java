package com.shamanland.fonticon;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.io.File;

public class FontIconTypefaceHolder {
    private static Typeface sTypeface;

    public static Typeface getTypeface() {
        if (sTypeface == null) {
            throw new IllegalStateException();
        }

        return sTypeface;
    }

    public static void init(AssetManager assets, String path) {
        sTypeface = Typeface.createFromAsset(assets, path);
    }

    public static void init(File file) {
        sTypeface = Typeface.createFromFile(file);
    }
}
