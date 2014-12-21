package com.github.androidscreenswitcher;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;

public class ScreenActivity extends ActionBarActivity implements ScreenSwitcher.OnScreenSwitchListener {

    private static final String SAVE_STATE_TITLE = "ScreenActivity.SAVE_STATE_TITLE";

    private ScreenSwitcher mScreenSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            setTitle(savedInstanceState.getCharSequence(SAVE_STATE_TITLE));
        }

        mScreenSwitcher = new ScreenSwitcher(this);
        mScreenSwitcher.addOnScreenSwitchListener(this);
    }

    public void switchScreen(ScreenFragment screenFragment) {
        mScreenSwitcher.changeScreen(screenFragment, false, false);
    }

    public void switchScreen(ScreenFragment screenFragment, boolean clearBackStack) {
        mScreenSwitcher.changeScreen(screenFragment, clearBackStack, false);
    }

    public void pushScreen(ScreenFragment screenFragment) {
        mScreenSwitcher.pushScreen(screenFragment);
    }

    public void pushScreen(ScreenFragment screenFragment, boolean clearBackStack, boolean addToBackStack) {
        mScreenSwitcher.changeScreen(screenFragment, clearBackStack, addToBackStack);
    }

    public ScreenSwitcher getScreenSwitcher() {
        return mScreenSwitcher;
    }

    @Override
    public void onScreenSwitched(ScreenFragment screenFragment) {
        String title = screenFragment.getTitle(this);

        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mScreenSwitcher.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(SAVE_STATE_TITLE, getTitle());
    }
}
