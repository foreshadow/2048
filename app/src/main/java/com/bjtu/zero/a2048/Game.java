package com.bjtu.zero.a2048;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

public class Game {

    private GameLayout layout;
    private int size;
    private Deque<Status> history;
    private int[][] increment = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public Game(GameLayout layout) {
        this(layout, Settings.DEFAULT_SIZE);
    }

    public Game(GameLayout layout, int size) {
        this.layout = layout;
        this.size = size;
        history = new LinkedList<>();
        history.add(new Status(size));
        layout.setBoard(history.getLast().getBoard());
        layout.refresh();
        spawnBlock();
        spawnBlock();
        layout.refresh();
    }

    private boolean canMove(int direction) {
        Status nowStatus = history.getLast();
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

    public void leftSlide() {
        if (!canMove(2)) return;
        Status nextStatus = history.getLast().clone();
        Board nextBoard = nextStatus.getBoard();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1; j++) {
                Block block = nextBoard.getData()[i][j];
                if (!block.isEmpty()) {
                    if (block.isSameRank(nextBoard.getData()[i][j + 1])) {
                        block.increase();
                        nextStatus.addScore(Settings.SCORE_LIST[block.getRank()]);
                        nextBoard.getData()[i][j + 1].setRank(0);
                    }
                }
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 1; j < size; j++) {
                if (!nextBoard.getData()[i][j].isEmpty() && nextBoard.getData()[i][j - 1].isEmpty()) {
                    nextBoard.getData()[i][j].swapRank(nextBoard.getData()[i][j - 1]);
                }
            }
        }
        newStatus(nextStatus);
    }

    public void rightSlide() {
        if (!canMove(3)) return;
        Status nextStatus = history.getLast().clone();
        Board nextBoard = nextStatus.getBoard();
        for (int i = 0; i < size; i++) {
            for (int j = size - 1; j > 0; j--) {
                Block block = nextBoard.getData()[i][j];
                if (!block.isEmpty()) {
                    if (block.isSameRank(nextBoard.getData()[i][j - 1])) {
                        block.increase();
                        nextStatus.addScore(Settings.SCORE_LIST[block.getRank()]);
                        nextBoard.getData()[i][j - 1].setRank(0);
                    }
                }
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = size - 2; j >= 0; j--) {
                if (!nextBoard.getData()[i][j].isEmpty() && nextBoard.getData()[i][j + 1].isEmpty()) {
                    nextBoard.getData()[i][j].swapRank(nextBoard.getData()[i][j + 1]);
                }
            }
        }
        newStatus(nextStatus);
    }

    public void upSlide() {
        if (!canMove(0)) return;
        Status nextStatus = history.getLast().clone();
        Board nextBoard = nextStatus.getBoard();
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size - 1; i++) {
                Block block = nextBoard.getData()[i][j];
                if (!block.isEmpty()) {
                    if (block.isSameRank(nextBoard.getData()[i + 1][j])) {
                        block.increase();
                        nextStatus.addScore(Settings.SCORE_LIST[block.getRank()]);
                        nextBoard.getData()[i + 1][j].setRank(0);
                    }
                }
            }
        }
        for (int j = 0; j < size; j++) {
            for (int i = 1; i < size; i++) {
                if (!nextBoard.getData()[i][j].isEmpty() && nextBoard.getData()[i - 1][j].isEmpty()) {
                    nextBoard.getData()[i][j].swapRank(nextBoard.getData()[i - 1][j]);
                }
            }
        }
        newStatus(nextStatus);
    }

    public void downSlide() {
        if (!canMove(1)) return;
        Status nextStatus = history.getLast().clone();
        Board nextBoard = nextStatus.getBoard();
        for (int j = 0; j < size; j++) {
            for (int i = size - 1; i > 0; i--) {
                Block block = nextBoard.getData()[i][j];
                if (!block.isEmpty()) {
                    if (block.isSameRank(nextBoard.getData()[i - 1][j])) {
                        block.increase();
                        nextStatus.addScore(Settings.SCORE_LIST[block.getRank()]);
                        nextBoard.getData()[i - 1][j].setRank(0);
                    }
                }
            }
        }
        for (int j = 0; j < size; j++) {
            for (int i = size - 2; i >= 0; i--) {
                if (!nextBoard.getData()[i][j].isEmpty() && nextBoard.getData()[i + 1][j].isEmpty()) {
                    nextBoard.getData()[i][j].swapRank(nextBoard.getData()[i + 1][j]);
                }
            }
        }
        newStatus(nextStatus);
    }

    private void newStatus(Status status) {
        history.add(status);
        while (history.size() > Settings.HISTORTY_SIZE) {
            history.removeFirst();
        }
    }

    private void spawnBlock() {
        if (isGameOver()) {
            throw new AssertionError();
        }
        int rank = 1;
        if (Math.random() < Settings.RANK_2_PROBABILITY) {
            rank = 2;
        }
        ArrayList<Point> emptyBlocks = history.getLast().getBoard().emptyBlocks();
        Point p = emptyBlocks.get((new Random()).nextInt(emptyBlocks.size()));
        history.getLast().getBoard().getData()[p.x][p.y] = new Block(rank);
        layout.playSpawn(p.x, p.y, history.getLast().getBoard().getData()[p.x][p.y]);
    }

    public void undo() {
        if (history.size() > 1) {
            history.removeLast();
            layout.setBoard(history.getLast().getBoard());
            layout.refresh();
        }
    }

    private boolean isGameOver() {
        return history.getLast().getBoard().isStalemate();
    }

    private void gameOverJudge() {
        if (isGameOver()) {
            // TODO: 2016/7/24  
        }
    }
}
