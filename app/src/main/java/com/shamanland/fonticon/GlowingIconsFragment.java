package com.shamanland.fonticon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class GlowingIconsFragment extends ContentFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View result = inflater.inflate(R.layout.f_glowing, container, false);
        return result;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.glowing, menu);

        menu.findItem(R.id.action_camera).setIcon(FontIconDrawable.inflate(getResources(), R.xml.ic_camera));
        menu.findItem(R.id.action_settings).setIcon(FontIconDrawable.inflate(getResources(), R.xml.ic_settings));
    }
}
