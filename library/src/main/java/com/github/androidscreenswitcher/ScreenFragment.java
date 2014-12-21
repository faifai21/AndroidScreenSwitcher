package com.github.androidscreenswitcher;

import android.app.Fragment;

public class ScreenFragment extends Fragment {

    public ScreenActivity getScreenActivity() {
        return (ScreenActivity) getActivity();
    }

    public ScreenSwitcher getScreenSwitcher() {
        return getScreenActivity().getScreenSwitcher();
    }

}
