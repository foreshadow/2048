package com.bjtu.zero.a2048;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bjtu.zero.a2048.core.GamePresenter;
import com.bjtu.zero.a2048.ui.GameLayout;
import com.bjtu.zero.a2048.ui.UndoButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private UndoButton undoButton;
    private GameLayout gameLayout;
    private GamePresenter gamePresenter;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout topButtonLayout = new LinearLayout(linearLayout.getContext());
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
                gamePresenter.setGameLayout(gameLayout);
                gameLayout.setBoard(gamePresenter.getGameModel().lastBoard());
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
                gamePresenter.setGameLayout(gameLayout);
                gameLayout.setBoard(gamePresenter.getGameModel().lastBoard());
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
        Point windowSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(windowSize);
        gamePresenter = new GamePresenter();
        gameLayout = new GameLayout(linearLayout.getContext(), windowSize.x, gamePresenter);
        linearLayout.addView(gameLayout);
        setContentView(linearLayout);
        gamePresenter.setGameLayout(gameLayout);
        gestureDetector = new GestureDetector(gameLayout.getContext(), new GestureDetector.OnGestureListener() {
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
        });
        gameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });
        gameLayout.setLongClickable(true);
        gamePresenter.loadSound(this);
        gamePresenter.start();
    }
}
