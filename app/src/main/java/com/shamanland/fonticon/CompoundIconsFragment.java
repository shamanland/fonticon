package com.shamanland.fonticon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class CompoundIconsFragment extends ContentFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View result = inflater.inflate(R.layout.f_compound, container, false);
        return result;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.compound, menu);

        menu.findItem(R.id.action_like).setIcon(FontIconDrawable.inflate(getResources(), R.xml.ic_like));
        menu.findItem(R.id.action_group).setIcon(FontIconDrawable.inflate(getResources(), R.xml.ic_group));
    }
}
