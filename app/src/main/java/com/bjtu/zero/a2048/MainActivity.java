package com.bjtu.zero.a2048;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GameLayout gl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new FrameLayout.LayoutParams(1000, 1000));
        gl = new GameLayout(ll.getContext());
        ll.addView(gl);
        setContentView(ll);
    }

    @Override
    protected void onResume() {
        super.onResume();

        BlockView blockView = new BlockView(gl.getContext());
        gl.addView(blockView);
        Animation animation = new TranslateAnimation(0, 500, 0, 500);
        animation.setDuration(200);
        animation.setRepeatMode(Animation.INFINITE);
        blockView.startAnimation(animation);
        blockView.setLayoutParams(new FrameLayout.LayoutParams(500, 500));
    }

    public void update(ArrayList<Block> blocks) { //直接刷新
        // TODO: 2016/7/21
    }

    public void spawn(Block block) {//新生一个block的动画
        // TODO: 2016/7/21  
    }

    public void translation(BlockChangeList list) { //平移动画
        // TODO: 2016/7/21     
    }

    public void gameOver() {
        // TODO: 2016/7/21  
    }
}
