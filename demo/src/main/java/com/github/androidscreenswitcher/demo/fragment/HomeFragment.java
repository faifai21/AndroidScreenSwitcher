package com.github.androidscreenswitcher.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.androidscreenswitcher.ScreenFragment;
import com.github.androidscreenswitcher.demo.MainActivity;
import com.github.androidscreenswitcher.demo.R;

public class HomeFragment extends ScreenFragment {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.replace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getScreenSwitcher().switchScreen(TextFragment.newInstance("Test"));
            }
        });
    }

}
