package com.bjtu.zero.a2048;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bjtu.zero.a2048.core.GamePresenter;
import com.bjtu.zero.a2048.ui.DoubleClickDetector;
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
        GestureDetector.OnGestureListener,
        DoubleClickDetector.ClickListener {

    private UndoButton undoButton;
    private GameLayout gameLayout;
    private GamePresenter gamePresenter;
    private GestureDetector gestureDetector;
    private long exitTime = 0;

    private DoubleClickDetector doubleClickDetector;
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
        gamePresenter = new GamePresenter(Setting.Runtime.BOARD_SIZE);
        gamePresenter.setScoreBoard(scoreBoardLayout);
        gamePresenter.setContext(linearLayout.getContext());
        gameLayout = new GameLayout(linearLayout.getContext(), windowSize.x, windowSize.x, gamePresenter);

        linearLayout.addView(gameLayout);
        LinearLayout topButtonLayout = new LinearLayout(linearLayout.getContext());
        topButtonLayout.setMinimumHeight(200);
        topButtonLayout.setGravity(Gravity.CENTER);
        undoButton = new UndoButton(topButtonLayout.getContext());
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("UI", "undo clicked");
                gamePresenter.undo();
                undoButton.update(gamePresenter.getGameModel().historySize());
            }
        });
        Button restartButton = new Button(topButtonLayout.getContext());
        restartButton.setText("Restart");
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //测试代码
                //gamePresenter.reset();
                undoButton.update(gamePresenter.getGameModel().historySize());
            }
        });
        topButtonLayout.addView(undoButton);
        topButtonLayout.addView(restartButton);
        linearLayout.addView(topButtonLayout);

        LinearLayout ButtonLayout = new LinearLayout(linearLayout.getContext());
        ButtonLayout.setMinimumHeight(200);
        ButtonLayout.setGravity(Gravity.CENTER);
        Button Button1 = new Button(ButtonLayout.getContext());
        Button1.setText("save 1");
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("UI", "undo clicked");
                m = gamePresenter.getGameModel().lastStatus().thumbnail();
                write(1);
                gamePresenter.write(1);
            }
        });
        Button Button2 = new Button(ButtonLayout.getContext());
        Button2.setText("save 2");
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("UI", "undo clicked");
                m = gamePresenter.getGameModel().lastStatus().thumbnail();
                write(2);
                gamePresenter.write(2);
            }
        });
        Button Button3 = new Button(ButtonLayout.getContext());
        Button3.setText("save 3");
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("UI", "undo clicked");
                m = gamePresenter.getGameModel().lastStatus().thumbnail();
                write(3);
                gamePresenter.write(3);
            }
        });
        ButtonLayout.addView(Button1);
        ButtonLayout.addView(Button2);
        ButtonLayout.addView(Button3);
        linearLayout.addView(ButtonLayout);



        setContentView(linearLayout);
        gamePresenter.setGameLayout(gameLayout);
        gestureDetector = new GestureDetector(gameLayout.getContext(), this);
        doubleClickDetector = new DoubleClickDetector(Setting.UI.DOUBLE_HIT_INTERVAL, this);
        gameLayout.setOnTouchListener(this);
        gameLayout.setLongClickable(true);
        gamePresenter.loadSound(this);

        Log.e("aaa","onCreat "+ Setting.savemodel);
        gamePresenter.start();
    }

    public void write(int i) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            Log.e("aaaaa", "write "+i);
            fos = openFileOutput("image" + String.valueOf(i) + ".txt", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            Log.e("aaaaa", "write111 "+i);
            oos.writeObject(m.compress(Bitmap.CompressFormat.JPEG,100,fos));
            fos.close();
            fos = openFileOutput("score" + String.valueOf(i) + ".txt", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(gamePresenter.getGameModel().lastStatus().getScore());
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

    @Override
    protected void onResume() {
        super.onResume();
        //gamePresenter.read();
        Log.e("aaa","onResume");
        if (Setting.savemodel == 0) {
            gamePresenter.read();
        } else if (Setting.savemodel == 2) {
            gamePresenter.read(1);
        } else if (Setting.savemodel == 3) {
            gamePresenter.read(2);
        } else if (Setting.savemodel == 4) {
            gamePresenter.read(3);
        }
        undoButton.update(gamePresenter.getGameModel().historySize());

    }

    @Override
    protected void onPause() {
        super.onPause();
        gamePresenter.write();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            doubleClickDetector.onClick();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onFirstClick() {
        gamePresenter.write();
        Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSecondClick() {
        finish();
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
                gamePresenter.slideRight();
            } else if (dx < dy && dx < -dy) {
                gamePresenter.slideLeft();
            } else if (dy > dx && dy > -dx) {
                gamePresenter.slideDown();
            } else if (dy < dx && dy < -dx) {
                gamePresenter.slideUp();
            }
            undoButton.update(gamePresenter.getGameModel().historySize());
            return true;
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
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

}
