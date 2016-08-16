package com.bjtu.zero.a2048.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjtu.zero.a2048.Setting;

public class ScoreBoardLayout extends LinearLayout {

    public final String KEY = "HIGH";
    protected TextView scoreView;
    protected TextView highScoreView;
    protected TextView textView;
    protected TextView textView1;
    protected TextView textView2;
    private SharedPreferences sp;
    private int currentScore;
    private int highestScore;

    public ScoreBoardLayout(Context context) {
        super(context);
        this.sp = context.getSharedPreferences("test", Activity.MODE_PRIVATE);
        currentScore = 0;
        highestScore = 0;
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
        scoreView = new TextView(context);
        setScore(currentScore);
        //scoreView.setText(String.valueOf(currentScore));
        scoreView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        scoreView.setGravity(Gravity.CENTER);
        vertical1.addView(scoreView);
        textView2 = new TextView(context);
        textView2.setText("最高分");
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        textView2.setGravity(Gravity.CENTER);
        vertical2.addView(textView2);
        highScoreView = new TextView(context);

        String a = sp.getString(KEY, "0");
        Log.e("aaaaa", a);
        setHighScore(Integer.parseInt(a));

        highScoreView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        highScoreView.setGravity(Gravity.CENTER);
        vertical2.addView(highScoreView);
    }

    private int refitText(String text) {
        int textWidth = (int) (600. / Setting.Runtime.BOARD_SIZE * 0.75);
        Log.e("ccccc", "wid=" + String.valueOf(textWidth));
        int minTextSize = 15;
        int maxTextSize = 40;
        if (textWidth > 0) {
            int trySize = maxTextSize;
            while ((trySize > minTextSize) && trySize * text.toCharArray().length >= textWidth) {
                trySize -= 1;
                if (trySize <= minTextSize) {
                    trySize = minTextSize;
                    break;
                }
            }
            Log.e("ccccc", "historySize = " + String.valueOf(trySize));
            return trySize;

        }
        return maxTextSize;
    }

    public void setScore(int score) {
        currentScore = score;
        int size = refitText(String.valueOf(score));
        scoreView.setTextSize(size);
        scoreView.setText(String.valueOf(currentScore));
        if (score > highestScore) {
            highestScore = score;
            highScoreView.setText(String.valueOf(highestScore));
        }
    }

    public void setHighScore(int highScore) {
        highestScore = highScore;
        int size = refitText(String.valueOf(highScore));
        highScoreView.setTextSize(size);
        highScoreView.setText(String.valueOf(highScore));
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY, String.valueOf(highScore));
        editor.commit();
        Log.e("aaaaa", "setHigh");
    }
}
