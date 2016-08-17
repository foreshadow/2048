package com.bjtu.zero.a2048;

import android.graphics.Color;

public class Setting {

    public static int savemodel = 0;

    public static class UI {

        public static final String[] STRING_LIST_CLASSICAL = new String[]{
                "",
                "2", "4", "8", "16",
                "32", "64", "128", "256",
                "512", "1024", "2048", "4096",
                "8192", "16384", "32768", "65536",
        };
        public static final String[] STRING_LIST_ACADEMIC = new String[]{
                "",
                "幼儿园", "一年级", "二年级", "三年级",
                "四年级", "五年级", "六年级", "初一",
                "初二", "初三", "高一", "高二",
                "高三", "大学", "硕士", "博士",
        };
        public static final String[] STRING_LIST_DYNASTY = new String[]{
                "",
                "夏", "商", "周", "秦",
                "汉", "三国", "晋", "南北朝",
                "隋", "唐", "宋", "元",
                "明", "清", "中华民国", "中国"
        };
        public static final int[] SCORE_LIST = new int[]{
                0, 2, 4, 8, 16, 32, 64, 128, 256,
                512, 1024, 2048, 4096, 8192, 16384, 32768, 65536,
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
                Color.rgb(127, 255, 127), Color.rgb(0, 255, 0),
        };
        public static final int[] FOREGROUND = new int[]{
                Color.TRANSPARENT,
                Color.DKGRAY, Color.DKGRAY,
                Color.WHITE, Color.WHITE,
                Color.WHITE, Color.WHITE,
                Color.WHITE, Color.WHITE,
                Color.WHITE, Color.WHITE,
                Color.WHITE, Color.WHITE,
                Color.WHITE, Color.WHITE,
                Color.WHITE, Color.WHITE,
        };
        public static final double BOARD_BOARDER_PERCENT = 0.08;
        public static final double INNER_BLOCK_PERCENT = 0.9;
        public static final double MINIMUM_MOVING_DISTANCE_ON_FLING = 50;
        public static final double MINIMUM_MOVING_VELOCITY_ON_FLING = 1000;
        public static final float BLOCK_SPAWN_SCALE_FROM_PERCENT = 0.5f;
        public static final int DEFAULT_ANIMATION_DURATION_MILLISECONDS = 100;
        public static final int BLOCK_ROUND_RAD = 20;
        public static final int BLOCK_FONT_SIZE = 75;
        public static final int BLOCK_FONT_SIZE_MIN = 40;
        public static final int DOUBLE_HIT_INTERVAL = 2000;

    }

    public static class Game {

        public static final int HISTORY_SIZE = 21;
        public static final double RANK_2_PROBABILITY = 0.1;
        public static final int DEFAULT_BOARD_SIZE = 4;
    }

    public static class Runtime {

        public static int ANIMATION_DURATION_MILLISECONDS =
                UI.DEFAULT_ANIMATION_DURATION_MILLISECONDS;
        public static int BOARD_SIZE = Game.DEFAULT_BOARD_SIZE;
        public static String[] STRING_LIST = UI.STRING_LIST_CLASSICAL;
    }

    public static class Sound {

        //0 dota版 ； 1 lol版 ； 2  开心消消乐版 ； 3 简单版
        public static boolean enabled = true;
        public static int SOUND_PACK = 1;
    }
}
