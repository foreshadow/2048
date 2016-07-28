package com.bjtu.zero.a2048;

public class Status implements Cloneable {

    private int score;
    private int adds;
    private Board board;

    public Status(int boardSize) {
        score = 0;
        adds =0;
        board = new Board(boardSize);
        board.initialize();
    }

    public Status(int score, Board board) {
        this.board = board;
        this.score = score;
        this.adds=0;
    }

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

    public void setAdds(int adds){
        this.adds=adds ;
    }

    public int getAdds(){
        return adds ;
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
