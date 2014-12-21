package com.github.androidscreenswitcher.demo.fragment;

import android.content.Context;

import com.github.androidscreenswitcher.ScreenFragment;
import com.github.androidscreenswitcher.demo.R;

import butterknife.OnClick;

public class HomeFragment extends ScreenFragment {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    public String getTitle(Context context) {
        return context.getString(R.string.app_name);
    }

    @OnClick(R.id.replace)
    public void onReplaceClick() {
        getScreenSwitcher().pushScreen(TextFragment.newInstance("Test"));
    }
}
