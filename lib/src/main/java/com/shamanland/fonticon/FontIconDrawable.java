package com.shamanland.fonticon;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.LayoutDirection;
import android.util.Log;
import android.util.Xml;
import android.view.AbsSavedState;
import android.view.InflateException;
import android.view.View;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.shamanland.fonticon.BuildConfig.DEBUG;

public class FontIconDrawable extends Drawable {
    private static final String LOG_TAG = FontIconDrawable.class.getSimpleName();

    private String mText;
    private ColorStateList mTextColor;
    private float mTextSize;
    private boolean mAutoMirrored;
    private boolean mNeedMirroring;

    private TextPaint mPaint;
    private Rect mRect;
    private boolean mRestoring;
    private boolean mBoundsChanged;

    public static FontIconDrawable inflate(Context context, int xmlId) {
        XmlResourceParser parser = context.getResources().getXml(xmlId);
        if (parser == null) {
            throw new InflateException();
        }

        try {
            int type;
            while ((type = parser.next()) != XmlPullParser.END_DOCUMENT) {
                final String name = parser.getName();

                if (type != XmlPullParser.START_TAG) {
                    continue;
                }

                if ("font-icon".equals(name)) {
                    FontIconDrawable result = new FontIconDrawable();
                    result.inflate(context, Xml.asAttributeSet(parser));
                    return result;
                } else {
                    throw new InflateException(name);
                }
            }
        } catch (XmlPullParserException ex) {
            throw new InflateException(ex);
        } catch (IOException ex) {
            throw new InflateException(ex);
        }

        throw new InflateException();
    }

