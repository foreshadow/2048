package com.bjtu.zero.a2048.core;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.animation.Animation;

import com.bjtu.zero.a2048.Setting;
import com.bjtu.zero.a2048.ui.GameLayout;

import java.util.ArrayList;
import java.util.Random;

public class GamePresenter {

    private int size;
    private boolean animationInProgress;
    private GameLayout layout;
    private GameModel model;
    private int[][] increment = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private Sound mSound ;

    public GamePresenter() {
        this(Setting.Game.DEFAULT_SIZE);
    }

    public GamePresenter(int size) {
        this.size = size;
        model = new GameModel(Setting.Game.HISTORY_SIZE);
        animationInProgress = false;
        history = new LinkedList<>();
        mSound =new Sound();
    }

    public void reset() {
        model.clear();
        if (layout != null) {
            layout.setBoard(new Board());
            start();
        }
    }

    public void start() {
        model.append(new Status(size));
        if (layout != null) {
            layout.setBoard(model.lastBoard());
        }
        spawnBlock();
        spawnBlock();
    }

    public void setLayout(GameLayout layout) {
        this.layout = layout;
        if (layout != null) {
            layout.refresh();
        }
    }

    //设置是否播放音效
    public void setSound(boolean haveSound){
        mSound.setSound(haveSound);
    }

    public void loadSound(Context context){
        mSound.load(context);
    }

