package com.github.androidscreenswitcher;

import android.content.Context;
import android.support.v4.app.Fragment;

public abstract class ScreenFragment extends Fragment {

    public ScreenActivity getScreenActivity() {
        return (ScreenActivity) getActivity();
    }

    public ScreenSwitcher getScreenSwitcher() {
        return getScreenActivity().getScreenSwitcher();
    }

    public String getTitle(Context context) {
        return null;
    }

}
