package com.bjtu.zero.a2048;

public class Settings {

    public static final String[] STRING_LIST = new String[]{
            "", "2", "4", "8", "16", "32", "64", "128", "256", "512", "1024", "2048"
    };
    public static final int[] SCORE_LIST = new int[]{
            0, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048
    };
    public static final int HISTORTY_SIZE = 1; //可撤销的步数
    public static final double RANK_2_PROBABILITY = 0.1; //RANK2出现的概率
    public static final double MINIMUM_MOVING_DISTANCE = 50;
    public static final double BOARD_BOARDER_PERCENT = 0.15;
    public static final int DEFAULT_SIZE = 4;
    public static double INNER_BLOCK_PERCENT = 0.9;
}
