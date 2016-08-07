package com.bjtu.zero.a2048;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bjtu.zero.a2048.core.GamePresenter;
import com.bjtu.zero.a2048.ui.GameLayout;
import com.bjtu.zero.a2048.ui.ScoreBoardLayout;
import com.bjtu.zero.a2048.ui.UndoButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnTouchListener, OnGestureListener {

    private UndoButton undoButton;
    private GameLayout gameLayout;
    private GamePresenter gamePresenter;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        ScoreBoardLayout scoreBoardLayout = new ScoreBoardLayout(linearLayout.getContext());
        linearLayout.addView(scoreBoardLayout);
        Point windowSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(windowSize);
        gamePresenter = new GamePresenter();
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
                undoButton.update(gamePresenter.getGameModel().size());
            }
        });
        Button restartButton = new Button(topButtonLayout.getContext());
        restartButton.setText("Restart");
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gamePresenter.reset();
                undoButton.update(gamePresenter.getGameModel().size());
            }
        });
        Button autoButton1 = new Button(topButtonLayout.getContext());
        autoButton1.setText("Auto 50");
        autoButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setting.Runtime.ANIMATION_DURATION_MILLISECONDS = 1;
                gamePresenter.setGameLayout(null);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 50; i++) {
                            switch (new Random().nextInt(4)) {
                                case 0:
                                    gamePresenter.slideLeft();
                                    break;
                                case 1:
                                    gamePresenter.slideUp();
                                    break;
                                case 2:
                                    gamePresenter.slideRight();
                                    break;
                                case 3:
                                    gamePresenter.slideDown();
                                    break;
                            }
                        }
                    }
                });
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                gameLayout.setBoard(gamePresenter.getGameModel().lastBoard());
                gamePresenter.setGameLayout(gameLayout);
                Setting.Runtime.ANIMATION_DURATION_MILLISECONDS =
                        Setting.UI.DEFAULT_ANIMATION_DURATION_MILLISECONDS;
                undoButton.update(gamePresenter.getGameModel().size());
            }
        });
        Button autoButton2 = new Button(topButtonLayout.getContext());
        autoButton2.setText("Auto 500");
        autoButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setting.Runtime.ANIMATION_DURATION_MILLISECONDS = 1;
                gamePresenter.setGameLayout(null);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 500; i++) {
                            switch (new Random().nextInt(4)) {
                                case 0:
                                    gamePresenter.slideLeft();
                                    break;
                                case 1:
                                    gamePresenter.slideUp();
                                    break;
                                case 2:
                                    gamePresenter.slideRight();
                                    break;
                                case 3:
                                    gamePresenter.slideDown();
                                    break;
                            }
                        }
                    }
                });
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                gameLayout.setBoard(gamePresenter.getGameModel().lastBoard());
                gamePresenter.setGameLayout(gameLayout);
                Setting.Runtime.ANIMATION_DURATION_MILLISECONDS =
                        Setting.UI.DEFAULT_ANIMATION_DURATION_MILLISECONDS;
                undoButton.update(gamePresenter.getGameModel().size());

            }
        });
        topButtonLayout.addView(undoButton);
        topButtonLayout.addView(restartButton);
        topButtonLayout.addView(autoButton1);
        topButtonLayout.addView(autoButton2);
        linearLayout.addView(topButtonLayout);
        setContentView(linearLayout);
        gamePresenter.setGameLayout(gameLayout);
        gestureDetector = new GestureDetector(gameLayout.getContext(), this);
        gameLayout.setOnTouchListener(this);
        gameLayout.setLongClickable(true);
        gamePresenter.loadSound(this);
        gamePresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gamePresenter.read();
        undoButton.update(gamePresenter.getGameModel().size());
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
            undoButton.update(gamePresenter.getGameModel().size());
            return true;
        }
        return false;
    }
}
