package com.bjtu.zero.a2048.core;

import com.bjtu.zero.a2048.Setting;

import java.util.ArrayList;

public class BlockChangeList extends ArrayList<BlockChangeListItem> {

    public int scoreIncreasement() {
        int score = 0;
        for (BlockChangeListItem item : this) {
            if (item.nextStatus == BlockChangeListItem.NextStatus.INCREASE) {
                score += Setting.UI.SCORE_LIST[item.block.getRank()];
            }
        }
        return score;
    }
}
