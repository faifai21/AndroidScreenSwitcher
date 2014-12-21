package com.github.androidscreenswitcher.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.androidscreenswitcher.ScreenFragment;
import com.github.androidscreenswitcher.demo.R;

public class TextFragment extends ScreenFragment {

    private static final String PARAM_TEXT = "TextFragment.PARAM_TEXT";

    public static TextFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString(PARAM_TEXT, text);

        TextFragment textFragment = new TextFragment();
        textFragment.setArguments(args);
        return textFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String paramText = getArguments().getString(PARAM_TEXT);

        TextView text = (TextView) view.findViewById(R.id.text);

        text.setText(paramText);
    }
}
