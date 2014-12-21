package com.github.androidscreenswitcher;

import android.app.Activity;
import android.app.FragmentManager;

public class ScreenSwitcher {

    private final FragmentManager mFragmentManager;
    private final int mContainerID;

    public ScreenSwitcher(Activity activity) {
        this(activity, R.id.screens_container);
    }

    public ScreenSwitcher(Activity activity, int containerID) {
        mFragmentManager = activity.getFragmentManager();
        mContainerID = containerID;
    }

    public void switchScreen(ScreenFragment screenFragment) {
        mFragmentManager
                .beginTransaction()
                .replace(mContainerID, screenFragment)
                .commit();
    }

}
