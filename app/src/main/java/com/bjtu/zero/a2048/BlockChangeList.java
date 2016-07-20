package com.bjtu.zero.a2048;

import java.util.ArrayList;

public class BlockChangeList extends ArrayList<BlockChangeListItem> {

    public int scoreIncreasement() {
        int score = 0;
        for (BlockChangeListItem item : this) {
            score += Settings.SCORELIST[item.block.getRank()];
        }
        return score;
    }
}
