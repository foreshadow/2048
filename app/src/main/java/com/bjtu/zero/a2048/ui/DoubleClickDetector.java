package com.bjtu.zero.a2048.ui;

import java.io.Serializable;

public class DoubleClickDetector implements Serializable {

    private final long intervalMillis;
    private long lastClick;
    private OnClickListener onClickListener;

    public DoubleClickDetector(long intervalMillis, OnClickListener onClickListener) {
        this.intervalMillis = intervalMillis;
        this.lastClick = System.currentTimeMillis();
        this.onClickListener = onClickListener;
    }

    public DoubleClickDetector(long intervalMillis) {
        this(intervalMillis, null);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void onClick() {
        if (onClickListener != null) {
            if (System.currentTimeMillis() > lastClick + intervalMillis) {
                onClickListener.onSingleClick();
            } else {
                onClickListener.onDoubleClick();
            }
        }
        lastClick = System.currentTimeMillis();
    }

    public interface OnClickListener {

        void onSingleClick();

        void onDoubleClick();
    }
}
