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
        mScreenSwitcher.switchScreen(screenFragment);
    }

    public ScreenSwitcher getScreenSwitcher() {
        return mScreenSwitcher;
    }

}
