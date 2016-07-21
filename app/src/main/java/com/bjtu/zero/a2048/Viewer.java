package com.bjtu.zero.a2048;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.widget.FrameLayout;

public class Viewer extends View {

    Paint p;

    public Viewer(Context context) {
        super(context);
        setLayoutParams(new FrameLayout.LayoutParams(100, 100));
        p = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p.setARGB(255, 255, 255, 255);
        canvas.drawLine(0, 0, 100, 100, p);
        canvas.drawRect(0, 0, 0, 0, p);
    }
}
