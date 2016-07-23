package com.bjtu.zero.a2048;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

public class Game {

    private MainActivity activity;
    private int size;
    private Deque<Status> history;
    private  int [][] increment;

    public Game(MainActivity activity) {
        this(activity, 4);
    }

    public Game(MainActivity activity, int size) {
        this.activity = activity;
        this.size = size;
        this.increment = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};//上0下1左2右3
        this.history = new LinkedList<>();
        this.history.add(new Status(size));
        this.spawnBlock();
        this.spawnBlock();
    }

    private boolean canMove(int  direction){
        Status nowStatus = history.getLast();
        //某个rank非0的block的next位置为rank==0的block
        //某个rank非0的block的next位置为rank相同的block
        for(int i=0;i<size;i++) {
            for (int j = 0; j < size; j++) {
                Block  nowBlock=nowStatus.getBoard().getData()[i][j];
                if (nowBlock.isempty()) continue;
                int x = i + increment[direction][0];
                int y = j + increment[direction][1];
                if (x < 0 || x >= size || y < 0 || y >= size) continue;
                Block  nextBlock=nowStatus.getBoard().getData()[x][y];
                if(nextBlock.isempty()||nowBlock.isSameRank(nextBlock))
                    return true ;
            }
        }
        return false ;
    }

    public void leftSlide() {
        // TODO: 2016/7/20
        if(!canMove(2)) return ;
        Status nextStatus  = new Status(history.getLast());
        Board nextBoard =nextStatus.getBoard();
        for(int i=0;i<size;i++){
            for(int j=0;j<size-1;j++){
                Block block  = nextBoard.getData()[i][j];
                if(block.isempty())  continue ;
                else if(block.isSameRank(nextBoard.getData()[i][j+1])){
                    block.addRank();
                    nextStatus.addScore(Settings.SCORELIST[block.getRank()]);
                    nextBoard.getData()[i][j+1].setRank(0);
                }
            }
        }
        for(int i=0;i<size;i++){
            for(int j=1;j<size;j++){
                if(!nextBoard.getData()[i][j].isempty()&&nextBoard.getData()[i][j-1].isempty()){
                    nextBoard.getData()[i][j].swapRank(nextBoard.getData()[i][j-1]);
                }
            }
        }
        newStatus(nextStatus);
    }

    public void rightSlide() {
        // TODO: 2016/7/20
        if(!canMove(3))  return ;
        Status nextStatus  = new Status(history.getLast());
        Board nextBoard =nextStatus.getBoard();
        for(int i=0;i<size;i++){
            for(int j=size-1;j>0;j--){
                Block block  = nextBoard.getData()[i][j];
                if(block.isempty())  continue ;
                else if(block.isSameRank(nextBoard.getData()[i][j-1])){
                    block.addRank();
                    nextStatus.addScore(Settings.SCORELIST[block.getRank()]);
                    nextBoard.getData()[i][j-1].setRank(0);
                }
            }
        }
        for(int i=0;i<size;i++){
            for(int j=size-2;j>=0;j--){
                if(!nextBoard.getData()[i][j].isempty()&&nextBoard.getData()[i][j+1].isempty()){
                    nextBoard.getData()[i][j].swapRank(nextBoard.getData()[i][j+1]);
                }
            }
        }
        newStatus(nextStatus);
    }

    public void upSlide() {
        // TODO: 2016/7/20
        if(!canMove(0)) return ;
        Status nextStatus  = new Status(history.getLast());
        Board nextBoard =nextStatus.getBoard();
        for(int j=0;j<size;j++){
            for(int i=0;i<size-1;i++){
                Block block  = nextBoard.getData()[i][j];
                if(block.isempty())  continue ;
                else if(block.isSameRank(nextBoard.getData()[i+1][j])){
                    block.addRank();
                    nextStatus.addScore(Settings.SCORELIST[block.getRank()]);
                    nextBoard.getData()[i+1][j].setRank(0);
                }
            }
        }
        for(int j=0;j<size;j++){
            for(int i=1;i<size;i++){
                if(!nextBoard.getData()[i][j].isempty()&&nextBoard.getData()[i-1][j].isempty()){
                    nextBoard.getData()[i][j].swapRank(nextBoard.getData()[i-1][j]);
                }
            }
        }
        newStatus(nextStatus);
    }

    public void downSlide() {
        // TODO: 2016/7/20
        if(!canMove(1)) return ;
        Status nextStatus  = new Status(history.getLast());
        Board nextBoard =nextStatus.getBoard();
        for(int j=0;j<size;j++){
            for(int i=size-1;i>0;i--){
                Block block  = nextBoard.getData()[i][j];
                if(block.isempty())  continue ;
                else if(block.isSameRank(nextBoard.getData()[i-1][j])){
                    block.addRank();
                    nextStatus.addScore(Settings.SCORELIST[block.getRank()]);
                    nextBoard.getData()[i-1][j].setRank(0);
                }
            }
        }
        for(int j=0;j<size;j++){
            for(int i=size-2;i>=0;i--){
                if(!nextBoard.getData()[i][j].isempty()&&nextBoard.getData()[i+1][j].isempty()){
                    nextBoard.getData()[i][j].swapRank(nextBoard.getData()[i+1][j]);
                }
            }
        }
        newStatus(nextStatus);
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
