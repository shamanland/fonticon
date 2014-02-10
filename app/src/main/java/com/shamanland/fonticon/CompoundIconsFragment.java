package com.shamanland.fonticon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CompoundIconsFragment extends ContentFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View result = inflater.inflate(R.layout.f_compound, container, false);
        return result;
    }
}
