package com.github.androidscreenswitcher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

public abstract class ScreenActivity extends AppCompatActivity implements ScreenSwitcher.OnScreenSwitchListener {

    private static final String SAVE_STATE_TITLE = "ScreenActivity.SAVE_STATE_TITLE";

    private ScreenSwitcher mScreenSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            setTitle(savedInstanceState.getCharSequence(SAVE_STATE_TITLE));
        }

        mScreenSwitcher = new ScreenSwitcher(this, getContainerId());
        mScreenSwitcher.addOnScreenSwitchListener(this);
    }

    public abstract int getContainerId();

    public void switchScreen(ScreenFragment screenFragment) {
        switchScreen(screenFragment, false, false);
    }

    public void switchScreen(ScreenFragment screenFragment, boolean clearBackStack) {
        switchScreen(screenFragment, clearBackStack, false);
    }

    public void switchScreen(ScreenFragment screenFragment, boolean clearBackStack, boolean addToBackStack) {
        switchScreen(screenFragment, clearBackStack, addToBackStack, false);
    }

    public void pushScreen(ScreenFragment screenFragment) {
        pushScreen(screenFragment, false);
    }

    public void pushScreen(ScreenFragment screenFragment, boolean isCheckpoint) {
        switchScreen(screenFragment, false, true, isCheckpoint);
    }

    public void switchScreen(ScreenFragment screenFragment, boolean clearBackStack, boolean addToBackStack, boolean isCheckpoint) {
        mScreenSwitcher.addToStack(addToBackStack)
                .clearStack(clearBackStack)
                .isCheckpoint(isCheckpoint)
                .commit(screenFragment);
    }

    public boolean popTillCheckpoint(){
        return mScreenSwitcher.popTillCheckpoint();
    }

    public void pop(int count){
        mScreenSwitcher.pop(count);
    }

    public void pop(){
        mScreenSwitcher.pop();
    }

    protected Fragment getVisibleFragment(){
        return mScreenSwitcher.getVisibleFragment();
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
        else
            setTitle(getDefaultTitle());
    }

    public abstract String getDefaultTitle();

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
