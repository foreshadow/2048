package com.bjtu.zero.a2048;

import java.util.ArrayList;
import java.util.Collections;

public class Board {

    private int size;
    private Block[][] data;

    public Board(int size) {
        this.size = size;
        data = new Block[size][size];
    }

    public ArrayList<Block> blockLine() {
        ArrayList<Block> list = new ArrayList<>();
        for (Block[] line : data) {
            Collections.addAll(list, line);
        }
        return list;
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
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1; j++) {
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

    ArrayList<Block> emptyBlocks() {
        ArrayList<Block> array = new ArrayList<>();
        for (Block[] blockLine : data) {
            for (Block block : blockLine) {
                if (block.isempty())
                    array.add(block);
            }
        }
        return array;
    }
}
