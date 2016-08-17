package com.bjtu.zero.a2048.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

import com.bjtu.zero.a2048.Setting;
import com.bjtu.zero.a2048.core.Block;

public class BlockView extends View {

    private Paint painter = new Paint();
    private Rect boundingRect = new Rect();
    private Block block;
    private boolean visible;
    private int fontSize = 75;
    private int wid;

    public BlockView(Context context, Block block, int centerX, int centerY, int width, int height) {
        super(context);
        this.block = block;
        visible = true;
        wid = width;
        int a;
        if(block == null)
            a = 0;
        else  a = block.getRank();
        refitText(String.valueOf(Setting.UI.SCORE_LIST[a]));
        painter.setTextSize(fontSize);
        painter.setTextAlign(Paint.Align.CENTER);

        setGeometry(centerX, centerY, width, height);
    }

    /**
     * Re size the font so the specified text fits in the text box * assuming
     * the text box is the specified width.
     */
    private void refitText(String text)
    {
        int textWidth = (int) (wid*1.5);
        Log.e("bbbbb","wid=" + String.valueOf(textWidth));
        Log.e("bbbbb","l=" + text.length());
        int minTextSize =  Setting.UI.BLOCK_FONT_SIZE_MIN;
        int maxTextSize = Setting.UI.BLOCK_FONT_SIZE;
        if (textWidth > 0)
        {
            int trySize = maxTextSize;
            while ((trySize > minTextSize) && trySize * text.length() >= textWidth)
            {
                trySize -= 1;
                if (trySize <= minTextSize)
                {
                    trySize = minTextSize;
                    break;
                }
            }
            fontSize = trySize;
            Log.e("bbbbb","size = " + String.valueOf(fontSize));
        }
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
        if (visible && block != null) {
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
            canvas.drawText(Setting.Runtime.STRING_LIST[block.getRank()],
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
