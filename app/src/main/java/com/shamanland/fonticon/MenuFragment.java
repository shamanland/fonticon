package com.shamanland.fonticon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MenuFragment extends Fragment implements View.OnClickListener {
    private int mSelectedMenuId;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        if (state != null) {
            mSelectedMenuId = state.getInt("selected.menu.id", 0);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        state.putInt("selected.menu.id", mSelectedMenuId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View root = inflater.inflate(R.layout.f_menu, container, false);

        if (root instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) root;
            int count = group.getChildCount();

            for (int i = 0; i < count; ++i) {
                View view = group.getChildAt(i);
                if (view != null) {
                    view.setOnClickListener(this);
                }
            }

            if (mSelectedMenuId != 0) {
                onClick(root.findViewById(mSelectedMenuId));
            }
        }

        return root;
    }

    @Override
    public void onClick(View v) {
        FragmentActivity activity = getActivity();
        if (activity instanceof MenuListener) {
            String menuText = null;

            if (v instanceof TextView) {
                CharSequence text = ((TextView) v).getText();
                if (text != null) {
                    menuText = text.toString();
                }
            }

            ((MenuListener) activity).onMenuClicked(v.getId(), menuText, mSelectedMenuId != v.getId());
            mSelectedMenuId = v.getId();
        }
    }
}
