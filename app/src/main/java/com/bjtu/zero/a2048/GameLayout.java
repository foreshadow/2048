package com.bjtu.zero.a2048;

import android.content.Context;
import android.widget.FrameLayout;

public class GameLayout extends FrameLayout {
    public GameLayout(Context context) {
        super(context);
        addView(new Viewer(getContext()));
    }
}
