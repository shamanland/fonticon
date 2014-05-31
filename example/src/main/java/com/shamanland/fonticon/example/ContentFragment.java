package com.shamanland.fonticon.example;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class ContentFragment extends Fragment {
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setHasOptionsMenu(true);
    }
}
