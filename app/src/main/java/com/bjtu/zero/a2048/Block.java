package com.bjtu.zero.a2048;

public class Block {

    private int rank;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isempty() {
        return this.getRank() == 0;
    }

    public boolean isSameRank(Block b) {
        return this.rank == b.rank;
    }
}
