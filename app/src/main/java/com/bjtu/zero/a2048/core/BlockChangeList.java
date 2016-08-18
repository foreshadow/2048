package com.bjtu.zero.a2048.core;

import com.bjtu.zero.a2048.Setting;

import java.io.Serializable;
import java.util.ArrayList;

public class BlockChangeList extends ArrayList<BlockChangeListItem> implements Serializable {

    public int scoreIncrement() {
        int score = 0;
        for (BlockChangeListItem item : this) {
            if (item.nextStatus == BlockChangeListItem.NextStatus.INCREASE) {
                score += Setting.UI.SCORE_LIST[item.block.getRank()];
            }
        }
        return score;
    }
}
