package com.bjtu.zero.a2048;

import android.graphics.Color;

public class Setting {

    static class UI {

        public static final String[] STRING_LIST = new String[]{
                "", "2", "4", "8", "16", "32", "64", "128", "256", "512", "1024", "2048",
        };
        public static final int[] SCORE_LIST = new int[]{
                0, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048,
        };
        public static final int[] BACKGROUND = new int[]{
                Color.parseColor("#bfbfbf"),
                Color.parseColor("#dfdfdf"), Color.parseColor("#cfcfcf"),
                Color.rgb(255, 204, 102), Color.rgb(255, 185, 93),
                Color.rgb(255, 167, 83), Color.rgb(255, 148, 74),
                Color.rgb(255, 130, 65), Color.rgb(255, 111, 56),
                Color.rgb(255, 93, 46), Color.rgb(255, 74, 37),
                Color.rgb(255, 56, 28), Color.rgb(255, 37, 19),
                Color.rgb(255, 19, 9), Color.rgb(255, 0, 0),
        };
        public static final int[] FOREGROUND = new int[]{
                Color.TRANSPARENT,
                Color.DKGRAY, Color.DKGRAY,
                Color.WHITE, Color.WHITE,
                Color.WHITE, Color.WHITE,
                Color.WHITE, Color.WHITE,
                Color.WHITE, Color.WHITE,
                Color.WHITE, Color.WHITE,
        };
        public static final double BOARD_BOARDER_PERCENT = 0.08;
        public static final double INNER_BLOCK_PERCENT = 0.9;
        public static final double MINIMUM_MOVING_DISTANCE_ON_FLING = 50;
        public static final int ANIMATION_DURATION_MILLISECONDS = 500;
        public static final float BLOCK_SPAWN_SCALE_FROM_PERCENT = 0.5f;
        public static final int BLOCK_ROUND_RAD = 20;
        public static final int BLOCK_FONT_SIZE = 75;
    }

    static class Game {

        public static final int HISTORY_SIZE = 1; //可撤销的步数
        public static final double RANK_2_PROBABILITY = 0.1; //RANK2出现的概率
        public static final int DEFAULT_SIZE = 4;
    }
}
