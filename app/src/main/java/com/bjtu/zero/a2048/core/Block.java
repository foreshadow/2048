package com.bjtu.zero.a2048.core;

import java.io.Serializable;

/**
 * 方块。
 * 包含一个rank，记录方块的阶而没有直接使用2, 4, 8, 16来表示。
 * 这样可以比较方便的更换方块显示的内容。
 *
 * @author 机智的Infinity
 */

public class Block implements Cloneable, Serializable {

    /**
     * 方块的阶。0阶一般表示空方块，通常在UI中是透明的。
     * 对于经典版，1阶表示2，2阶表示4，3阶表示8，以此类推。
     */
    private int rank;

    /**
     * 创建一个0阶方块。
     */
    public Block() {
        this(0);
    }

    /**
     * 以指定的阶创建方块。
     *
     * @param rank 阶
     */
    public Block(int rank) {
        this.rank = rank;
    }

    /**
     * 克隆Block
     *
     * @return 克隆的Block
     */
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

    /**
     * 得到阶。
     *
     * @return 阶
     */
    public int getRank() {
        return rank;
    }

    /**
     * 设置阶。
     * 最初的设计不打算使Block的阶在构造完后能够被修改。
     * 考虑到要实现动画效果，在方块合并的时候设计应为创建新方块。
     * 然而在做动画效果的时候将就了一下。
     *
     * @param rank 阶
     */
    @Deprecated
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * 返回方块的阶是否非零
     *
     * @return 方块的阶是否非零
     */
    public boolean isEmpty() {
        return this.getRank() == 0;
    }

    /**
     * 和另一个方块交换阶。
     * 很糟糕的函数。我也不知道Lazy_sheep这样设计是做什么。
     * 因此我把它设为<code>@Deprecated</code>了
     *
     * @param block 另一个方块
     */
    @Deprecated
    public void swapRank(Block block) {
        int bRank = block.rank;
        block.rank = this.rank;
        this.rank = bRank;
    }

    /**
     * 返回是否和另一个方块同阶
     *
     * @param block 另一个方块
     * @return 是否和另一个方块同阶
     */
    public boolean isSameRank(Block block) {
        return this.rank == block.rank;
    }

    /**
     * 阶+1。
     * 做动画效果时候将就的产物。
     * 有机会要重构掉。
     */
    @Deprecated
    public void increase() {
        ++rank;
    }
}
