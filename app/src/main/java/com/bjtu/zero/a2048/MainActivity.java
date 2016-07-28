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

    private UndoButton btn_undo;
    private Button btn_restart;
    private Button btn_auto_1;
    private Button btn_auto_2;
    private GameLayout gl;
    private GamePresenter gamePresenter;
    private GestureDetector gd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll_v = new LinearLayout(this);
        ll_v.setOrientation(LinearLayout.VERTICAL);
        LinearLayout ll_h = new LinearLayout(ll_v.getContext());
        btn_undo = new UndoButton(ll_h.getContext());
        btn_undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("UI", "undo clicked");
                gamePresenter.undo();
                btn_undo.update(gamePresenter.getModel().size());
            }
        });
        btn_restart = new Button(ll_h.getContext());
        btn_restart.setText("Restart");
        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gamePresenter.reset();
                btn_undo.update(gamePresenter.getModel().size());
            }
        });
        btn_auto_1 = new Button(ll_h.getContext());
        btn_auto_1.setText("Auto 50");
        btn_auto_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setting.Runtime.ANIMATION_DURATION_MILLISECONDS = 1;
                gamePresenter.setLayout(null);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 50; i++) {
                            switch ((new Random()).nextInt(4)) {
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
                gamePresenter.setLayout(gl);
                gl.setBoard(gamePresenter.getModel().lastBoard());
                Setting.Runtime.ANIMATION_DURATION_MILLISECONDS =
                        Setting.UI.DEFAULT_ANIMATION_DURATION_MILLISECONDS;
                btn_undo.update(game.getHistory().size());
            }
        });
        btn_auto_2 = new Button(ll_h.getContext());
        btn_auto_2.setText("Auto 500");
        btn_auto_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setting.Runtime.ANIMATION_DURATION_MILLISECONDS = 1;
                gamePresenter.setLayout(null);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 500; i++) {
                            switch ((new Random()).nextInt(4)) {
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
                gamePresenter.setLayout(gl);
                gl.setBoard(gamePresenter.getModel().lastBoard());
                Setting.Runtime.ANIMATION_DURATION_MILLISECONDS =
                        Setting.UI.DEFAULT_ANIMATION_DURATION_MILLISECONDS;
                btn_undo.update(gamePresenter.getModel().size());
            }
        });
        ll_h.addView(btn_undo);
        ll_h.addView(btn_restart);
        ll_h.addView(btn_auto_1);
        ll_h.addView(btn_auto_2);
        ll_v.addView(ll_h);
        Point windowSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(windowSize);
        gamePresenter = new GamePresenter();
        gl = new GameLayout(ll_v.getContext(), windowSize.x, gamePresenter);
        ll_v.addView(gl);
        setContentView(ll_v);
        game.setLayout(gl);
        game.loadSound(this);
        gamePresenter.setLayout(gl);
        gd = new GestureDetector(gl.getContext(), new GestureDetector.OnGestureListener() {
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
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                double dx = motionEvent1.getX() - motionEvent.getX();
                double dy = motionEvent1.getY() - motionEvent.getY();
                if (Math.sqrt(dx * dx + dy * dy) > Setting.UI.MINIMUM_MOVING_DISTANCE_ON_FLING) {
                    if (dx > dy && dx > -dy) {
                        gamePresenter.slideRight();
                    } else if (dx < dy && dx < -dy) {
                        gamePresenter.slideLeft();
                    } else if (dy > dx && dy > -dx) {
                        gamePresenter.slideDown();
                    } else if (dy < dx && dy < -dx) {
                        gamePresenter.slideUp();
                    }
                    btn_undo.update(gamePresenter.getModel().size());
                    return true;
                }
                return false;
            }
        });
        gl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gd.onTouchEvent(motionEvent);
            }
        });
        gl.setLongClickable(true);
        gamePresenter.start();
    }
}
