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

    public void slideLeft() {
        if (!canMove(2)) return;
        Status nextStatus = history.getLast().clone();
        Block [][] nextBlock = nextStatus.getBoard().getData();
        for (int i = 0; i < size; i++) {
            for (int j = 1; j < size ; j++) {
                if (!nextBlock[i][j].isEmpty()) {
                    if (nextBlock[i][j].isSameRank(nextBlock[i][j - 1])) {
                        nextBlock[i][j - 1].setRank(0);
                        nextBlock[i][j].increase();
                        nextStatus.addScore(Settings.SCORE_LIST[nextBlock[i][j].getRank()]);
                    }
                }
            }
        }
        BlockChangeList changeList=new BlockChangeList();
        Block [][]  preBlock = history.getLast().getBoard().getData();
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(!nextBlock[i][j].isEmpty()){
                    int toX=i,toY=j-1;
                    while(toY>=0&&nextBlock[toX][toY].isEmpty()){
                        nextBlock[toX][toY].swapRank(nextBlock[toX][toY+1]);
                        toY--;
                    }
                    if(nextBlock[i][toY+1].isSameRank(preBlock[i][j]))
                        changeList.add(new BlockChangeListItem(preBlock[i][j],toX,toY+1,BlockChangeListItem.NextStatus.MAINTAIN));
                    else{
                        changeList.add(new BlockChangeListItem(preBlock[i][j],toX,toY+1,BlockChangeListItem.NextStatus.INCREASE));
                        changeList.add(new BlockChangeListItem(preBlock[i][j-1],toX,toY+1,BlockChangeListItem.NextStatus.DESTROY));
                    }
                }
            }
        }
        //layout.playTransition(changeList);
        newStatus(nextStatus);
    }

    public void slideRight() {
        if (!canMove(3)) return;
        Status nextStatus = history.getLast().clone();
        Block [][] nextBlock = nextStatus.getBoard().getData();
        for (int i = 0; i < size; i++) {
            for (int j = size-2; j >=0 ; j--) {
                if (!nextBlock[i][j].isEmpty()) {
                    if (nextBlock[i][j].isSameRank(nextBlock[i][j + 1])) {
                        nextBlock[i][j + 1].setRank(0);
                        nextBlock[i][j].increase();
                        nextStatus.addScore(Settings.SCORE_LIST[nextBlock[i][j].getRank()]);
                    }
                }
            }
        }
        BlockChangeList changeList=new BlockChangeList();
        Block [][]  preBlock = history.getLast().getBoard().getData();
        for(int i=0;i<size;i++){
            for(int j=size-1;j>=0;j--){
                if(!nextBlock[i][j].isEmpty()){
                    int toX=i,toY=j+1;
                    while(toY<size && nextBlock[toX][toY].isEmpty()){
                        nextBlock[toX][toY].swapRank(nextBlock[toX][toY-1]);
                        toY++;
                    }
                    if(nextBlock[i][toY-1].isSameRank(preBlock[i][j]))
                        changeList.add(new BlockChangeListItem(preBlock[i][j],toX,toY-1,BlockChangeListItem.NextStatus.MAINTAIN));
                    else{
                        changeList.add(new BlockChangeListItem(preBlock[i][j],toX,toY-1,BlockChangeListItem.NextStatus.INCREASE));
                        changeList.add(new BlockChangeListItem(preBlock[i][j+1],toX,toY-1,BlockChangeListItem.NextStatus.DESTROY));
                    }
                }
            }
        }
       // layout.playTransition(changeList);
        newStatus(nextStatus);
    }

    public void slideUp() {
        if (!canMove(0)) return;
        Status nextStatus = history.getLast().clone();
        Block [][] nextBlock = nextStatus.getBoard().getData();
        for (int j = 0; j < size; j++) {
            for (int i = 1; i<size ; i++) {
                if (!nextBlock[i][j].isEmpty()) {
                    if (nextBlock[i][j].isSameRank(nextBlock[i-1][j])) {
                        nextBlock[i-1][j ].setRank(0);
                        nextBlock[i][j].increase();
                        nextStatus.addScore(Settings.SCORE_LIST[nextBlock[i][j].getRank()]);
                    }
                }
            }
        }
        BlockChangeList changeList=new BlockChangeList();
        Block [][]  preBlock = history.getLast().getBoard().getData();
        for(int  j =0;j <size;j++){
            for(int i=0;i<size;i++){
                if(!nextBlock[i][j].isEmpty()){
                    int toX=i-1,toY=j;
                    while(toX>=0 && nextBlock[toX][toY].isEmpty()){
                        nextBlock[toX][toY].swapRank(nextBlock[toX-1][toY]);
                        toX--;
                    }
                    if(nextBlock[toX+1][toY].isSameRank(preBlock[i][j]))
                        changeList.add(new BlockChangeListItem(preBlock[i][j],toX+1,toY,BlockChangeListItem.NextStatus.MAINTAIN));
                    else{
                        changeList.add(new BlockChangeListItem(preBlock[i][j],toX+1,toY,BlockChangeListItem.NextStatus.INCREASE));
                        changeList.add(new BlockChangeListItem(preBlock[i-1][j],toX+1,toY,BlockChangeListItem.NextStatus.DESTROY));
                    }
                }
            }
        }
        //layout.playTransition(changeList);
        newStatus(nextStatus);
    }

    public void slideDown() {
        if (!canMove(1)) return;
        Status nextStatus = history.getLast().clone();
        Block [][] nextBlock = nextStatus.getBoard().getData();
        for (int j = 0; j < size; j++) {
            for (int i = size-2; i>=0 ; i--) {
                if (!nextBlock[i][j].isEmpty()) {
                    if (nextBlock[i][j].isSameRank(nextBlock[i+1][j])) {
                        nextBlock[i+1][j ].setRank(0);
                        nextBlock[i][j].increase();
                        nextStatus.addScore(Settings.SCORE_LIST[nextBlock[i][j].getRank()]);
                    }
                }
            }
        }
        BlockChangeList changeList=new BlockChangeList();
        Block [][]  preBlock = history.getLast().getBoard().getData();
        for(int  j =0;j <size;j++){
            for(int i=size-1;i>=0;i -- ){
                if(!nextBlock[i][j].isEmpty()){
                    int toX=i+1,toY=j;
                    while(toX<size && nextBlock[toX][toY].isEmpty()){
                        nextBlock[toX][toY].swapRank(nextBlock[toX+1][toY]);
                        toX++;
                    }
                    if(nextBlock[toX-1][toY].isSameRank(preBlock[i][j]))
                        changeList.add(new BlockChangeListItem(preBlock[i][j],toX-1,toY,BlockChangeListItem.NextStatus.MAINTAIN));
                    else{
                        changeList.add(new BlockChangeListItem(preBlock[i][j],toX-1,toY,BlockChangeListItem.NextStatus.INCREASE));
                        changeList.add(new BlockChangeListItem(preBlock[i+1][j],toX-1,toY,BlockChangeListItem.NextStatus.DESTROY));
                    }
                }
            }
        }
        //layout.playTransition(changeList);
        newStatus(nextStatus);
    }

    private void newStatus(Status status) {
        history.add(status);
        while (history.size() > Settings.HISTORTY_SIZE) {
            history.removeFirst();
        }
        spawnBlock();
        //layout.setBoard(history.getLast().getBoard());
        layout.refresh();
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
