package com.bjtu.zero.a2048;

public class Status {

    private int score;
    private Board board;
    
    public Status() {
        // TODO: 2016/7/20  
    }
    
    public Status(Board board, int score) {
        // TODO: 2016/7/20  
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
