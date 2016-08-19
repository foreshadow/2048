package com.bjtu.zero.a2048.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.AsyncTask;
import android.util.Log;
import android.view.animation.Animation;

import com.bjtu.zero.a2048.Setting;
import com.bjtu.zero.a2048.ui.GameLayout;
import com.bjtu.zero.a2048.ui.ScoreBoardLayout;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

/**
 * 处理绝大部分游戏逻辑
 *
 * @author Lazy_sheep unableToMove slide*
 * @author brioso     read write
 * @author Infinity   其他
 */

public class GamePresenter implements Serializable {

    private static final int[][] increment = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final String name = "game";
    private final int size;
    private boolean animationInProgress;
    private Context context;
    private GameModel gameModel;
    private GameLayout gameLayout;
    private ScoreBoardLayout scoreBoardLayout;

    /**
     * 该函数仅在测试时使用。
     * 以Setting中的棋盘大小创建游戏。
     * 一般情况下，请明确指出棋盘大小。
     */
    @Deprecated
    public GamePresenter() {
        this(Setting.Runtime.BOARD_SIZE);
    }

    /**
     * 以指定的棋盘大小创建游戏。
     *
     * @param size 棋盘大小
     */
    public GamePresenter(int size) {
        this.size = size;
        animationInProgress = false;
        gameModel = new GameModel(size, Setting.Game.HISTORY_SIZE);
    }

    /**
     * 该函数仅在测试时使用。
     * 重置游戏。早期开发中，单游戏实例，固定棋盘大小时用此函数重新开始游戏。
     * 一般情况下，请重新创建GamePresenter。
     */
    @Deprecated
    public void reset() {
        write();
        gameModel.clear();
        //Setting.soundManager.clear();
        gameLayout.setSize(Setting.Runtime.BOARD_SIZE);
        if (gameLayout != null) {
            gameLayout.setBoard(new Board());
            start();
        }
        getScoreBoardLayout().setScore(0);
    }

    /**
     * // TODO: 2016/8/17 brioso
     */
    public void write() {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            Log.e("aaaaa", "write");
            fos = getContext().openFileOutput(String.format("%s%s.txt", name, String.valueOf(size)), Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            Log.e("aaaaa", "write111");
            oos.writeObject(gameModel);
            Log.e("aaaaa", "write ok");
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * // TODO: 2016/8/17 brioso
     */
    public void read() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Log.e("aaaaa", "read");
        try {
            fis = getContext().openFileInput(String.format("%s%s.txt", name, String.valueOf(size)));
            Log.e("aaaaa", "read111");
            ois = new ObjectInputStream(fis);
            Log.e("aaaaa", "read222");
            gameModel = ((GameModel) ois.readObject());
            Log.e("aaaaa", "read ok");
            gameLayout.setBoard(gameModel.lastBoard());
            getScoreBoardLayout().setScore(gameModel.lastStatus().getScore());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(int i) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            Log.e("aaaaa", "write " + i);
            fos = getContext().openFileOutput(String.format("save%s.txt", String.valueOf(i)), Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            Log.e("aaaaa", "write111 " + i);
            oos.writeObject(gameModel);
            Log.e("aaaaa", "write ok " + i);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writee(int i) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            Log.e("aaaaa", "write " + i);
            fos = getContext().openFileOutput(String.format("image%s.txt", String.valueOf(i)), Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            Log.e("aaaaa", "write111 " + i);
            Bitmap thumbnail = gameModel.lastStatus().thumbnail();
            oos.writeObject(thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, fos));
            fos.close();
            fos = getContext().openFileOutput(String.format("score%s.txt", String.valueOf(i)), Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(gameModel.lastStatus().getScore());
            Log.e("aaaaa", "write ok " + i);
            fos.close();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
            String date = format.format(new java.util.Date());
            fos = getContext().openFileOutput(String.format("time%s.txt", String.valueOf(i)), Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(date);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void read(int i) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = getContext().openFileInput(String.format("save%s.txt", String.valueOf(i)));
            Log.e("aaaaa", "read111 " + i);
            ois = new ObjectInputStream(fis);
            Log.e("aaaaa", "read222 " + i);
            gameModel = ((GameModel) ois.readObject());
            Log.e("aaaaa", "read ok " + i);
            gameLayout.setBoard(gameModel.lastBoard());
            scoreBoardLayout.setScore(gameModel.lastStatus().getScore());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 开始游戏。
     * 为GameModel增加第一个状态。
     * 更新GameLayout（如果有）。
     * 生成两个方块。
     *
     * @see #spawnBlock()
     */
    public void start() {
        gameModel.append(new Status(size));
        if (gameLayout != null) {
            gameLayout.setBoard(gameModel.lastBoard());
        }
        spawnBlock();
        spawnBlock();
    }

    /**
     * 该函数仅在测试时使用。
     * 绑定GameLayout。
     * 早期开发中，连续模拟多次操作会造成GameLayout异常，
     * 因此用于临时绑定为null和重新绑定。
     *
     * @param gameLayout 要绑定的GameLayout，如果不与任何GameLayout绑定，则为null
     */
    @Deprecated
    public void setGameLayout(GameLayout gameLayout) {
        this.gameLayout = gameLayout;
        if (gameLayout != null) {
            gameLayout.refresh();
        }
    }

    /**
     * 绑定计分版，以及时更新得分情况。
     *
     * @param scoreBoardLayout 要绑定的ScoreBoardLayout
     */
    public void setScoreBoard(ScoreBoardLayout scoreBoardLayout) {
        this.setScoreBoardLayout(scoreBoardLayout);
    }

    /**
     * // TODO: 2016/8/17 Lazy_sheep
     *
     * @param direction
     * @return
     */
    private boolean unableToMove(int direction) {
        if (animationInProgress) return true;
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
                if (nextBlock.isEmpty() || nowBlock.isSameRank(nextBlock)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * // TODO: 2016/8/17 Lazy_sheep
     */
    public void slideLeft() {
        if (unableToMove(2)) return;
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
                            //把已经increase且位置没变的block也add了
                            changeList.add(new BlockChangeListItem(preBlock[i][j], i, toY));
                            changeList.add(new BlockChangeListItem(preBlock[i][k], i, toY));
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
                        changeList.add(new BlockChangeListItem(preBlock[i][j], i, toY));
                    }
                }
            }
        }
        nextStatus.setAdds(nextStatus.getScore() - getGameModel().lastStatus().getScore());
        Setting.Runtime.soundManager.playProcess(maxRank, mergeNum);
        validOperation(changeList, nextStatus);
    }

    /**
     * // TODO: 2016/8/17 Lazy_sheep
     */
    public void slideRight() {
        if (unableToMove(3)) return;
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
                            //把已经increase且位置没变的block也add了
                            changeList.add(new BlockChangeListItem(preBlock[i][j], i, toY));
                            changeList.add(new BlockChangeListItem(preBlock[i][k], i, toY));
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
                        changeList.add(new BlockChangeListItem(preBlock[i][j], i, toY));
                    }
                }
            }
        }
        Setting.Runtime.soundManager.playProcess(maxRank, mergeNum);
        validOperation(changeList, nextStatus);
    }

