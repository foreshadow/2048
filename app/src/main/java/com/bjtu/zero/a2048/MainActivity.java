package com.bjtu.zero.a2048;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
