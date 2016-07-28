package com.bjtu.zero.a2048;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private UndoButton btn_undo;
    private Button btn_restart;
    private Button btn_auto_1;
    private Button btn_auto_2;
    private GameLayout gl;
    private Game game;
    private GestureDetector gd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new Game();
        LinearLayout ll_v = new LinearLayout(this);
        ll_v.setOrientation(LinearLayout.VERTICAL);
        LinearLayout ll_h = new LinearLayout(ll_v.getContext());
        LinearLayout ll_h1 = new LinearLayout(ll_v.getContext());
        btn_undo = new UndoButton(ll_h.getContext());
        btn_undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("UI", "undo clicked");
                game.undo();
                btn_undo.update(game.getHistory().size());
            }
        });
        btn_restart = new Button(ll_h.getContext());
        btn_restart.setText("Restart");
        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.reset();
                btn_undo.update(game.getHistory().size());
            }
        });
        btn_auto_1 = new Button(ll_h.getContext());
        btn_auto_1.setText("Auto 5");
        btn_auto_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setting.Runtime.ANIMATION_DURATION_MILLISECONDS = 1;
                game.setLayout(null);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 5; i++) {
                            switch ((new Random()).nextInt(4)) {
                                case 0:
                                    game.slideLeft();
                                    break;
                                case 1:
                                    game.slideUp();
                                    break;
                                case 2:
                                    game.slideRight();
                                    break;
                                case 3:
                                    game.slideDown();
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
                game.setLayout(gl);
                gl.setBoard(game.getHistory().getLast().getBoard());
                Setting.Runtime.ANIMATION_DURATION_MILLISECONDS =
                        Setting.UI.DEFAULT_ANIMATION_DURATION_MILLISECONDS;
                btn_undo.update(game.getHistory().size());
            }
        });
        btn_auto_2 = new Button(ll_h.getContext());
        btn_auto_2.setText("Auto 30");
        btn_auto_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setting.Runtime.ANIMATION_DURATION_MILLISECONDS = 1;
                game.setLayout(null);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 30; i++) {
                            switch ((new Random()).nextInt(4)) {
                                case 0:
                                    game.slideLeft();
                                    break;
                                case 1:
                                    game.slideUp();
                                    break;
                                case 2:
                                    game.slideRight();
                                    break;
                                case 3:
                                    game.slideDown();
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
                game.setLayout(gl);
                gl.setBoard(game.getHistory().getLast().getBoard());
                Setting.Runtime.ANIMATION_DURATION_MILLISECONDS =
                        Setting.UI.DEFAULT_ANIMATION_DURATION_MILLISECONDS;
                btn_undo.update(game.getHistory().size());
            }
        });


        ll_h.addView(btn_undo);
        ll_h.addView(btn_restart);
        ll_h.addView(btn_auto_1);
        ll_h.addView(btn_auto_2);
        //ll_h1.addView(game.s.t1);
        //ll_h1.addView(game.s.score);
        //ll_h1.addView(game.s.t2);
        //ll_h1.addView(game.s.highScore);
        ll_v.addView(ll_h);
        ll_v.addView(ll_h1);
        Point windowSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(windowSize);
        game = new Game();
        gl = new GameLayout(ll_v.getContext(), windowSize.x, game,game.s);
        ll_v.addView(gl);
        setContentView(ll_v);
        game.setLayout(gl);
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
                        game.slideRight();
                    } else if (dx < dy && dx < -dy) {
                        game.slideLeft();
                    } else if (dy > dx && dy > -dx) {
                        game.slideDown();
                    } else if (dy < dx && dy < -dx) {
                        game.slideUp();
                    }
                    btn_undo.update(game.getHistory().size());
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
        game.start();
    }
}
