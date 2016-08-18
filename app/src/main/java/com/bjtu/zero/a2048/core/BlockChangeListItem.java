package com.bjtu.zero.a2048.core;

import java.io.Serializable;

/**
 * 表示方块移动的情况。用来生成动画。
 * 感觉设计的不好。有机会再重构。
 * 通过对原状态的遍历查找来找到移动前的位置。
 *
 * @author Infinity
 */

public class BlockChangeListItem implements Serializable {

    public final Block block;
    public final int toX;
    public final int toY;

    /**
     * 构造函数。
     *
     * @param block 方块
     * @param toX   目标行
     * @param toY   目标列
     */
    public BlockChangeListItem(Block block, int toX, int toY) {
        this.block = block;
        this.toX = toX;
        this.toY = toY;
    }
}
