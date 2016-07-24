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
        this(layout, Settings.Game.DEFAULT_SIZE);
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
        Block [][]  preBlock = history.getLast().getBoard().getData();
        BlockChangeList changeList=new BlockChangeList();
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(!nextBlock[i][j].isEmpty()) {
                    for(int k=j+1;k<size;k++) {
                        if (nextBlock[i][j].isSameRank(nextBlock[i][k])) {
                            nextBlock[i][j].increase();
                            nextBlock[i][k].setRank(0);
                            nextStatus.addScore(Settings.UI.SCORE_LIST[nextBlock[i][j].getRank()]);
                            int toY = j - 1;
                            while (toY >= 0 && nextBlock[i][toY].isEmpty()){
                                toY--;
                            }
                            toY++;
                            nextBlock[i][j].swapRank(nextBlock[i][toY]);
                            //把已经increase且位置没变的blcok也add了
                            changeList.add(new BlockChangeListItem(preBlock[i][j], i, toY, BlockChangeListItem.NextStatus.INCREASE));
                            changeList.add(new BlockChangeListItem(preBlock[i][k], i, toY, BlockChangeListItem.NextStatus.DESTROY));
                            break;
                        }
                    }
                    if(nextBlock[i][j].isSameRank(preBlock[i][j])){
                        int toY = j - 1;
                        while (toY >= 0 && nextBlock[i][toY].isEmpty()){
                            toY--;
                        }
                        toY++;
                        if(j!=toY){
                            nextBlock[i][j].swapRank(nextBlock[i][toY]);
                            changeList.add(new BlockChangeListItem(preBlock[i][j], i, toY, BlockChangeListItem.NextStatus.MAINTAIN));
                        }
                    }
                }
            }
        }
        layout.playTransition(changeList);
        newStatus(nextStatus);
    }

    public void slideRight() {
        if (!canMove(3)) return;
        Status nextStatus = history.getLast().clone();
        Block [][] nextBlock = nextStatus.getBoard().getData();
        Block [][]  preBlock = history.getLast().getBoard().getData();
        BlockChangeList changeList=new BlockChangeList();
        for(int i=0;i<size;i++){
            for(int j=size-1 ;j>=0;j--){
                if(!nextBlock[i][j].isEmpty()) {
                    for(int k=j-1;k>=0;k--) {
                        if (nextBlock[i][j].isSameRank(nextBlock[i][k])) {
                            nextBlock[i][j].increase();
                            nextBlock[i][k].setRank(0);
                            nextStatus.addScore(Settings.UI.SCORE_LIST[nextBlock[i][j].getRank()]);
                            int toY = j + 1;
                            while (toY < size && nextBlock[i][toY].isEmpty()){
                                toY++;
                            }
                            toY-- ;
                            nextBlock[i][j].swapRank(nextBlock[i][toY]);
                            //把已经increase且位置没变的blcok也add了
                            changeList.add(new BlockChangeListItem(preBlock[i][j], i, toY, BlockChangeListItem.NextStatus.INCREASE));
                            changeList.add(new BlockChangeListItem(preBlock[i][k], i, toY, BlockChangeListItem.NextStatus.DESTROY));
                            break;
                        }
                    }
                    if(nextBlock[i][j].isSameRank(preBlock[i][j])){
                        int toY = j + 1;
                        while (toY < size && nextBlock[i][toY].isEmpty()){
                            toY++;
                        }
                        toY--;
                        if(j!=toY){
                            nextBlock[i][j].swapRank(nextBlock[i][toY]);
                            changeList.add(new BlockChangeListItem(preBlock[i][j], i, toY, BlockChangeListItem.NextStatus.MAINTAIN));
                        }
                    }
                }
            }
        }
        layout.playTransition(changeList);
        newStatus(nextStatus);
    }

    public void slideUp() {
        if (!canMove(0)) return;
        Status nextStatus = history.getLast().clone();
        Block [][] nextBlock = nextStatus.getBoard().getData();
        Block [][]  preBlock = history.getLast().getBoard().getData();
        BlockChangeList changeList=new BlockChangeList();
        for(int j=0;j<size;j++){
            for(int i=0;i<size;i++){
                if(!nextBlock[i][j].isEmpty()) {
                    for(int k=i+1;k<size;k++) {
                        if (nextBlock[i][j].isSameRank(nextBlock[k][j])) {
                            nextBlock[i][j].increase();
                            nextBlock[k][j].setRank(0);
                            nextStatus.addScore(Settings.UI.SCORE_LIST[nextBlock[i][j].getRank()]);
                            int toX = i - 1;
                            while (toX >= 0 && nextBlock[toX][j].isEmpty()){
                                toX--;
                            }
                            toX++;
                            nextBlock[i][j].swapRank(nextBlock[toX][j]);
                            //把已经increase且位置没变的blcok也add了
                            changeList.add(new BlockChangeListItem(preBlock[i][j], toX,j, BlockChangeListItem.NextStatus.INCREASE));
                            changeList.add(new BlockChangeListItem(preBlock[i][k], toX,j, BlockChangeListItem.NextStatus.DESTROY));
                            break;
                        }
                    }
                    if(nextBlock[i][j].isSameRank(preBlock[i][j])){
                        int toX = i - 1;
                        while (toX >= 0 && nextBlock[toX][j].isEmpty()){
                            toX--;
                        }
                        toX++;
                        if(i!=toX){
                            nextBlock[i][j].swapRank(nextBlock[toX][j]);
                            changeList.add(new BlockChangeListItem(preBlock[i][j], toX, j, BlockChangeListItem.NextStatus.MAINTAIN));
                        }
                    }
                }
            }
        }
        layout.playTransition(changeList);
        newStatus(nextStatus);
    }

    public void slideDown() {
        if (!canMove(1)) return;
        Status nextStatus = history.getLast().clone();
        Block [][] nextBlock = nextStatus.getBoard().getData();
        Block [][]  preBlock = history.getLast().getBoard().getData();
        BlockChangeList changeList=new BlockChangeList();
        for(int j=0;j<size;j++){
            for(int i=size-1;i>=0;i--){
                if(!nextBlock[i][j].isEmpty()) {
                    for(int k=i-1;k>=0;k--) {
                        if (nextBlock[i][j].isSameRank(nextBlock[k][j])) {
                            nextBlock[i][j].increase();
                            nextBlock[k][j].setRank(0);
                            nextStatus.addScore(Settings.UI.SCORE_LIST[nextBlock[i][j].getRank()]);
                            int toX = i + 1;
                            while (toX <size && nextBlock[toX][j].isEmpty()){
                                toX++;
                            }
                            toX--;
                            nextBlock[i][j].swapRank(nextBlock[toX][j]);
                            //把已经increase且位置没变的blcok也add了
                            changeList.add(new BlockChangeListItem(preBlock[i][j], toX,j, BlockChangeListItem.NextStatus.INCREASE));
                            changeList.add(new BlockChangeListItem(preBlock[i][k], toX,j, BlockChangeListItem.NextStatus.DESTROY));
                            break;
                        }
                    }
                    if(nextBlock[i][j].isSameRank(preBlock[i][j])){
                        int toX = i + 1;
                        while (toX < size && nextBlock[toX][j].isEmpty()){
                            toX++;
                        }
                        toX--;
                        if(i!=toX){
                            nextBlock[i][j].swapRank(nextBlock[toX][j]);
                            changeList.add(new BlockChangeListItem(preBlock[i][j], toX, j, BlockChangeListItem.NextStatus.MAINTAIN));
                        }
                    }
                }
            }
        }
        layout.playTransition(changeList);
        newStatus(nextStatus);
    }



    private void newStatus(Status status) {
        history.add(status);
        while (history.size() > Settings.Game.HISTORY_SIZE) {
            history.removeFirst();
        }
        spawnBlock();
        layout.setBoard(history.getLast().getBoard());
        layout.refresh();
    }

    private void spawnBlock() {
        if (isGameOver()) {
            throw new AssertionError();
        }
        int rank = 1;
        if (Math.random() < Settings.Game.RANK_2_PROBABILITY) {
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
