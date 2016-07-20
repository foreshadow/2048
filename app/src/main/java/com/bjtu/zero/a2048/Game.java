package com.bjtu.zero.a2048;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

public class Game {

    private MainActivity activity;
    private int size;
    private Deque<Status> history;

    public Game(MainActivity activity) {
        this(activity, 4);
    }

    public Game(MainActivity activity, int size) {
        this.activity = activity;
        this.size = size;
        this.history = new LinkedList<>();
        this.history.add(new Status(size));
        this.spawnBlock();
        this.spawnBlock();
    }

    public void leftSlide() {
        // TODO: 2016/7/20
    }

    public void rightSlide() {
        // TODO: 2016/7/20
    }

    public void upSlide() {
        // TODO: 2016/7/20
    }

    public void downSlide() {
        // TODO: 2016/7/20
    }

    private void newStatus(Status status) {
        history.add(status);
        while (history.size() > Settings.HISTORTYSIZE) {
            history.removeFirst();
        }
    }

    private void spawnBlock() {
        if (isGameOver()) {
            throw new AssertionError();
        }
        int rank = 1;
        if (Math.random() < Settings.RANK2PROBABILITY) {
            rank = 2;
        }
        ArrayList<Block> emptyBlocks = history.getLast().getBoard().emptyBlocks();
        Block block = emptyBlocks.get((new Random()).nextInt(emptyBlocks.size()));
        block.setRank(rank);
        activity.spawn(block);
    }

    public void undo() {
        if (history.size() > 1) {
            history.removeLast();
            activity.update(history.getLast().getBoard().blockLine());
        }
    }

    private boolean isGameOver() {
        return history.getLast().getBoard().isStalemate();
    }

    private void gameOverJudge() {
        if (isGameOver()) {
            activity.gameOver();
        }
    }
}
