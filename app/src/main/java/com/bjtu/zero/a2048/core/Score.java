package com.bjtu.zero.a2048.core;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by asus-pc on 2016/7/28.
 */
public class Score {
    private static final String FILE_NAME = "sorce.txt";
    public final String KEY = "HIGH";
    public TextView score;
    public TextView highScore;
    public TextView t1;
    public TextView t2;
    public int now;
    public int high;
    SharedPreferences sp = null;
    SharedPreferences.Editor editor = null;

    //public Score(){

    //}

    public Score(Context con) {
        this.sp = con.getSharedPreferences("test", Activity.MODE_PRIVATE);
        this.editor = sp.edit();
    }

    public void init() {
        String a = sp.getString(KEY, "0");
        Log.e("aaaaa", a);
        setHighScore(Integer.parseInt(a));
        Log.e("aaaaa", "Score");
    }

    public void setScore(int a) {
        now = a;
        score.setText(String.valueOf(now));
        Log.e("aaaaa", "setScore");
    }

    public void setHighScore(int a) {
        high = a;
        highScore.setText(String.valueOf(high));
        editor.putString(KEY, String.valueOf(high));
        editor.commit();
        Log.e("aaaaa", "setHigh");
    }

}
