package com.bjtu.zero.a2048;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.content.Intent;
import android.graphics.BitmapFactory;
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

public class MainActivity extends Activity
        implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener {

    private UndoButton undoButton;
    private GameLayout gameLayout;
    private GestureDetector gestureDetector;
    private long exitTime = 0;

    private LinearLayout linearLayout;
    private ScoreBoardLayout scoreBoardLayout;
    private Point windowSize;
    private Bitmap m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        scoreBoardLayout = new ScoreBoardLayout(linearLayout.getContext());
        linearLayout.addView(scoreBoardLayout);
        windowSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(windowSize);
        Setting.gamePresenter = new GamePresenter(Setting.Runtime.BOARD_SIZE);
        Setting.gamePresenter.setScoreBoard(scoreBoardLayout);
        Setting.gamePresenter.setContext(linearLayout.getContext());
        gameLayout = new GameLayout(linearLayout.getContext(), windowSize.x, windowSize.x, Setting.gamePresenter);

        linearLayout.addView(gameLayout);
        LinearLayout topButtonLayout = new LinearLayout(linearLayout.getContext());
        topButtonLayout.setMinimumHeight(200);
        topButtonLayout.setGravity(Gravity.CENTER);
        undoButton = new UndoButton(topButtonLayout.getContext());
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("UI", "undo clicked");
                Setting.gamePresenter.undo();
                undoButton.update(Setting.gamePresenter.getGameModel().historySize());
            }
        });

        Button SettingButton = new Button(topButtonLayout.getContext());
        SettingButton.setText("菜单");
        SettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(MainActivity.this, SoundSettingMenu.class);
                startActivity(i);
            }
        });

        topButtonLayout.addView(undoButton);
        topButtonLayout.addView(SettingButton);
        linearLayout.addView(topButtonLayout);



        setContentView(linearLayout);
        Setting.gamePresenter.setGameLayout(gameLayout);
        gestureDetector = new GestureDetector(gameLayout.getContext(), this);
        gameLayout.setOnTouchListener(this);
        gameLayout.setLongClickable(true);

        Log.e("aaa","onCreat "+ Setting.savemodel);
        Setting.gamePresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //gamePresenter.read();
        Log.e("aaa","onResume");
        if (Setting.savemodel == 0) {
            Setting.gamePresenter.read();
        } else if (Setting.savemodel == 2) {
            Setting.gamePresenter.read(1);
        } else if (Setting.savemodel == 3) {
            Setting.gamePresenter.read(2);
        } else if (Setting.savemodel == 4) {
            Setting.gamePresenter.read(3);
        }
        undoButton.update(Setting.gamePresenter.getGameModel().historySize());

    }

    @Override
    protected void onPause() {
        super.onPause();
        Setting.gamePresenter.write();
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
                Setting.gamePresenter.slideRight();
            } else if (dx < dy && dx < -dy) {
                Setting.gamePresenter.slideLeft();
            } else if (dy > dx && dy > -dx) {
                Setting.gamePresenter.slideDown();
            } else if (dy < dx && dy < -dx) {
                Setting.gamePresenter.slideUp();
            }
            undoButton.update(Setting.gamePresenter.getGameModel().historySize());
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
            Log.e("aaaaa", "write "+i);
            fos = openFileOutput("image" + String.valueOf(i) + ".txt", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            Log.e("aaaaa", "write111 "+i);
            m = Setting.gamePresenter.getGameModel().lastStatus().thumbnail();
            oos.writeObject(m.compress(Bitmap.CompressFormat.JPEG,100,fos));
            fos.close();
            fos = openFileOutput("score" + String.valueOf(i) + ".txt", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(Setting.gamePresenter.getGameModel().lastStatus().getScore());
            Log.e("aaaaa", "write ok "+i);
            fos.close();
            SimpleDateFormat sDateFormat  =  new SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");
            String date = sDateFormat.format(new  java.util.Date());
            fos = openFileOutput("time" + String.valueOf(i) + ".txt", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(date);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    //fos流关闭异常
                    e.printStackTrace();
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    //oos流关闭异常
                    e.printStackTrace();
                }
            }
        }
    }
}
