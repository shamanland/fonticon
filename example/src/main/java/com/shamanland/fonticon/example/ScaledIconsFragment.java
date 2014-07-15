package com.shamanland.fonticon.example;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.shamanland.fonticon.FontIconDrawable;

public class ScaledIconsFragment extends ContentFragment {
    private static final int MIN_SIZE = 10;
    private static final int MAX_SIZE = 300;
    private static final int DEFAULT_SIZE = 48;

    private ViewGroup mContainer;
    private SeekBar mSeekBar;

    private int mTextSize;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View result = inflater.inflate(R.layout.f_scaled, container, false);

        mContainer = (ViewGroup) result.findViewById(R.id.container);

        mSeekBar = (SeekBar) result.findViewById(R.id.seek_bar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setTextSize(progress);

                if (fromUser) {
                    updateContent();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // empty
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // empty
            }
        });

        if (state != null) {
            setTextSize(state.getInt("text.size"));
        } else {
            setTextSize(DEFAULT_SIZE);
        }

        updateContent();

        return result;
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        state.putInt("text.size", mTextSize);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.scaled, menu);

        menu.findItem(R.id.action_scaleup).setIcon(FontIconDrawable.inflate(getContext(), R.xml.ic_ab_scaleup));
        menu.findItem(R.id.action_scaledown).setIcon(FontIconDrawable.inflate(getContext(), R.xml.ic_ab_scaledown));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_scaleup:
                setTextSize(mTextSize + 20);
                updateContent();
                return true;

            case R.id.action_scaledown:
                setTextSize(mTextSize - 20);
                updateContent();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setTextSize(int value) {
        mTextSize = Math.min(Math.max(MIN_SIZE, value), MAX_SIZE);
    }

    protected void updateContent() {
        mSeekBar.setProgress(mTextSize);

        int count = mContainer.getChildCount();
        for (int i = 0; i < count; ++i) {
            View view = mContainer.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mTextSize);
            }
        }
    }
}
