package com.bjtu.zero.a2048.ui;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScoreBoardLayout extends LinearLayout {

    public int now;
    public int high;
    protected TextView score;
    protected TextView highScore;
    protected TextView textView;
    protected TextView textView1;
    protected TextView textView2;

    public ScoreBoardLayout(Context context) {
        super(context);
        setOrientation(HORIZONTAL);
        setMinimumHeight(300);
        setGravity(Gravity.CENTER);
        textView = new TextView(context);
        textView.setText("2048");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
        textView.setGravity(Gravity.CENTER);
        addView(textView);
        LinearLayout vertical1 = new LinearLayout(context);
        vertical1.setOrientation(VERTICAL);
        vertical1.setMinimumWidth(300);
        addView(vertical1);
        LinearLayout vertical2 = new LinearLayout(context);
        vertical2.setOrientation(VERTICAL);
        vertical2.setMinimumWidth(300);
        addView(vertical2);
        textView1 = new TextView(context);
        textView1.setText("分数");
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        textView1.setGravity(Gravity.CENTER);
        vertical1.addView(textView1);
        score = new TextView(context);
        score.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        score.setGravity(Gravity.CENTER);
        vertical1.addView(score);
        textView2 = new TextView(context);
        textView2.setText("最高分");
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        textView2.setGravity(Gravity.CENTER);
        vertical2.addView(textView2);
        highScore = new TextView(context);
        highScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        highScore.setGravity(Gravity.CENTER);
        vertical2.addView(highScore);
        setScore(0);
        setHighScore(0);
    }

    public void setScore(int a) {
        now = a;
        score.setText(String.valueOf(now));
    }

    public void setHighScore(int a) {
        high = a;
        highScore.setText(String.valueOf(high));
    }
}
