package com.shamanland.fonticon.example;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class ContentFragment extends Fragment {
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setHasOptionsMenu(true);
    }
}
