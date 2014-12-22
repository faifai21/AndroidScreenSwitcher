package com.github.androidscreenswitcher;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ScreenSwitcher {

    private final FragmentManager mFragmentManager;
    private final int mContainerID;
    private List<OnScreenSwitchListener> mOnScreenSwitchListeners = new ArrayList<>();

    public static interface OnScreenSwitchListener {
        public void onScreenSwitched(ScreenFragment screenFragment);
    }

    public ScreenSwitcher(ActionBarActivity activity) {
        this(activity, R.id.screens_container);
    }

    public ScreenSwitcher(ActionBarActivity activity, int containerID) {
        mFragmentManager = activity.getSupportFragmentManager();
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
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        String tag = UUID.randomUUID().toString();

        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }

        notifyFragmentSwitch(fragment);

        transaction.replace(mContainerID, fragment, tag);

        transaction.commit();
    }

    public void clearBackStack() {
        for (int i = 0; i < mFragmentManager.getBackStackEntryCount(); ++i) {
            mFragmentManager.popBackStack();
        }
    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = mFragmentManager;
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    public void addOnScreenSwitchListener(OnScreenSwitchListener onScreenSwitchListener) {
        mOnScreenSwitchListeners.add(onScreenSwitchListener);
    }

    public void onBackPressed() {
        Fragment fragment = getVisibleFragment();
        if (fragment != null && fragment instanceof ScreenFragment) {
            notifyFragmentSwitch((ScreenFragment) fragment);
        }
    }

    private void notifyFragmentSwitch(ScreenFragment screenFragment) {
        for (OnScreenSwitchListener onScreenSwitchListener : mOnScreenSwitchListeners) {
            onScreenSwitchListener.onScreenSwitched(screenFragment);
        }
    }
}
