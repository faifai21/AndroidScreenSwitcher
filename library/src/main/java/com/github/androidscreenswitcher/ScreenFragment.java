package com.github.androidscreenswitcher;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class ScreenFragment extends Fragment {

    public ScreenActivity getScreenActivity() {
        return (ScreenActivity) getActivity();
    }

    public ScreenSwitcher getScreenSwitcher() {
        return getScreenActivity().getScreenSwitcher();
    }

    public String getTitle(Context context) {
        return null;
    }

    public int getLayoutResource() {
        return 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutResource() > 0) {
            View view = inflater.inflate(getLayoutResource(), container, false);
            tryToInject(view);
            return view;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void tryToInject(View view) {
        try {
            Method method = Class.forName("butterknife.ButterKnife").getMethod("inject", Object.class, View.class);
            method.invoke(null, this, view);
        } catch (NoSuchMethodException | ClassNotFoundException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
