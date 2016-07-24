package com.bjtu.zero.a2048;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.animation.Animation;

public class BlockView extends View {

    private Paint painter = new Paint();
    private Rect boundingRect = new Rect();
    private Block block;

    public BlockView(Context context, Block block, int centerX, int centerY, int width, int height) {
        super(context);
        this.block = block;
        painter.setTextSize(75);
        painter.setTextAlign(Paint.Align.CENTER);
        setGeometry(centerX, centerY, width, height);
    }

    void setGeometry(int centerX, int centerY, int width, int height) {
        width = (int) (width * Settings.UI.INNER_BLOCK_PERCENT);
        height = (int) (height * Settings.UI.INNER_BLOCK_PERCENT);
        boundingRect.set(centerY - width / 2, centerX - height / 2,
                centerY + width / 2, centerX + height / 2);
    }

    void animate(Animation animation) {
        startAnimation(animation);
    }

    private RectF toRectF(Rect rect) {
        return new RectF(rect.left, rect.top, rect.right, rect.bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        painter.setColor(Settings.UI.BACKGROUND[block.getRank()]);
        canvas.drawRoundRect(toRectF(boundingRect), 20, 20, painter);
        painter.setColor(Settings.UI.FOREGROUND[block.getRank()]);
        Paint.FontMetricsInt fontMetrics = painter.getFontMetricsInt();
        int baseline = (boundingRect.bottom + boundingRect.top
                - fontMetrics.bottom - fontMetrics.top) / 2;
        canvas.drawText(Settings.UI.STRING_LIST[block.getRank()],
                boundingRect.centerX(), baseline, painter);
    }

    public Block getBlock() {
        return block;
    }
}
