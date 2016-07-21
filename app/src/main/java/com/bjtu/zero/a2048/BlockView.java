package com.bjtu.zero.a2048;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.FrameLayout;

public class BlockView extends View {

    Paint p;

    public BlockView(Context context) {
        super(context);
        setLayoutParams(new FrameLayout.LayoutParams(1000, 1000));
        p = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p.setColor(Color.BLACK);
        canvas.drawRect(0, 0, 200, 200, p);
        p.setColor(Color.WHITE);
        p.setTextSize(50);
        canvas.drawText("2048", 25, 100, p);
    }
}
