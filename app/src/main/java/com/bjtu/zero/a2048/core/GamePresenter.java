package com.bjtu.zero.a2048.core;

import android.content.Context;
import android.graphics.Point;
import android.os.AsyncTask;
import android.util.Log;
import android.view.animation.Animation;

import com.bjtu.zero.a2048.Setting;
import com.bjtu.zero.a2048.ui.GameLayout;
import com.bjtu.zero.a2048.ui.ScoreBoardLayout;
import com.bjtu.zero.a2048.ui.SoundManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class GamePresenter {

    private ScoreBoardLayout scoreBoardLayout;
    private int size;
    private boolean animationInProgress;
    private GameLayout gameLayout;
    private GameModel gameModel;
    private SoundManager soundManager;
    private int[][] increment = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private Context context;

    public GamePresenter() {
        this(Setting.Game.DEFAULT_SIZE);
    }

    public GamePresenter(int size) {
        this.size = size;
        animationInProgress = false;
        gameModel = new GameModel(Setting.Game.HISTORY_SIZE);
        Log.e("aaaaa", "loading soundmanager");
        soundManager = new SoundManager();
        Log.e("aaaaa", "soundmanager loaded");
    }

    public void reset() {
        write();
        gameModel.clear();
        soundManager.clear();
        if (gameLayout != null) {
            gameLayout.setBoard(new Board());
            start();
        }
        getScoreBoardLayout().setScore(0);
    }

    public void write() {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            Log.e("aaaaa", "write");
            fos = getContext().openFileOutput("history.txt", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            Log.e("aaaaa", "write111");
            oos.writeObject(gameModel);
            Log.e("aaaaa", "write ok");
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    //fos流关闭异常
                    e.printStackTrace();
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    //oos流关闭异常
                    e.printStackTrace();
                }
            }
        }
    }

    public void read() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Log.e("aaaaa", "read");
        try {
            fis = getContext().openFileInput("history.txt");
            Log.e("aaaaa", "read111");
            ois = new ObjectInputStream(fis);
            Log.e("aaaaa", "read222");
            gameModel = ((GameModel) ois.readObject());
            Log.e("aaaaa", "read ok");
            gameLayout.setBoard(gameModel.lastBoard());
            getScoreBoardLayout().setScore(gameModel.lastStatus().getScore());

        } catch (Exception e) {
            e.printStackTrace();
            //这里是读取文件产生异常
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    //fis流关闭异常
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    //ois流关闭异常
                    e.printStackTrace();
                }
            }
        }
    }

    public void start() {
        gameModel.append(new Status(size));
        if (gameLayout != null) {
            gameLayout.setBoard(gameModel.lastBoard());
        }
        spawnBlock();
        spawnBlock();
    }

    public void setGameLayout(GameLayout gameLayout) {
        this.gameLayout = gameLayout;
        if (gameLayout != null) {
            gameLayout.refresh();
        }
    }

    public void setScoreBoard(ScoreBoardLayout scoreBoardLayout) {
        this.setScoreBoardLayout(scoreBoardLayout);
    }

    public boolean isSoundEnabled() {
        return soundManager.isEnabled();
    }

    //设置是否播放音效
    public void setSound(boolean enable) {
        soundManager.setEnabled(enable);
    }

    public void loadSound(Context context) {
        soundManager.load(context);
    }

    private boolean isAbleToMove(int direction) {
        if (animationInProgress) return false;
        Status nowStatus = gameModel.lastStatus();
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
        if (!isAbleToMove(2)) return;
        Status nextStatus = gameModel.lastStatus().clone();
        Block[][] nextBlock = nextStatus.getBoard().getData();
        Block[][] preBlock = gameModel.lastBlocks();
        BlockChangeList changeList = new BlockChangeList();
        int maxRank = 1, mergeNum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!nextBlock[i][j].isEmpty()) {
                    for (int k = j + 1; k < size; k++) {
                        if (nextBlock[i][j].isSameRank(nextBlock[i][k])) {
                            nextBlock[i][j].increase();
                            maxRank = Math.max(maxRank, nextBlock[i][j].getRank());
                            mergeNum++;
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
        nextStatus.setAdds(nextStatus.getScore() - getGameModel().lastStatus().getScore());
        soundManager.playProcess(maxRank, mergeNum);
        validOperation(changeList, nextStatus);
    }

    public void slideRight() {
        if (!isAbleToMove(3)) return;
        Status nextStatus = gameModel.lastStatus().clone();
        Block[][] nextBlock = nextStatus.getBoard().getData();
        Block[][] preBlock = gameModel.lastBlocks();
        BlockChangeList changeList = new BlockChangeList();
        int maxRank = 1, mergeNum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = size - 1; j >= 0; j--) {
                if (!nextBlock[i][j].isEmpty()) {
                    for (int k = j - 1; k >= 0; k--) {
                        if (nextBlock[i][j].isSameRank(nextBlock[i][k])) {
                            nextBlock[i][j].increase();
                            maxRank = Math.max(maxRank, nextBlock[i][j].getRank());
                            mergeNum++;
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
        soundManager.playProcess(maxRank, mergeNum);
        validOperation(changeList, nextStatus);
    }

    public void slideUp() {
        if (!isAbleToMove(0)) return;
        Status nextStatus = gameModel.lastStatus().clone();
        Block[][] nextBlock = nextStatus.getBoard().getData();
        Block[][] preBlock = gameModel.lastBlocks();
        BlockChangeList changeList = new BlockChangeList();
        int maxRank = 1, mergeNum = 0;
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                if (!nextBlock[i][j].isEmpty()) {
                    for (int k = i + 1; k < size; k++) {
                        if (nextBlock[i][j].isSameRank(nextBlock[k][j])) {
                            nextBlock[i][j].increase();
                            maxRank = Math.max(maxRank, nextBlock[i][j].getRank());
                            mergeNum++;
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
        soundManager.playProcess(maxRank, mergeNum);
        validOperation(changeList, nextStatus);
    }

    public void slideDown() {
        if (!isAbleToMove(1)) return;
        Status nextStatus = gameModel.lastStatus().clone();
        Block[][] nextBlock = nextStatus.getBoard().getData();
        Block[][] preBlock = gameModel.lastBlocks();
        BlockChangeList changeList = new BlockChangeList();
        int maxRank = 1, mergeNum = 0;
        for (int j = 0; j < size; j++) {
            for (int i = size - 1; i >= 0; i--) {
                if (!nextBlock[i][j].isEmpty()) {
                    for (int k = i - 1; k >= 0; k--) {
                        if (nextBlock[i][j].isSameRank(nextBlock[k][j])) {
                            nextBlock[i][j].increase();
                            maxRank = Math.max(maxRank, nextBlock[i][j].getRank());
                            mergeNum++;
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
        soundManager.playProcess(maxRank, mergeNum);
        validOperation(changeList, nextStatus);
    }

    private void validOperation(BlockChangeList changeList, Status status) {
        if (gameLayout != null) {
            gameLayout.playTranslation(changeList, new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    animationInProgress = true;
                    gameLayout.clearBoard();
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.e("ANIMATION", "onEnd");
                    gameLayout.setBoard(gameModel.lastBoard());
                    spawnBlock();
                    animationInProgress = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        new AnotherTask().execute(String.valueOf(status.getScore()));
        gameModel.append(status);
        if (gameLayout != null) {
            gameLayout.setBoard2(); // critical !
        } else {
            spawnBlock();
        }
        //new AnotherTask().execute(String.valueOf(status.getScore()));
    }

    public void spawnBlock() {
        if (isGameOver()) {
            throw new AssertionError();
        }
        int rank = 1;
        if (Math.random() < Setting.Game.RANK_2_PROBABILITY) {
            rank = 2;
        }
        ArrayList<Point> emptyBlocks = gameModel.lastBoard().emptyBlocks();
        final Point p = emptyBlocks.get(new Random().nextInt(emptyBlocks.size()));
        gameModel.lastBlocks()[p.x][p.y] = new Block(rank);

        if (gameLayout != null) {
            gameLayout.playSpawn(p.x, p.y, gameModel.lastBlocks()[p.x][p.y]);
        }
    }

    public void undo() {
        if (gameModel.size() > 1) {
            gameModel.popBack();
            gameLayout.setBoard(gameModel.lastBoard());
            gameLayout.refresh();
            new AnotherTask().execute(String.valueOf(gameModel.lastStatus().getScore()));
        }
    }

    private boolean isGameOver() {
        return gameModel.lastBoard().isStalemate();
    }

    private void gameOverJudge() {
        if (isGameOver()) {
            // TODO: 2016/7/24
            soundManager.playGameOver();
        }

    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ScoreBoardLayout getScoreBoardLayout() {
        return scoreBoardLayout;
    }

    public void setScoreBoardLayout(ScoreBoardLayout scoreBoardLayout) {
        this.scoreBoardLayout = scoreBoardLayout;
    }

    private class AnotherTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return params[0];
        }

        @Override
        protected void onPostExecute(String result) {
            //更新UI的操作，这里面的内容是在UI线程里面执行的
            getScoreBoardLayout().setScore(Integer.parseInt(result));
        }
    }
}
