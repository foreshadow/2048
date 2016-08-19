package com.bjtu.zero.a2048.ui;

import android.content.Context;
import android.widget.Button;

import java.io.Serializable;
import java.util.Locale;

public class UndoButton extends Button implements Serializable {

    private static final String text = "Undo";
    private int size = 0;

    public UndoButton(Context context) {
        this(context, null);
    }

    public UndoButton(Context context, OnClickListener listener) {
        super(context);
        setOnClickListener(listener);
        update();
    }

    public void update(int n) {
        size = n;
        update();
    }

    private void update() {
        setText(String.format(Locale.CHINA, "%s (%d)", text, Math.max(size - 1, 0)));
    }
}
