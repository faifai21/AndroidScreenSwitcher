package com.github.androidscreenswitcher;

import android.app.Activity;
import android.os.Bundle;

public class ScreenActivity extends Activity {

    private ScreenSwitcher mScreenSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        mScreenSwitcher = new ScreenSwitcher(this);
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

}
