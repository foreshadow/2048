package com.bjtu.zero.a2048;

import android.app.Activity;
import android.content.Intent;
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

public class MainActivity extends Activity
        implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener {

    private UndoButton undoButton;
    private GameLayout gameLayout;
    private GamePresenter gamePresenter;
    private GestureDetector gestureDetector;
    private long exitTime = 0;

    private LinearLayout linearLayout;
    private ScoreBoardLayout scoreBoardLayout;
    private Point windowSize;

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
        Setting.gamePresenter = gamePresenter ;
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

        Button SettingButton = new Button(topButtonLayout.getContext());
        SettingButton.setText("菜单");
        SettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this,GameOverActivity .class);
                Intent intent = new Intent(MainActivity.this,NewGameActivity.class);
                startActivity(intent);
            }
        });

        topButtonLayout.addView(undoButton);
        topButtonLayout.addView(SettingButton);
        linearLayout.addView(topButtonLayout);



        setContentView(linearLayout);
        gamePresenter.setGameLayout(gameLayout);
        gestureDetector = new GestureDetector(gameLayout.getContext(), this);
        gameLayout.setOnTouchListener(this);
        gameLayout.setLongClickable(true);

        Log.e("aaa","onCreat "+ Setting.savemodel);
        gamePresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //gamePresenter.read();
        Log.e("aaa","onResume");
        if (Setting.savemodel == 0) {
            gamePresenter.read();
            Setting.gamePresenter = gamePresenter ;
        } else if (Setting.savemodel == 2) {
            gamePresenter.read(1);
            Setting.gamePresenter = gamePresenter ;
        } else if (Setting.savemodel == 3) {
            gamePresenter.read(2);
            Setting.gamePresenter = gamePresenter ;
        } else if (Setting.savemodel == 4) {
            gamePresenter.read(3);
            Setting.gamePresenter = gamePresenter ;
        }
        undoButton.update(gamePresenter.getGameModel().historySize());

    }

    @Override
    protected void onPause() {
        super.onPause();
        gamePresenter.write();
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
            if(gamePresenter.isGameOver()){
                Intent intent = new Intent(MainActivity.this,GameOverActivity.class);
                startActivity(intent);
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
