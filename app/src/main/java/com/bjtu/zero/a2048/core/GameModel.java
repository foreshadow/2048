package com.bjtu.zero.a2048.core;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 游戏模型的状态的集合以及历史记录。
 *
 * @author Infinity
 */

public class GameModel implements Serializable {

    private final int boardSize;
    private final int capacity;
    private Deque<Status> history;

    /**
     * 创建GameModel。
     *
     * @param boardSize 棋盘大小。仅用作boardSize的返回值，与Status的大小无关。
     * @param capacity  历史记录容量。请务必设置大于等于1的值。
     */
    public GameModel(int boardSize, int capacity) {
        history = new LinkedList<>();
        this.boardSize = boardSize;
        this.capacity = capacity;
    }

    /**
     * 得到棋盘大小。
     *
     * @return 棋盘大小
     */
    public int boardSize() {
        return boardSize;
    }

    /**
     * 得到历史记录大小。
     *
     * @return 历史记录大小
     */
    public int historySize() {
        return history.size();
    }

    /**
     * 清空历史记录。该函数仅在测试时使用，如果要重新开始游戏，请重新创建对象。
     */
    @Deprecated
    public void clear() {
        history.clear();
    }

    /**
     * 返回最后一个Status。没有测试当队列为空时的情况。通常，创建GameModel后应当调用append方法。
     *
     * @return 最后一个Status
     */
    public Status lastStatus() {
        return history.getLast();
    }

    /**
     * 返回最后一个Board。没有测试当队列为空时的情况。通常，创建GameModel后应当调用append方法。
     *
     * @return 最后一个Board
     */
    public Board lastBoard() {
        return history.getLast().getBoard();
    }

    /**
     * 返回最后一个Board的data数组。没有测试当队列为空时的情况。通常，创建GameModel后应当调用append方法。
     *
     * @return 最后一个Board的data数组
     */
    public Block[][] lastBlocks() {
        return history.getLast().getBoard().getData();
    }

    /**
     * 向队尾添加状态。一般用于滑动事件中。
     * 如果队列长度大于capacity，队头状态出队。
     * 为了保证撤销操作的合理性，不应为滑动后新生成方块添加新的Status，应该直接修改最后一个Status。
     *
     * @param status 新的状态
     */
    public void append(Status status) {
        history.add(status);
        while (history.size() > capacity) {
            history.removeFirst();
        }
    }

    /**
     * 队尾状态出队。一般用于撤销。
     */
    public void popBack() {
        history.removeLast();
    }

    /**
     * 得到历史记录双端队列。仅用于调试。
     *
     * @return 历史记录双端队列
     */
    @Deprecated
    public Deque<Status> getHistory() {
        return history;
    }

    /**
     * 设置历史纪录。不觉得这个函数有什么用。
     *
     * @param history 历史记录
     */
    @Deprecated
    public void setHistory(Deque<Status> history) {
        this.history = history;
    }
}