    public FontIconDrawable() {
        mText = "";
        mPaint = new TextPaint();
        mRect = new Rect();

        mPaint.setTypeface(FontIconTypefaceHolder.getTypeface());
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.LEFT);
    }

    public void inflate(Context context, AttributeSet attrs) throws XmlPullParserException, IOException {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FontIconDrawable);
        if (a == null) {
            if (DEBUG) {
                Log.w(FontIconDrawable.class.getSimpleName(), "inflate failed: r.obtainAttributes() returns null");
            }

            return;
        }

        try {
            mText = a.getString(R.styleable.FontIconDrawable_text);
            if (mText == null) {
                mText = "";
            }

            mTextColor = a.getColorStateList(R.styleable.FontIconDrawable_textColor);
            if (mTextColor == null) {
                mTextColor = ColorStateList.valueOf(0);
            }

            mTextSize = a.getDimension(R.styleable.FontIconDrawable_textSize, 9f);

            mAutoMirrored = a.getBoolean(R.styleable.FontIconDrawable_autoMirrored, false);
            mNeedMirroring = a.getBoolean(R.styleable.FontIconDrawable_needMirroring, false);
        } finally {
            a.recycle();
        }

        updatePaint(true, true);
    }

    private void updatePaint() {
        updatePaint(false, false);
    }

    private void updatePaint(boolean updateBounds, boolean forcedInvalidate) {
        boolean colorChanged = updatePaintColor(getState());

        boolean textSizeChanged = false;

        float oldTextSize = mPaint.getTextSize();
        float newTextSize = mTextSize;

        if (Float.compare(oldTextSize, newTextSize) != 0) {
            mPaint.setTextSize(newTextSize);
            textSizeChanged = true;
        }

        if (textSizeChanged || updateBounds) {
            mBoundsChanged = false;

            mPaint.getTextBounds(mText, 0, mText.length(), mRect);
            fitRect(mRect, (int) mTextSize);
            setBounds(mRect);
        }

        if (colorChanged || forcedInvalidate || mBoundsChanged) {
            invalidateSelf();
        }
    }

    private static void fitRect(Rect rect, int size) {
        int w = rect.width();
        if (size > w) {
            int d1 = (size - w) / 2;
            int d2 = size - w - d1;
            rect.left -= d1;
            rect.right += d2;
        }

        int h = rect.height();
        if (size > h) {
            int d1 = (size - h) / 2;
            int d2 = size - h - d1;
            rect.top -= d1;
            rect.bottom += d2;
        }
    }

    private boolean updatePaintColor(int[] state) {
        int oldColor = mPaint.getColor();
        int newColor = mTextColor.getColorForState(state, mTextColor.getDefaultColor());

        if (oldColor != newColor) {
            mPaint.setColor(newColor);
            return true;
        }

        return false;
    }

    @Override
    public boolean isStateful() {
        return mTextColor.isStateful();
    }

    @Override
    protected boolean onStateChange(int[] state) {
        return updatePaintColor(state);
    }

    @SuppressWarnings("unused")
    public TextPaint getPaint() {
        return mPaint;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text != null ? text : "";

        if (!mRestoring) {
            updatePaint(true, false);
        }
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(float textSize) {
        mTextSize = textSize;

        if (!mRestoring) {
            updatePaint();
        }
    }

    public int getTextColor() {
        return mTextColor.getColorForState(getState(), mTextColor.getDefaultColor());
    }

    public void setTextColor(int color) {
        mTextColor = ColorStateList.valueOf(color);

        if (!mRestoring) {
            updatePaint();
        }
    }

    public ColorStateList getTextColorStateList() {
        return mTextColor;
    }

    public void setTextColorStateList(ColorStateList color) {
        mTextColor = color;
        if (mTextColor == null) {
            mTextColor = ColorStateList.valueOf(0);
        }

        if (!mRestoring) {
            updatePaint();
        }
    }

    public boolean isAutoMirrored() {
        return mAutoMirrored;
    }

    public void setAutoMirrored(boolean autoMirrored) {
        mAutoMirrored = autoMirrored;
    }

    @Deprecated
    public boolean isNeedMirroring() {
        return mNeedMirroring;
    }

    @Deprecated
    @SuppressWarnings("unused")
    public void setNeedMirroring(boolean needMirroring) {
        mNeedMirroring = needMirroring;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    protected void onBoundsChange(Rect bounds) {
        mBoundsChanged = true;
    }

    @Override
    public int getIntrinsicWidth() {
        return mRect.width();
    }

    @Override
    public int getIntrinsicHeight() {
        return mRect.height();
    }

    protected boolean needMirroring() {
        //noinspection SimplifiableIfStatement
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            //noinspection deprecation
            return isNeedMirroring();
        } else {
            return isAutoMirrored() && MethodGetLayoutDirection.invoke(this) == LayoutDirection.RTL;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        final boolean needMirroring = needMirroring();

        if (needMirroring) {
            canvas.save();
            canvas.translate(mRect.right - mRect.left, 0);
            canvas.scale(-1.0f, 1.0f);
        }

        canvas.drawText(mText, -mRect.left, -mRect.top, mPaint);

        if (needMirroring) {
            canvas.restore();
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(AbsSavedState.EMPTY_STATE);

        ss.text = getText();
        ss.textColor = getTextColorStateList();
        ss.textSize = getTextSize();
        ss.autoMirrored = isAutoMirrored();
        //noinspection deprecation
        ss.needMirroring = isNeedMirroring();

        return ss;
    }

    public void onRestoreInstanceState(Parcelable savedState) {
        if (savedState instanceof SavedState) {
            SavedState ss = (SavedState) savedState;

            try {
                mRestoring = true;

                setText(ss.text);
                setTextColorStateList(ss.textColor);
                setTextSize(ss.textSize);
                setAutoMirrored(ss.autoMirrored);
                //noinspection deprecation
                setNeedMirroring(ss.needMirroring);
            } finally {
                mRestoring = false;
            }

            updatePaint(true, false);
        }
    }

    static class SavedState extends View.BaseSavedState {
        String text;
        ColorStateList textColor;
        float textSize;
        boolean autoMirrored;
        boolean needMirroring;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(@SuppressWarnings("NullableProblems") Parcel out, int flags) {
            super.writeToParcel(out, flags);

            out.writeString(text);
            out.writeParcelable(textColor, flags);
            out.writeFloat(textSize);
            out.writeInt(autoMirrored ? 1 : 0);
            out.writeInt(needMirroring ? 1 : 0);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        protected SavedState(Parcel in) {
            super(in);

            text = in.readString();
            textColor = in.readParcelable(null);
            textSize = in.readFloat();
            autoMirrored = in.readInt() == 1;
            needMirroring = in.readInt() == 1;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    static class MethodGetLayoutDirection {
        static Method sMethod;

        static {
            try {
                sMethod = Drawable.class.getDeclaredMethod("getLayoutDirection");
            } catch (Throwable ex) {
                if (DEBUG) {
                    Log.w(LOG_TAG, ex);
                }
            }
        }

        static int invoke(Drawable drawable) {
            if (sMethod != null) {
                try {
                    Object result = sMethod.invoke(drawable);
                    if (result instanceof Integer) {
                        return (Integer) result;
                    }
                } catch (Throwable ex) {
                    if (DEBUG) {
                        Log.w(LOG_TAG, ex);
                    }
                }
            }

            return LayoutDirection.LTR;
        }
    }
}
