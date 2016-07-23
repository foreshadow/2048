package com.bjtu.zero.a2048;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    GameLayout gl;
    Game game;

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
    }

    public void gameOver() {
        // TODO: 2016/7/21  
    }
}
