package com.bjtu.zero.a2048.ui;

public class DoubleClickDetector {

    private long intervalMillis;
    private long lastClick;
    private ClickListener clickListener;

    public DoubleClickDetector(long intervalMillis, ClickListener clickListener) {
        this.intervalMillis = intervalMillis;
        this.lastClick = System.currentTimeMillis();
        this.clickListener = clickListener;
    }

    public DoubleClickDetector(long intervalMillis) {
        this(intervalMillis, null);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void onClick() {
        if (clickListener != null) {
            if (System.currentTimeMillis() > lastClick + intervalMillis) {
                clickListener.onFirstClick();
            } else {
                clickListener.onSecondClick();
            }
        }
        lastClick = System.currentTimeMillis();
    }

    public interface ClickListener {

        void onFirstClick();

        void onSecondClick();
    }
}
