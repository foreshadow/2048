package com.bjtu.zero.a2048.ui;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjtu.zero.a2048.Setting;
import com.bjtu.zero.a2048.core.Block;
import com.bjtu.zero.a2048.core.BlockChangeList;
import com.bjtu.zero.a2048.core.BlockChangeListItem;
import com.bjtu.zero.a2048.core.Board;
import com.bjtu.zero.a2048.core.GamePresenter;

public class GameLayout extends FrameLayout {

    int size;
    GamePresenter gamePresenter;
    BlockView[][] viewGrid;
    int blockWidth;
    int[][] centerX;
    int[][] centerY;

    public GameLayout(Context context, int width, GamePresenter gamePresenter,Score s) {
        this(context, width, gamePresenter, Setting.Game.DEFAULT_SIZE,s);
    }

    public GameLayout(Context context, int width, GamePresenter gamePresenter, int size,Score s) {
        super(context);
        this.size = size;
        this.gamePresenter = gamePresenter;
        game.s = s;

        LinearLayout ll_h1 = new LinearLayout(this.getContext());
        game.s.t1 = new TextView(this.getContext());
        game.s.t1.setText("分数");
        game.s.t1.setWidth(200);
        game.s.t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        game.s.score = new TextView(this.getContext());
        game.s.score.setText("0");
        game.s.score.setWidth(300);
        game.s.score.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        game.s.t2 = new TextView(this.getContext());
        game.s.t2.setText("最高分");
        game.s.t2.setWidth(300);
        game.s.t2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        game.s.highScore = new TextView(this.getContext());
        game.s.highScore.setText("0");
        game.s.highScore.setWidth(300);
        game.s.highScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        ll_h1.addView(game.s.t1);
        ll_h1.addView(game.s.score);
        ll_h1.addView(game.s.t2);
        ll_h1.addView(game.s.highScore);
        this.addView(ll_h1);


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
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                addView(new BlockView(getContext(), new Block(),
                        centerX[i][j], centerY[i][j], blockWidth, blockWidth));
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
                if (viewGrid[i][j] != null && viewGrid[i][j].getBlock() == block) {
                    return new Point(j, i); // deliberately transformation
                }
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

    public void playTranslation(BlockChangeList list, Animation.AnimationListener animationListener) {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(Setting.Runtime.ANIMATION_DURATION_MILLISECONDS);
        for (BlockChangeListItem item : list) {
            Point p = findCoordinate(item.block);
            int toX = centerX[item.toY][item.toX] - centerX[p.x][p.y];
            int toY = centerY[item.toY][item.toX] - centerY[p.x][p.y];
            Animation animation = new TranslateAnimation(0, toX, 0, toY);
            animation.setDuration(Setting.Runtime.ANIMATION_DURATION_MILLISECONDS);
            if (animationListener != null) {
                animation.setAnimationListener(animationListener);
                animationListener = null;
            }
            viewGrid[p.y][p.x].setAnimation(animation);
            animationSet.addAnimation(animation);
            Log.e("ANIMATION",
                    "Block r=" + item.block.getRank()
                            + ", lPos (" + p.y + ", " + p.x + ")"
                            + "->(" + item.toX + ", " + item.toY + "), "
                            + "flag=" + item.nextStatus.toString()
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
        animation.setDuration(Setting.Runtime.ANIMATION_DURATION_MILLISECONDS);
        viewGrid[i][j].startAnimation(animation);
        viewGrid[i][j].setVisible();
    }
}
