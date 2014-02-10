package com.shamanland.fonticon;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ScaledIconsFragment extends ContentFragment {
    private ViewGroup mContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View result = inflater.inflate(R.layout.f_scaled, container, false);
        mContainer = (ViewGroup) result.findViewById(R.id.container);
        return result;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.scaled, menu);

        menu.findItem(R.id.action_scaleup).setIcon(FontIconDrawable.inflate(getResources(), R.xml.ic_scaleup));
        menu.findItem(R.id.action_scaledown).setIcon(FontIconDrawable.inflate(getResources(), R.xml.ic_scaledown));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_scaleup:
                scale(20);
                return true;

            case R.id.action_scaledown:
                scale(-20);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void scale(float value) {
        int w = mContainer.getWidth();

        int count = mContainer.getChildCount();
        for (int i = 0; i < count; ++i) {
            View view = mContainer.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;

                float newSize = tv.getTextSize() + value;

                if (newSize > w) {
                    newSize = w;
                } else if (newSize < 10) {
                    newSize = 10;
                }

                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, newSize);
            }
        }
    }
}
