package com.bjtu.zero.a2048;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

public class BlockView extends View {

    private Paint painter = new Paint();
    private Rect boundingRect = new Rect();
    private Block block;
    private boolean visible;

    public BlockView(Context context, Block block, int centerX, int centerY, int width, int height) {
        super(context);
        this.block = block;
        visible = true;
        painter.setTextSize(Setting.UI.BLOCK_FONT_SIZE);
        painter.setTextAlign(Paint.Align.CENTER);
        setGeometry(centerX, centerY, width, height);
    }

    void setGeometry(int centerX, int centerY, int width, int height) {
        width = (int) (width * Setting.UI.INNER_BLOCK_PERCENT);
        height = (int) (height * Setting.UI.INNER_BLOCK_PERCENT);
        boundingRect.set(centerY - width / 2, centerX - height / 2,
                centerY + width / 2, centerX + height / 2);
    }

    private RectF toRectF(Rect rect) {
        return new RectF(rect.left, rect.top, rect.right, rect.bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (visible) {
            painter.setColor(Setting.UI.BACKGROUND[block.getRank()]);
            canvas.drawRoundRect(
                    toRectF(boundingRect),
                    Setting.UI.BLOCK_ROUND_RAD,
                    Setting.UI.BLOCK_ROUND_RAD,
                    painter
            );
            painter.setColor(Setting.UI.FOREGROUND[block.getRank()]);
            Paint.FontMetricsInt fontMetrics = painter.getFontMetricsInt();
            int baseline = (boundingRect.bottom + boundingRect.top
                    - fontMetrics.bottom - fontMetrics.top) / 2;
            canvas.drawText(Setting.UI.STRING_LIST[block.getRank()],
                    boundingRect.centerX(), baseline, painter);
        }
    }

    public Block getBlock() {
        return block;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setVisible() {
        setVisible(true);
    }
}
