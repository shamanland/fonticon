package com.shamanland.fonticon.example;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shamanland.fonticon.FontIconDrawable;
import com.shamanland.fonticon.FontIconTextView;

public class CompoundIconsFragment extends ContentFragment {
    private static final int[] sIcons = {R.string.ic_android, R.string.ic_camera, R.string.ic_compound};
    private static final int[] sColors = {Color.GREEN, 0xffff8000, Color.MAGENTA
    };

    protected FontIconDrawable mIcon;
    protected int mColorIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View result = inflater.inflate(R.layout.f_compound, container, false);

        final FontIconTextView tv = (FontIconTextView) result.findViewById(R.id.example_text);

        // left, top, right, bottom
        final Drawable[] drawables = tv.getCompoundDrawables();
        mIcon = (FontIconDrawable) drawables[0];

        if (state != null) {
            mIcon.onRestoreInstanceState(state.getParcelable("icon"));
            tv.updateCompoundDrawables();

            mColorIndex = state.getInt("color.index");
        }

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mColorIndex = (mColorIndex + 1) % sIcons.length;

                mIcon.setText(getString(sIcons[mColorIndex]));
                mIcon.setTextColor(sColors[mColorIndex]);

                // use FontIconTextView method
                tv.updateCompoundDrawables();

                // or CompoundDrawables.update(TextView) if you don't want to cast FontIconTextView

                // or manually
                // tv.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
            }
        });

        return result;
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        state.putParcelable("icon", mIcon != null ? mIcon.onSaveInstanceState() : null);
        state.putInt("color.index", mColorIndex);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.compound, menu);

        menu.findItem(R.id.action_like).setIcon(FontIconDrawable.inflate(getResources(), R.xml.ic_ab_like));
        menu.findItem(R.id.action_group).setIcon(FontIconDrawable.inflate(getResources(), R.xml.ic_ab_group));
    }
}
