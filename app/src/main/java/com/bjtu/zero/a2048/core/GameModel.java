package com.bjtu.zero.a2048.core;

import android.util.Log;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

public class GameModel implements Serializable {

    private static final long serialVersion = 1L;
    private Deque<Status> history;
    private int capacity;

    public GameModel(int capacity) {
        history = new LinkedList<>();
        this.capacity = capacity;
    }

    public int size() {
        return history.size();
    }

    public void clear() {
        history.clear();
    }

    public Status lastStatus() {
        return history.getLast();
    }

    public Board lastBoard() {
        return history.getLast().getBoard();
    }

    public Block[][] lastBlocks() {
        return history.getLast().getBoard().getData();
    }

    public void append(Status status) {
        history.add(status);
        while (history.size() > capacity) {
            history.removeFirst();
        }
    }

    public void popBack() {
        history.removeLast();
    }

    public Deque<Status> getHistory() {
        return history;
    }

    public void setHistory(Deque<Status> h) {
        history = h;
        Log.e("aaaaa", "histroy");
    }
}
