package com.bjtu.zero.a2048.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjtu.zero.a2048.R;
import com.bjtu.zero.a2048.Setting;

import java.io.Serializable;

public class ScoreBoardLayout extends LinearLayout implements Serializable {

    private final String KEY = "HIGH";
    private final TextView scoreView;
    private final TextView highScoreView;
    private final TextView textView;
    private final TextView textView1;
    private final TextView textView2;
    private final SharedPreferences sp;
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
        textView.setText(R.string.title);
        ImageView imageView = new ImageView(context);  //创建ImageView
        imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));  //image的布局方式
        imageView.setImageResource(R.drawable.show);  //设置ImageView呈现的图片
        this.addView(imageView);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
        textView.setGravity(Gravity.CENTER);
        //addView(textView);
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
        scoreView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        scoreView.setGravity(Gravity.CENTER);
        vertical1.addView(scoreView);
        textView2 = new TextView(context);
        textView2.setText("最高分");
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        textView2.setGravity(Gravity.CENTER);
        vertical2.addView(textView2);
        highScoreView = new TextView(context);

        //序列化读取最高分
        String a = sp.getString(KEY, "0");
        Log.e("aaaaa", a);
        setHighScore(Integer.parseInt(a));

        highScoreView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        highScoreView.setGravity(Gravity.CENTER);
        vertical2.addView(highScoreView);
    }

    /**
     * 动态调整文本的字体大小
     *
     * @param text 需要显示的文本
     * @return 文本大小
     */
    private int refitText(String text) {
        int textWidth = (int) (600. / Setting.Runtime.BOARD_SIZE * 0.75);
        Log.e("ccccc", "wid=" + String.valueOf(textWidth));
        int minTextSize = 15;
        int maxTextSize = 40;
        if (textWidth > 0) {
            int trySize = maxTextSize;
            while (trySize > minTextSize && trySize * text.toCharArray().length >= textWidth) {
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

    /**
     * 设置分数
     *
     * @param score 当前分数
     */
    public void setScore(int score) {
        currentScore = score;
        int size = refitText(String.valueOf(score));
        scoreView.setTextSize(size);
        scoreView.setText(String.valueOf(currentScore));
        //如果当前分数高于最高分，需要设置最高分
        if (score > highestScore) {
            setHighScore(score);
        }
        Log.e("aaaaa", "setScore " + score);
        Log.e("aaaaa", "high " + highestScore);
    }

    /**
     * 设置最高分
     *
     * @param highScore 最高分
     */
    public void setHighScore(int highScore) {
        String loadedScoreString = sp.getString(KEY, "0");
        int loadedScore = Integer.parseInt(loadedScoreString);
        Log.e("aaaaa", "setHigh aa " + highScore);
        if (loadedScore > highScore) {
            highScore = loadedScore;
        }
        highestScore = highScore;
        int size = refitText(String.valueOf(highScore));
        highScoreView.setTextSize(size);
        highScoreView.setText(String.valueOf(highScore));
        //序列化存入最高分
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY, String.valueOf(highScore));
        editor.apply();
        Log.e("aaaaa", "setHigh " + highScore);
    }
}
