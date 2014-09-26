FontIcon Library
====

[![Build Status](https://travis-ci.org/shamanland/fonticon.svg?branch=master)](https://travis-ci.org/shamanland/fonticon)

FontIcon is simple library which use font-based icons in Android.

Links
----

- [Project page][1]
- [Example video][2]

Get demo on Google Play
----

<a href="https://play.google.com/store/apps/details?id=com.shamanland.fonticon.example">
<img alt="Get it on Google Play" src="http://developer.android.com/images/brand/en_generic_rgb_wo_45.png" />
</a>

Pros and cons
----

**Advantages**

- Single ``.ttf`` file instead of lot of bitmaps for different densities (mdpi, hdpi, xhdpi, etc.)
- Scalable vector graphics instead of raster images
- Possibility to use any color for icon in run-time
- Possibility to use any graphics effect which is available for TextView

**Disadvantages**

- Not easy to add additional icons

Gradle dependency
----

    dependencies {
        compile 'com.shamanland:fonticon:0.1.8'
    }

Usage
----

**1. Create your font with icons**

Do it yourself. Check tutorial on [fontastic.me][4] service or find other service.

**2. Prepare your font to be used in Android.**

Read this [manual][5] for details. In case of fontastic use [this util][3].

**3. Declare your FontIconDrawable in xml**

[**res/xml/ic_android.xml**][6]

    <?xml version="1.0" encoding="utf-8"?>
    <font-icon
            xmlns:android="http://schemas.android.com/apk/res-auto"
            android:text="@string/ic_android"
            android:textSize="@dimen/big_icon_size"
            android:textColor="@color/green_170"
            />

**4. Inflate your FontIconDrawable in Java**

    Drawable icon = FontIconDrawable.inflate(getResources(), R.xml.ic_android);

**5. Use your FontIconDrawable as compound for TextView, EditText or Button (in res/layout with custom class)**

    <com.shamanland.fonticon.FontIconTextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Dummy text"
            app:iconLeft="@xml/ic_android"
            />

    <com.shamanland.fonticon.FontIconEditText
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

**6. Use your FontIconDrawable as compound for TextView (in Java-code with platform class)**

    Drawable icon = FontIconDrawable.inflate(getResources(), R.xml.ic_android);
    TextView tv = (TextView) result.findViewById(R.id.my_textview_or_button);
    tv.setCompoundDrawables(icon, null, null, null);

**7. Use FontIconView as single icon in your layout**

    <com.shamanland.fonticon.FontIconView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/ic_android"
            android:textSize="@dimen/icon_size"
            android:textColor="@color/icon_color"
            />

**8. Add glowing effect on touch for your FontIconView**

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

Tools
----

[FontIcon-Prepare][3] - prepares web fonts to be used with this library

License
--------

    Copyright 2014 ShamanLand.Com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: http://shamanland.github.io/fonticon
[2]: https://docs.google.com/file/d/0Bwh0SNLPmjQBbXJVd3c3S2hfVTg/preview
[3]: http://github.com/shamanland/fonticon-prepare/
[4]: http://fontastic.me/howto
[5]: http://blog.shamanland.com/2013/11/how-to-use-icon-fonts-in-android.html
[6]: http://github.com/shamanland/fonticon/blob/dev/app/src/main/res/xml/ic_android.xml
[7]: http://github.com/shamanland/fonticon/blob/dev/app/src/main/res/layout/f_glowing.xml
