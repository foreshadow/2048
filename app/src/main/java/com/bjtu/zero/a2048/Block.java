package com.bjtu.zero.a2048;

public class Block {

    private int rank;

    public Block (){
        rank=0;
    }

    public Block(Block block){
        this.rank=block.rank;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isempty() {
        return this.getRank() == 0;
    }

    public void swapRank(Block b){
        int bRank=b.rank;
        b.rank=this.rank ;
        this.rank=bRank ;
    }

    public boolean isSameRank(Block b) {
        return  this.rank == b.rank;
    }

    public void addRank(){
        rank++;
    }
}
