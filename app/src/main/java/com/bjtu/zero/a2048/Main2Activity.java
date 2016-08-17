package com.bjtu.zero.a2048;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.zero.a2048.core.GameModel;
import com.bjtu.zero.a2048.core.GamePresenter;
import com.bjtu.zero.a2048.ui.DoubleClickDetector;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Main2Activity extends Activity  implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        DoubleClickDetector.ClickListener{

    private GestureDetector gestureDetector;
    private DoubleClickDetector doubleClickDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);

        ListView list = (ListView) findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);
        gestureDetector = new GestureDetector(this);
        doubleClickDetector = new DoubleClickDetector(Setting.UI.DOUBLE_HIT_INTERVAL, this);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                //i 第几个
                //startActivity()
                Intent intent = new Intent();
                //MainActivity a = new MainActivity();
                switch(i){
                    case 0:Setting.savemodel = 0;intent.setClass(Main2Activity.this,MainActivity.class);break;
                    case 1:Setting.savemodel = 1;
                        //Intent intent0 = new Intent(Main2Activity.this, NewGameActivity.class);
                        //startActivity(intent0);
                        intent.setClass(Main2Activity.this,NewGameActivity.class);
                        break;
                    case 2:Setting.savemodel = 2;intent.setClass(Main2Activity.this,MainActivity.class);break;
                    case 3:Setting.savemodel = 3;intent.setClass(Main2Activity.this,MainActivity.class);break;
                    case 4:Setting.savemodel = 4;intent.setClass(Main2Activity.this,MainActivity.class);break;
                }
                startActivity(intent);
            }
        });
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
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    //适配器
    public class MyAdapter extends BaseAdapter {

        String[] myText = {"继续游戏","新游戏","存档1", "存档2", "存档3"};

        //要显示多少条数据
        @Override
        public int getCount() {
            return myText.length;
        }

        //返回指定Item对应的数据
        @Override
        public Object getItem(int i) {
            return null;
        }

        //返回指定Item对应的ID
        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {
            //1.获得Item显示的View
            //a.加载布局文件
            //Context Activity继承Context
            LayoutInflater inflater = LayoutInflater.from(Main2Activity.this);
            View root = inflater.inflate(R.layout.item_list, null);
            ImageView imageView = (ImageView) root.findViewById(R.id.item_ion);
            TextView textView = (TextView) root.findViewById(R.id.item_text);

            //2.为ItemView设置上显示的数据
            imageView.setImageResource(R.mipmap.ic_launcher);
            textView.setText(myText[i]);
            //3.返回到ListView显示
            return root;
        }
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


}
