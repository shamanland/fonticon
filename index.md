---
layout: default
title: FontIcon Library
---
FontIcon is simple library which use font-based icons in Android.

## Pros and cons

**Advantages**

- Single ``.ttf`` file instead of lot of bitmaps for different densities (mdpi, hdpi, xhdpi, etc.)
- Scalable vector graphics instead of raster images
- Possibility to use any color for icon in run-time
- Possibility to use any graphics effect which is available for TextView

**Disadvantages**

- Not easy to add additional icons

## Preparations

**1. Create font with icons**

Check tutorial on [fontastic.me][4] service or find other service.

**2. Prepare it to be used in Android**

In case of fontastic use [this tool][3], in other cases read this [manual][5] for details.

**3. Include into project**

    src/main/assets/icons.ttf
    src/main/res/values/icons.xml

**4. Gradle dependency**

Release:

    repositories {
        mavenCentral()
    }

    dependencies {
        compile 'com.shamanland:fonticon:0.1.6'
    }

Snapshot:

    repositories {
        maven {
            url 'https://oss.sonatype.org/content/groups/public'
        }
    }

    dependencies {
        compile 'com.shamanland:fonticon:0.1.6-SNAPSHOT'
    }

## Usage

**Declare your FontIconDrawable in xml**

[**res/xml/ic_android.xml**][6]

    <?xml version="1.0" encoding="utf-8"?>
    <font-icon
        xmlns:android="http://schemas.android.com/apk/res-auto"
        android:text="@string/ic_android"
        android:textSize="@dimen/big_icon_size"
        android:textColor="@color/green_170"
        />

**Inflate your FontIconDrawable in Java**

    Drawable icon = FontIconDrawable.inflate(getResources(), R.xml.ic_android);

**Use your FontIconDrawable as compound for TextView or Button (in res/layout with custom class)**

    <com.shamanland.fonticon.FontIconTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Dummy text"
        app:iconLeft="@xml/ic_android"
        />

    <com.shamanland.fonticon.FontIconButton
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Dummy text"
        app:iconLeft="@xml/ic_button_yes"
        />

**Use your FontIconDrawable as compound for TextView (in Java-code with platform class)**

    Drawable icon = FontIconDrawable.inflate(getResources(), R.xml.ic_android);
    TextView tv = (TextView) result.findViewById(R.id.my_textview_or_button);
    tv.setCompoundDrawables(icon, null, null, null);

**Use FontIconView as single icon in your layout**

    <com.shamanland.fonticon.FontIconView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/ic_android"
        android:textSize="@dimen/icon_size"
        android:textColor="@color/icon_color"
        />

**Add glowing effect on touch for your FontIconView**

[**res/layout/f_glowing.xml**][7]

    <com.shamanland.fonticon.FontIconView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="@dimen/icon_glow_radius"
        android:text="@string/ic_googleplus"
        android:textSize="@dimen/big_icon_size"
        android:textColor="@color/pressed_googleplus"
        android:clickable="true"
        app:overridePressed="true"
        app:pressedGlowColor="@color/googleplus"
        app:pressedGlowRadius="@dimen/icon_glow_radius"
        />

## Tools

[FontIcon-Prepare][3] - prepares web fonts to be used with this library

[1]: http://shamanland.github.io/fonticon
[2]: https://docs.google.com/file/d/0Bwh0SNLPmjQBbXJVd3c3S2hfVTg/preview
[3]: http://github.com/shamanland/fonticon-prepare/
[4]: http://fontastic.me/howto
[5]: http://blog.shamanland.com/2013/11/how-to-use-icon-fonts-in-android.html
[6]: http://github.com/shamanland/fonticon/blob/dev/app/src/main/res/xml/ic_android.xml
[7]: http://github.com/shamanland/fonticon/blob/dev/app/src/main/res/layout/f_glowing.xml
