package com.bjtu.zero.a2048.ui;

import android.content.Context;
import android.widget.Button;

public class AutoButton extends Button {

    private static final String text = new String("Auto");
    private int ops;

    public AutoButton(Context context, int times) {
        this(context, times, null);
    }

    public AutoButton(Context context, int times, OnClickListener listener) {
        super(context);
        ops = times;
        setOnClickListener(listener);
    }
}
