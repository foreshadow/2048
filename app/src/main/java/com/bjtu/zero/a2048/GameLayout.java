package com.bjtu.zero.a2048;

import android.content.Context;
import android.graphics.Point;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

public class GameLayout extends FrameLayout {

    int size;
    BlockView[][] viewGrid;
    int blockWidth;
    int[][] centerX;
    int[][] centerY;

    public GameLayout(Context context, int width) {
        this(context, width, Settings.DEFAULT_SIZE);
    }

    public GameLayout(Context context, int width, int size) {
        super(context);
        this.size = size;
        viewGrid = new BlockView[size][size];
        setLayoutParams(new LayoutParams(width, width));
        int boarder = (int) (width * Settings.BOARD_BOARDER_PERCENT);
        int boardWidth = width - boarder * 2;
        blockWidth = boardWidth / size;
        centerX = new int[size][size];
        centerY = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                centerX[i][j] = boarder + blockWidth * i + blockWidth / 2;
                centerY[i][j] = boarder + blockWidth * j + blockWidth / 2;
            }
        }
    }

    public void setBlock(int i, int j, Block block) {
        if (viewGrid[i][j] != null) {
            removeView(viewGrid[i][j]);
        }
        viewGrid[i][j] = new BlockView(getContext(), block,
                centerX[i][j], centerY[i][j], blockWidth, blockWidth);
        addView(viewGrid[i][j]);
    }

    public void setBoard(Board board) {
        if (size != board.getSize()) {
            throw new AssertionError();
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                setBlock(i, j, board.getData()[i][j]);
            }
        }
    }

    public Point findCoordinate(Block block) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (viewGrid[i][j].getBlock() == block)
                    return new Point(i, j);
            }
        }
        return null;
    }

    public void refresh() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (viewGrid[i][j] != null) {
                    viewGrid[i][j].setGeometry(centerX[i][j], centerY[i][j], blockWidth, blockWidth);
                }
            }
        }
    }

    public void playTransition(BlockChangeList list) {
        AnimationSet set = new AnimationSet(true);
        for (BlockChangeListItem item : list) {
            Point fromPos = findCoordinate(item.block);
            int fromX = centerX[fromPos.x][fromPos.y];
            int fromY = centerY[fromPos.x][fromPos.y];
            int toX = centerX[item.toX][item.toY];
            int toY = centerY[item.toX][item.toY];
            set.addAnimation(new TranslateAnimation(fromX, toX, fromY, toY));
        }
        startAnimation(set);
    }

    public void playSpawn(int i, int j, Block block) {
        setBlock(i, j, block);
        viewGrid[i][j].animate(new ScaleAnimation(blockWidth / 2, blockWidth / 2, blockWidth, blockWidth));
    }
}
