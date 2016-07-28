package com.bjtu.zero.a2048.core;

public class Status implements Cloneable {

    private int score;
    private Board board;

    public Status(int boardSize) {
        score = 0;
        board = new Board(boardSize);
        board.initialize();
    }

    public Status(int score, Board board) {
        this.board = board;
        this.score = score;
    }

    public Status clone() {
        Status clone = null;
        try {
            clone = (Status) super.clone();
            clone.score = this.score;
            clone.board = this.board.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
