package com.bjtu.zero.a2048.core;

import android.graphics.Point;

import com.bjtu.zero.a2048.Setting;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 棋盘。
 *
 * @author Infinity
 */

public class Board implements Cloneable, Serializable {

    private int size;
    private Block[][] data;

    /**
     * 该函数仅在测试时使用。
     * 以Setting中的棋盘大小创建棋盘。
     * 一般情况下，请明确指出棋盘大小。
     */
    @Deprecated
    public Board() {
        this(Setting.Runtime.BOARD_SIZE);
    }

    /**
     * 以指定的大小创建空棋盘。
     *
     * @param size 棋盘大小
     */
    public Board(int size) {
        this.size = size;
        data = new Block[size][size];
    }

    /**
     * 初始化棋盘每个Block为<code>new Block()</code>。
     *
     * @see Block#Block()
     */
    public void initialize() {
        for (int i = 0; i < size(); i++) {
            data[i] = new Block[size()];
            for (int j = 0; j < size(); j++) {
                data[i][j] = new Block();
            }
        }
    }

    /**
     * 克隆Board。
     *
     * @return 克隆的Board
     */
    public Board clone() {
        Board clone = null;
        try {
            clone = (Board) super.clone();
            clone.size = this.size();
            clone.data = new Block[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (getBlock(i, j) != null) {
                        clone.data[i][j] = getBlock(i, j).clone();
                    }
                }
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

    /**
     * 返回棋盘大小。
     *
     * @return 棋盘大小
     */
    public int size() {
        return size;
    }

    /**
     * 获得data数组。
     * 如非必要，建议使用getBlock。
     *
     * @return data数组
     * @see #getBlock(int, int)
     */
    public Block[][] getData() {
        return data;
    }

    /**
     * 获得指定位置的Block。
     *
     * @param i 行
     * @param j 列
     * @return 指定位置的Block
     */
    public Block getBlock(int i, int j) {
        return data[i][j];
    }

    /**
     * 返回该棋盘是否为死局。
     * 一般用于游戏结束的判断。
     *
     * @return 该棋盘是否为死局
     */
    public boolean isStalemate() {
        if (emptyBlocks().size() > 0) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size() - 1; j++) {
                if (data[i][j].isSameRank(data[i][j + 1])) {
                    return false;
                }
                if (data[j][i].isSameRank(data[j + 1][i])) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 返回空Block的坐标。
     * 一般用于生成方块。
     *
     * @return 空Block坐标数组
     * @see Block#isEmpty()
     */
    ArrayList<Point> emptyBlocks() {
        ArrayList<Point> array = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (getBlock(i, j).isEmpty()) {
                    array.add(new Point(i, j));
                }
            }
        }
        return array;
    }
}
