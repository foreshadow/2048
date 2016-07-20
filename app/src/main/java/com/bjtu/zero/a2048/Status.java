package com.bjtu.zero.a2048;

public class Status {

    private int score;
    private Board board;

    public Status(int boardSize) {
        score = 0;
        board = new Board(boardSize);
    }

    public Status(int score, Board board) {
        this.board = board;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
