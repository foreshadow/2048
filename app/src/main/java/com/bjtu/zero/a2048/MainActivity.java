package com.bjtu.zero.a2048;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private GameLayout gl;
    private Game game;

    private GestureDetector gd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        Point windowSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(windowSize);
        gl = new GameLayout(ll.getContext(), windowSize.x);
        ll.addView(gl);
        setContentView(ll);
        game = new Game(gl);
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
    }

    public void gameOver() {
        // TODO: 2016/7/21  
    }
}
