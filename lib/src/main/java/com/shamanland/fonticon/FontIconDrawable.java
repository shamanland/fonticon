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
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class FontIconDrawable extends Drawable {
    private String mText;
    private TextPaint mPaint;
    private Rect mRect;

    public static FontIconDrawable inflate(Resources resources, int xmlId) {
        XmlResourceParser parser = resources.getXml(xmlId);

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

        try {
            mText = a.getString(R.styleable.FontIconDrawable_text);
            mPaint.setColor(a.getColor(R.styleable.FontIconDrawable_textColor, Color.BLACK));
            mPaint.setTextSize(a.getDimension(R.styleable.FontIconDrawable_textSize, 9f));
        } finally {
            a.recycle();
        }

        invalidateSelf();
    }

    public void setText(String text) {
        mText = text != null ? text : "";
        invalidateSelf();
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

    @Override
    public void invalidateSelf() {
        super.invalidateSelf();

        mPaint.getTextBounds(mText, 0, mText.length(), mRect);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(mText, -mRect.exactCenterX(), -mRect.exactCenterY(), mPaint);
    }
}
