package com.shamanland.fonticon;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.AbsSavedState;
import android.view.InflateException;
import android.view.View;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class FontIconDrawable extends Drawable {
    private String mText;
    private TextPaint mPaint;
    private Rect mRect;
    private boolean mRestoring;
    private boolean mBoundsChanged;

    public static FontIconDrawable inflate(Resources resources, int xmlId) {
        XmlResourceParser parser = resources.getXml(xmlId);
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
                    result.inflate(resources, parser, Xml.asAttributeSet(parser));
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

    @Override
    public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs) throws XmlPullParserException, IOException {
        super.inflate(r, parser, attrs);

        TypedArray a = r.obtainAttributes(attrs, R.styleable.FontIconDrawable);
        if (a == null) {
            if (BuildConfig.DEBUG) {
                Log.w(FontIconDrawable.class.getSimpleName(), "inflate failed: r.obtainAttributes() returns null");
            }

            return;
        }

        try {
            mText = a.getString(R.styleable.FontIconDrawable_text);
            mPaint.setColor(a.getColor(R.styleable.FontIconDrawable_textColor, Color.BLACK));
            mPaint.setTextSize(a.getDimension(R.styleable.FontIconDrawable_textSize, 9f));
        } finally {
            a.recycle();
        }

        updateBounds();
    }

    private void updateBounds() {
        mBoundsChanged = false;

        mPaint.getTextBounds(mText, 0, mText.length(), mRect);
        setBounds(mRect);

        if (mBoundsChanged) {
            invalidateSelf();
        }
    }

    public TextPaint getPaint() {
        return mPaint;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text != null ? text : "";

        if (!mRestoring) {
            updateBounds();
        }
    }

    public float getTextSize() {
        return mPaint.getTextSize();
    }

    public void setTextSize(float textSize) {
        mPaint.setTextSize(textSize);

        if (!mRestoring) {
            updateBounds();
        }
    }

    public int getTextColor() {
        return mPaint.getColor();
    }

    public void setTextColor(int color) {
        mPaint.setColor(color);

        if (!mRestoring) {
            invalidateSelf();
        }
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

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(mText, -mRect.left, -mRect.top, mPaint);
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(AbsSavedState.EMPTY_STATE);

        ss.text = getText();
        ss.textColor = getTextColor();
        ss.textSize = getTextSize();

        return ss;
    }

    public void onRestoreInstanceState(Parcelable savedState) {
        if (savedState instanceof SavedState) {
            SavedState ss = (SavedState) savedState;

            try {
                mRestoring = true;

                setText(ss.text);
                setTextColor(ss.textColor);
                setTextSize(ss.textSize);
            } finally {
                mRestoring = false;
            }

            updateBounds();

            if (!mBoundsChanged) {
                invalidateSelf();
            }
        }
    }

    static class SavedState extends View.BaseSavedState {
        String text;
        int textColor;
        float textSize;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);

            out.writeString(text);
            out.writeInt(textColor);
            out.writeFloat(textSize);
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
            textColor = in.readInt();
            textSize = in.readFloat();
        }
    }
}