    /**
     * // TODO: 2016/8/17 Lazy_sheep
     */
    public void slideUp() {
        if (unableToMove(0)) return;
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
                            //把已经increase且位置没变的block也add了
                            changeList.add(new BlockChangeListItem(preBlock[i][j], toX, j));
                            changeList.add(new BlockChangeListItem(preBlock[k][j], toX, j));
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
                        changeList.add(new BlockChangeListItem(preBlock[i][j], toX, j));
                    }
                }
            }
        }
        Setting.Runtime.soundManager.playProcess(maxRank, mergeNum);
        validOperation(changeList, nextStatus);
    }

    /**
     * // TODO: 2016/8/17 Lazy_sheep
     */
    public void slideDown() {
        if (unableToMove(1)) return;
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
                            //把已经increase且位置没变的block也add了
                            changeList.add(new BlockChangeListItem(preBlock[i][j], toX, j));
                            changeList.add(new BlockChangeListItem(preBlock[k][j], toX, j));
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
                        changeList.add(new BlockChangeListItem(preBlock[i][j], toX, j));
                    }
                }
            }
        }
        Setting.Runtime.soundManager.playProcess(maxRank, mergeNum);
        validOperation(changeList, nextStatus);
    }

    /**
     * 准备播放有效滑动后的动画，更新分数，以及生成方块。
     *
     * @param changeList 方块变化
     * @param status     下一个状态
     * @see #spawnBlock()
     * @see AnotherTask
     */
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

    /**
     * 生成方块并播放动画。
     */
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

    /**
     * 撤销。
     */
    public void undo() {
        if (gameModel.historySize() > 1) {
            gameModel.popBack();
            gameLayout.setBoard(gameModel.lastBoard());
            gameLayout.refresh();
            new AnotherTask().execute(String.valueOf(gameModel.lastStatus().getScore()));
        }
    }

    /**
     * 返回游戏是否结束
     *
     * @return GameModel中最后一个状态的棋盘是否不能再做有效操作
     */
    public boolean isGameOver() {
        return gameModel.lastBoard().isStalemate();
    }

    /**
     * 判断游戏结束。
     * 应当在执行完一次操作后调用。
     * 应当处理游戏结束时的行为。
     */
    private void gameOverJudge() {
        if (isGameOver()) {
            // TODO: 2016/7/24
        }

    }

    /**
     * 得到绑定的GameModel
     *
     * @return 绑定的GameModel
     */
    public GameModel getGameModel() {
        return gameModel;
    }

    /**
     * 得到绑定的Context
     *
     * @return 绑定的Context
     */
    public Context getContext() {
        return context;
    }

    /**
     * 绑定Context
     *
     * @param context 要绑定的Context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 得到绑定的ScoreBoardLayout
     *
     * @return 绑定的ScoreBoardLayout
     */
    public ScoreBoardLayout getScoreBoardLayout() {
        return scoreBoardLayout;
    }

    /**
     * 绑定ScoreBoardLayout
     *
     * @param scoreBoardLayout 要绑定的ScoreBoardLayout
     */
    public void setScoreBoardLayout(ScoreBoardLayout scoreBoardLayout) {
        this.scoreBoardLayout = scoreBoardLayout;
    }

    /**
     * 更新分数。// TODO: 2016/8/17 brioso
     */
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
