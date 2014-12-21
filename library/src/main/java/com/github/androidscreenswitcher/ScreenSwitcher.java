package com.github.androidscreenswitcher;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import java.util.UUID;

public class ScreenSwitcher {

    private final FragmentManager mFragmentManager;
    private final int mContainerID;
    private OnScreenSwitchListener mOnScreenSwitchListener;

    public static interface OnScreenSwitchListener {
        public void onScreenSwitched(ScreenFragment screenFragment);
    }

    public ScreenSwitcher(Activity activity) {
        this(activity, R.id.screens_container);
    }

    public ScreenSwitcher(Activity activity, int containerID) {
        mFragmentManager = activity.getFragmentManager();
        mContainerID = containerID;
    }

    public void switchScreen(ScreenFragment screenFragment) {
        changeScreen(screenFragment, false, false);
    }

    public void switchScreen(ScreenFragment screenFragment, boolean clearBackStack) {
        changeScreen(screenFragment, clearBackStack, false);
    }

    public void pushScreen(ScreenFragment screenFragment) {
        changeScreen(screenFragment, false, true);
    }

    protected void changeScreen(ScreenFragment fragment, boolean clearBackStack, boolean addToBackStack) {
        if (clearBackStack) {
            clearBackStack();
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        String tag = UUID.randomUUID().toString();

        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }

        if (mOnScreenSwitchListener != null) {
            mOnScreenSwitchListener.onScreenSwitched(fragment);
        }

        transaction.replace(mContainerID, fragment, tag);

        transaction.commit();
    }

    public void clearBackStack() {
        for (int i = 0; i < mFragmentManager.getBackStackEntryCount(); ++i) {
            mFragmentManager.popBackStack();
        }
    }

    public void setOnScreenSwitchListener(OnScreenSwitchListener onScreenSwitchListener) {
        mOnScreenSwitchListener = onScreenSwitchListener;
    }
}
