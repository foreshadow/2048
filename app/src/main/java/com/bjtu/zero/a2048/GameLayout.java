package com.bjtu.zero.a2048;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

public class GameLayout extends FrameLayout {

    int size;
    Game game;
    BlockView[][] viewGrid;
    int blockWidth;
    int[][] centerX;
    int[][] centerY;

    public GameLayout(Context context, int width, Game game) {
        this(context, width, game, Setting.Game.DEFAULT_SIZE);
    }

    public GameLayout(Context context, int width, Game game, int size) {
        super(context);
        this.size = size;
        this.game = game;
        viewGrid = new BlockView[size][size];
        setLayoutParams(new LayoutParams(width, width));
        int boarder = (int) (width * Setting.UI.BOARD_BOARDER_PERCENT);
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

    public void setBlock(int i, int j, Block block, boolean visible) {
        if (viewGrid[i][j] != null) {
            removeView(viewGrid[i][j]);
        }
        viewGrid[i][j] = new BlockView(getContext(), block,
                centerX[i][j], centerY[i][j], blockWidth, blockWidth);
        if (!visible) {
            viewGrid[i][j].setVisible(false);
        }
        addView(viewGrid[i][j]);
    }

    public void setBlock2(int i, int j) {
        if (viewGrid[i][j] != null) {
            Block block = viewGrid[i][j].getBlock();
            removeView(viewGrid[i][j]);
            viewGrid[i][j] = new BlockView(getContext(), block,
                    centerX[i][j], centerY[i][j], blockWidth, blockWidth);
            addView(viewGrid[i][j]);
        }
    }

    public void clearBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (viewGrid[i][j] != null) {
                    removeView(viewGrid[i][j]);
                }
            }
        }
    }

    public void setBoard(Board board) {
        if (size != board.getSize()) {
            throw new AssertionError();
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                setBlock(i, j, board.getData()[i][j], true);
            }
        }
    }

    public void setBoard2() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                setBlock2(i, j);
            }
        }
    }

    public Point findCoordinate(Block block) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (viewGrid[i][j].getBlock() == block)
                    return new Point(j, i); // deliberately transformation
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

    public void playTransition(BlockChangeList list, Animation.AnimationListener animationListener) {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(Setting.UI.ANIMATION_DURATION_MILLISECONDS);
        for (BlockChangeListItem item : list) {
            Point fromPos = findCoordinate(item.block);
            int toX = centerX[item.toY][item.toX] - centerX[fromPos.x][fromPos.y];
            int toY = centerY[item.toY][item.toX] - centerY[fromPos.x][fromPos.y];
            Point p = findCoordinate(item.block);
            Animation animation = new TranslateAnimation(0, toX, 0, toY);
            animation.setDuration(Setting.UI.ANIMATION_DURATION_MILLISECONDS);
            if (animationListener != null) {
                animation.setAnimationListener(animationListener);
                animationListener = null;
            }
            viewGrid[p.y][p.x].setAnimation(animation);
            animationSet.addAnimation(animation);
            Log.e("ANIMATION",
                    "Block rank = " + item.block.getRank()
                            + ", from lPos (" + fromPos.y + ", " + fromPos.x + "), "
                            + "to lPos (" + item.toX + ", " + item.toY + "), "
                            + "flag = " + item.nextStatus.toString()
            );
        }
        animationSet.start();
    }

    public void playSpawn(int i, int j, Block block) {
        setBlock(i, j, block, false);
        Point p = findCoordinate(block);
        Animation animation = new ScaleAnimation(
                Setting.UI.BLOCK_SPAWN_SCALE_FROM_PERCENT, 1f,
                Setting.UI.BLOCK_SPAWN_SCALE_FROM_PERCENT, 1f,
                Animation.ABSOLUTE, centerX[p.x][p.y],
                Animation.ABSOLUTE, centerY[p.x][p.y]
        );
        animation.setDuration(Setting.UI.ANIMATION_DURATION_MILLISECONDS);
        viewGrid[i][j].startAnimation(animation);
        viewGrid[i][j].setVisible();
    }
}
