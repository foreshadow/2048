package com.bjtu.zero.a2048.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bjtu.zero.a2048.Setting;

import java.io.Serializable;

/**
 * 游戏状态，包含棋盘和分数。
 * 应该设计为包含棋盘大小，但考虑到游戏状态依赖于GameModel，
 * 而GameModel包含了棋盘大小，所以暂时没有添加。
 *
 * @author brioso *adds
 * @author Infinity 其他
 */

public class Status implements Cloneable, Serializable {

    private int score;
    private int adds;
    private Board board;

    /**
     * 以指定的棋盘大小创建新棋盘状态。
     *
     * @param boardSize 棋盘大小
     * @see Board#initialize()
     */
    public Status(int boardSize) {
        score = 0;
        adds = 0;
        board = new Board(boardSize);
        board.initialize();
    }

    /**
     * 以指定的分数和棋盘创建状态。
     * 一般用于恢复游戏。
     *
     * @param score 分数
     * @param board 棋盘
     */
    public Status(int score, Board board) {
        this.board = board;
        this.score = score;
        this.adds = 0;
    }

    /**
     * 克隆Status
     *
     * @return 克隆的Status
     */
    public Status clone() {
        Status clone = null;
        try {
            clone = (Status) super.clone();
            clone.score = this.score;
            clone.adds = 0;
            clone.board = this.board.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

    /**
     * // TODO: 2016/8/17 brioso
     *
     * @return
     */
    public int getAdds() {
        return adds;
    }

    /**
     * // TODO: 2016/8/17 brioso
     *
     * @param adds
     */
    public void setAdds(int adds) {
        this.adds = adds;
    }

    /**
     * 得到分数。
     *
     * @return 分数
     */
    public int getScore() {
        return score;
    }

    /**
     * // TODO: 2016/8/17 brioso
     *
     * @param score
     */
    public void addScore(int score) {
        this.score += score;
    }

    /**
     * 得到棋盘。
     *
     * @return 棋盘
     */
    public Board getBoard() {
        return board;
    }

    /**
     * 得到缩略图。
     *
     * @return 缩略图
     */
    public Bitmap thumbnail() {
        int bitmapSize = 240;
        int blockSize = bitmapSize / board.size();
        Bitmap bitmap = Bitmap.createBitmap(bitmapSize, bitmapSize, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        Paint painter = new Paint();
        canvas.drawRGB(127, 127, 127);
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                painter.setColor(Setting.UI.BACKGROUND[board.getBlock(j, i).getRank()]);
                // deliberately transposition here .................. ^. ^
                canvas.drawRect(
                        i * blockSize,
                        j * blockSize,
                        i * blockSize + blockSize,
                        j * blockSize + blockSize,
                        painter
                );
            }
        }
        return bitmap;
    }
}