    private boolean canMove(int direction) {
        if (animationInProgress) return false;
        Status nowStatus = model.lastStatus();
        //某个rank非0的block的next位置为rank==0的block
        //某个rank非0的block的next位置为rank相同的block
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Block nowBlock = nowStatus.getBoard().getData()[i][j];
                if (nowBlock.isEmpty()) continue;
                int x = i + increment[direction][0];
                int y = j + increment[direction][1];
                if (x < 0 || x >= size || y < 0 || y >= size) continue;
                Block nextBlock = nowStatus.getBoard().getData()[x][y];
                if (nextBlock.isEmpty() || nowBlock.isSameRank(nextBlock))
                    return true;
            }
        }
        return false;
    }

    public void slideLeft() {
        if (!canMove(2)) return;
        Status nextStatus = model.lastStatus().clone();
        Block[][] nextBlock = nextStatus.getBoard().getData();
        Block[][] preBlock = model.lastBlocks();
        BlockChangeList changeList = new BlockChangeList();
        int maxRank =1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!nextBlock[i][j].isEmpty()) {
                    for (int k = j + 1; k < size; k++) {
                        if (nextBlock[i][j].isSameRank(nextBlock[i][k])) {
                            nextBlock[i][j].increase();
                            maxRank = Math.max(maxRank,nextBlock[i][j].getRank());
                            nextBlock[i][k].setRank(0);
                            nextStatus.addScore(Setting.UI.SCORE_LIST[nextBlock[i][j].getRank()]);
                            int toY = j - 1;
                            while (toY >= 0 && nextBlock[i][toY].isEmpty()) {
                                toY--;
                            }
                            toY++;
                            nextBlock[i][j].swapRank(nextBlock[i][toY]);
                            //把已经increase且位置没变的blcok也add了
                            changeList.add(new BlockChangeListItem(preBlock[i][j], i, toY, BlockChangeListItem.NextStatus.INCREASE));
                            changeList.add(new BlockChangeListItem(preBlock[i][k], i, toY, BlockChangeListItem.NextStatus.DESTROY));
                            break;
                        } else if (!nextBlock[i][k].isEmpty()) break;
                    }
                    if (nextBlock[i][j].isSameRank(preBlock[i][j])) {
                        int toY = j - 1;
                        while (toY >= 0 && nextBlock[i][toY].isEmpty()) {
                            toY--;
                        }
                        toY++;
                        nextBlock[i][j].swapRank(nextBlock[i][toY]);
                        changeList.add(new BlockChangeListItem(preBlock[i][j], i, toY, BlockChangeListItem.NextStatus.MAINTAIN));
                    }
                }
            }
        }
        nextStatus.setAdds(nextStatus.getScore()- getHistory().getLast().getScore());
        mSound.playProcess(maxRank);
        validOperation(changeList, nextStatus);
    }

    public void slideRight() {
        if (!canMove(3)) return;
        Status nextStatus = model.lastStatus().clone();
        Block[][] nextBlock = nextStatus.getBoard().getData();
        Block[][] preBlock = model.lastBlocks();
        BlockChangeList changeList = new BlockChangeList();
        int maxRank =1;
        for (int i = 0; i < size; i++) {
            for (int j = size - 1; j >= 0; j--) {
                if (!nextBlock[i][j].isEmpty()) {
                    for (int k = j - 1; k >= 0; k--) {
                        if (nextBlock[i][j].isSameRank(nextBlock[i][k])) {
                            nextBlock[i][j].increase();
                            maxRank = Math.max(maxRank,nextBlock[i][j].getRank());
                            nextBlock[i][k].setRank(0);
                            nextStatus.addScore(Setting.UI.SCORE_LIST[nextBlock[i][j].getRank()]);
                            int toY = j + 1;
                            while (toY < size && nextBlock[i][toY].isEmpty()) {
                                toY++;
                            }
                            toY--;
                            nextBlock[i][j].swapRank(nextBlock[i][toY]);
                            //把已经increase且位置没变的blcok也add了
                            changeList.add(new BlockChangeListItem(preBlock[i][j], i, toY, BlockChangeListItem.NextStatus.INCREASE));
                            changeList.add(new BlockChangeListItem(preBlock[i][k], i, toY, BlockChangeListItem.NextStatus.DESTROY));
                            break;
                        } else if (!nextBlock[i][k].isEmpty()) break;
                    }
                    if (nextBlock[i][j].isSameRank(preBlock[i][j])) {
                        int toY = j + 1;
                        while (toY < size && nextBlock[i][toY].isEmpty()) {
                            toY++;
                        }
                        toY--;
                        nextBlock[i][j].swapRank(nextBlock[i][toY]);
                        changeList.add(new BlockChangeListItem(preBlock[i][j], i, toY, BlockChangeListItem.NextStatus.MAINTAIN));
                    }
                }
            }
        }
        mSound.playProcess(maxRank);
        validOperation(changeList, nextStatus);
    }

    public void slideUp() {
        if (!canMove(0)) return;
        Status nextStatus = model.lastStatus().clone();
        Block[][] nextBlock = nextStatus.getBoard().getData();
        Block[][] preBlock = model.lastBlocks();
        BlockChangeList changeList = new BlockChangeList();
        int maxRank =1;
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                if (!nextBlock[i][j].isEmpty()) {
                    for (int k = i + 1; k < size; k++) {
                        if (nextBlock[i][j].isSameRank(nextBlock[k][j])) {
                            nextBlock[i][j].increase();
                            maxRank = Math.max(maxRank,nextBlock[i][j].getRank());
                            nextBlock[k][j].setRank(0);
                            nextStatus.addScore(Setting.UI.SCORE_LIST[nextBlock[i][j].getRank()]);
                            int toX = i - 1;
                            while (toX >= 0 && nextBlock[toX][j].isEmpty()) {
                                toX--;
                            }
                            toX++;
                            nextBlock[i][j].swapRank(nextBlock[toX][j]);
                            //把已经increase且位置没变的blcok也add了
                            changeList.add(new BlockChangeListItem(preBlock[i][j], toX, j, BlockChangeListItem.NextStatus.INCREASE));
                            changeList.add(new BlockChangeListItem(preBlock[k][j], toX, j, BlockChangeListItem.NextStatus.DESTROY));
                            break;
                        } else if (!nextBlock[k][j].isEmpty()) break;
                    }
                    if (nextBlock[i][j].isSameRank(preBlock[i][j])) {
                        int toX = i - 1;
                        while (toX >= 0 && nextBlock[toX][j].isEmpty()) {
                            toX--;
                        }
                        toX++;
                        nextBlock[i][j].swapRank(nextBlock[toX][j]);
                        changeList.add(new BlockChangeListItem(preBlock[i][j], toX, j, BlockChangeListItem.NextStatus.MAINTAIN));
                    }
                }
            }
        }
        mSound.playProcess(maxRank);
        validOperation(changeList, nextStatus);
    }

    public void slideDown() {
        if (!canMove(1)) return;
        Status nextStatus = model.lastStatus().clone();
        Block[][] nextBlock = nextStatus.getBoard().getData();
        Block[][] preBlock = model.lastBlocks();
        BlockChangeList changeList = new BlockChangeList();
        int maxRank =1;
        for (int j = 0; j < size; j++) {
            for (int i = size - 1; i >= 0; i--) {
                if (!nextBlock[i][j].isEmpty()) {
                    for (int k = i - 1; k >= 0; k--) {
                        if (nextBlock[i][j].isSameRank(nextBlock[k][j])) {
                            nextBlock[i][j].increase();
                            maxRank = Math.max(maxRank,nextBlock[i][j].getRank());
                            nextBlock[k][j].setRank(0);
                            nextStatus.addScore(Setting.UI.SCORE_LIST[nextBlock[i][j].getRank()]);
                            int toX = i + 1;
                            while (toX < size && nextBlock[toX][j].isEmpty()) {
                                toX++;
                            }
                            toX--;
                            nextBlock[i][j].swapRank(nextBlock[toX][j]);
                            //把已经increase且位置没变的blcok也add了
                            changeList.add(new BlockChangeListItem(preBlock[i][j], toX, j, BlockChangeListItem.NextStatus.INCREASE));
                            changeList.add(new BlockChangeListItem(preBlock[k][j], toX, j, BlockChangeListItem.NextStatus.DESTROY));
                            break;
                        } else if (!nextBlock[k][j].isEmpty()) break;
                    }
                    if (nextBlock[i][j].isSameRank(preBlock[i][j])) {
                        int toX = i + 1;
                        while (toX < size && nextBlock[toX][j].isEmpty()) {
                            toX++;
                        }
                        toX--;
                        nextBlock[i][j].swapRank(nextBlock[toX][j]);
                        changeList.add(new BlockChangeListItem(preBlock[i][j], toX, j, BlockChangeListItem.NextStatus.MAINTAIN));
                    }
                }
            }
        }
        mSound.playProcess(maxRank);
        validOperation(changeList, nextStatus);
    }

    private void validOperation(BlockChangeList changeList, Status status) {
        if (layout != null) {
            layout.playTranslation(changeList, new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    animationInProgress = true;
                    layout.clearBoard();
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.e("ANIMATION", "onEnd");
                    layout.setBoard(model.lastBoard());
                    spawnBlock();
                    animationInProgress = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        model.append(status);
        if (layout != null) {
            layout.setBoard2(); // critical !
        } else {
            spawnBlock();
        }
    }

    public void spawnBlock() {
        if (isGameOver()) {
            throw new AssertionError();
        }
        int rank = 1;
        if (Math.random() < Setting.Game.RANK_2_PROBABILITY) {
            rank = 2;
        }
        ArrayList<Point> emptyBlocks = model.lastBoard().emptyBlocks();
        final Point p = emptyBlocks.get((new Random()).nextInt(emptyBlocks.size()));
        model.lastBlocks()[p.x][p.y] = new Block(rank);

        if (layout != null) {
            layout.playSpawn(p.x, p.y, model.lastBlocks()[p.x][p.y]);
        }
    }

    public void undo() {
        if (model.size() > 1) {
            model.popBack();
            layout.setBoard(model.lastBoard());
            layout.refresh();
        }
    }

    private boolean isGameOver() {
        return model.lastBoard().isStalemate();
    }

    private void gameOverJudge() {
        if (isGameOver()) {
            // TODO: 2016/7/24
            //mSound.playGameOver();
        }

    }

    public GameModel getModel() {
        return model;
    }
}
