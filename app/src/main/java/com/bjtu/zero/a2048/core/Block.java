package com.bjtu.zero.a2048.core;

import java.io.Serializable;

public class Block implements Cloneable , Serializable {

    private int rank;

    public Block() {
        this(0);
    }

    public Block(int rank) {
        this.rank = rank;
    }

    @Override
    public Block clone() {
        Block clone = null;
        try {
            clone = (Block) super.clone();
            clone.rank = this.rank;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isEmpty() {
        return this.getRank() == 0;
    }

    public void swapRank(Block b) {
        int bRank = b.rank;
        b.rank = this.rank;
        this.rank = bRank;
    }

    public boolean isSameRank(Block b) {
        return this.rank == b.rank;
    }

    public void increase() {
        ++rank;
    }
}
