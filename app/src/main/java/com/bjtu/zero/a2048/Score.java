package com.bjtu.zero.a2048;

import android.widget.TextView;

/**
 * Created by asus-pc on 2016/7/28.
 */
public class Score {
    protected TextView score;
    protected TextView highScore;
    protected TextView t1;
    protected TextView t2;
    protected int now;
    protected int high;


    public Score() {
        now = 0;
        high = 0;
    }

    public void setScore(int a){
        now = a;
        score.setText(String.valueOf(now));
    }

    public void setHighScore(int a){
        high = a;
        highScore.setText(String.valueOf(high));
    }
}
