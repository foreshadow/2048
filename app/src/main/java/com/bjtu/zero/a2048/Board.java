package com.bjtu.zero.a2048;

import android.graphics.Point;

import java.util.ArrayList;

public class Board implements Cloneable {

    private int size;
    private Block[][] data;

    public Board(int size) {
        this.setSize(size);
        data = new Block[size][size];
    }

    public void initialize() {
        for (int i = 0; i < getSize(); i++) {
            data[i] = new Block[getSize()];
            for (int j = 0; j < getSize(); j++) {
                data[i][j] = new Block();
            }
        }
    }

    public Board clone() {
        Board clone = null;
        try {
            clone = (Board) super.clone();
            clone.setSize(this.getSize());
            clone.data = new Block[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    clone.data[i][j] = this.getData()[i][j].clone();
                }
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

    public Block[][] getData() {
        return data;
    }

    public void setData(Block[][] data) {
        this.data = data;
    }

    public boolean isStalemate() {
        if (emptyBlocks().size() > 0) {
            return false;
        }
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize() - 1; j++) {
                if (data[i][j].isSameRank(data[i][j + 1])) {
                    return false;
                }
                if (data[j][i].isSameRank(data[j + 1][i])) {
                    return false;
                }
            }
        }
        return true;
    }

    ArrayList<Point> emptyBlocks() {
        ArrayList<Point> array = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (getData()[i][j].isEmpty())
                    array.add(new Point(i, j));
            }
        }
        return array;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
