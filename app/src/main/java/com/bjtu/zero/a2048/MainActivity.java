package com.bjtu.zero.a2048;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bjtu.zero.a2048.core.GamePresenter;
import com.bjtu.zero.a2048.ui.GameLayout;
import com.bjtu.zero.a2048.ui.ScoreBoardLayout;
import com.bjtu.zero.a2048.ui.UndoButton;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends Activity
        implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener {

    private UndoButton undoButton;
    private GestureDetector gestureDetector;
    private long exitTime = 0;
    public static  MainActivity  instance=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("gao","build a MainActivity");
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        ScoreBoardLayout scoreBoardLayout = new ScoreBoardLayout(linearLayout.getContext());
        linearLayout.addView(scoreBoardLayout);
        Point windowSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(windowSize);
        Setting.Runtime.gamePresenter = new GamePresenter(Setting.Runtime.BOARD_SIZE);
        Setting.Runtime.gamePresenter.setScoreBoard(scoreBoardLayout);
        Setting.Runtime.gamePresenter.setContext(linearLayout.getContext());
        GameLayout gameLayout = new GameLayout(linearLayout.getContext(),
                windowSize.x, windowSize.x, Setting.Runtime.gamePresenter);
        // deliberately...... ^
        linearLayout.addView(gameLayout);
        LinearLayout topButtonLayout = new LinearLayout(linearLayout.getContext());
        topButtonLayout.setMinimumHeight(200);
        topButtonLayout.setGravity(Gravity.CENTER);
        undoButton = new UndoButton(topButtonLayout.getContext());
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("UI", "undo clicked");
                Setting.Runtime.gamePresenter.undo();
                undoButton.update(Setting.Runtime.gamePresenter.getGameModel().historySize());
            }
        });

        Button SettingButton = new Button(topButtonLayout.getContext());
        SettingButton.setText("菜单");
        SettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this,GameOverActivity .class);
                Intent intent = new Intent(MainActivity.this, SoundSettingMenuActivity.class);
                startActivity(intent);
            }
        });

        topButtonLayout.addView(undoButton);
        topButtonLayout.addView(SettingButton);
        linearLayout.addView(topButtonLayout);

        setContentView(linearLayout);
        instance=this;
        Setting.Runtime.gamePresenter.setGameLayout(gameLayout);
        gestureDetector = new GestureDetector(gameLayout.getContext(), this);
        gameLayout.setOnTouchListener(this);
        gameLayout.setLongClickable(true);

        Log.e("aaa", "onCreate " + Setting.Runtime.FILE_ID);
        Setting.Runtime.gamePresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //gamePresenter.read();
        Log.e("aaa", "onResume");
        if (Setting.Runtime.FILE_ID == 0) {
            Setting.Runtime.gamePresenter.read();
        } else {
            Setting.Runtime.gamePresenter.read(Setting.Runtime.FILE_ID - 1);
        }
        undoButton.update(Setting.Runtime.gamePresenter.getGameModel().historySize());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Setting.Runtime.gamePresenter.write();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float vx, float vy) {
        double dx = motionEvent1.getX() - motionEvent.getX();
        double dy = motionEvent1.getY() - motionEvent.getY();
        Log.e("UIOPERATION", String.format("FLING d=(%f, %f) v=(%f, %f)", dx, dy, vx, vy));
        if (Math.sqrt(dx * dx + dy * dy) > Setting.UI.MINIMUM_MOVING_DISTANCE_ON_FLING
                && Math.sqrt(vx * vx + vy * vy) > Setting.UI.MINIMUM_MOVING_VELOCITY_ON_FLING) {
            if (dx > dy && dx > -dy) {
                Setting.Runtime.gamePresenter.slideRight();
            } else if (dx < dy && dx < -dy) {
                Setting.Runtime.gamePresenter.slideLeft();
            } else if (dy > dx && dy > -dx) {
                Setting.Runtime.gamePresenter.slideDown();
            } else if (dy < dx && dy < -dx) {
                Setting.Runtime.gamePresenter.slideUp();
            }
            //判断游戏结束
//            if (Setting.Runtime.gamePresenter.isGameOver()) {
//                Intent intent = new Intent(MainActivity.this, GameOverActivity.class);
//                startActivity(intent);
//            }
            undoButton.update(Setting.Runtime.gamePresenter.getGameModel().historySize());
            return true;
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    public void write(int i) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            Log.e("aaaaa", "write " + i);
            fos = openFileOutput("image" + String.valueOf(i) + ".txt", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            Log.e("aaaaa", "write111 " + i);
            Bitmap m = Setting.Runtime.gamePresenter.getGameModel().lastStatus().thumbnail();
            oos.writeObject(m.compress(Bitmap.CompressFormat.JPEG, 100, fos));
            fos.close();
            fos = openFileOutput("score" + String.valueOf(i) + ".txt", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(Setting.Runtime.gamePresenter.getGameModel().lastStatus().getScore());
            Log.e("aaaaa", "write ok " + i);
            fos.close();
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
            String date = sDateFormat.format(new java.util.Date());
            fos = openFileOutput("time" + String.valueOf(i) + ".txt", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(date);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
