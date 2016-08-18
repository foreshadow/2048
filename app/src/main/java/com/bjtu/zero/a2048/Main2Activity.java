package com.bjtu.zero.a2048;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.zero.a2048.core.GameModel;
import com.bjtu.zero.a2048.core.Status;
import com.bjtu.zero.a2048.ui.DoubleClickDetector;
import com.bjtu.zero.a2048.ui.SoundManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main2Activity extends Activity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        DoubleClickDetector.ClickListener {

    private GestureDetector gestureDetector;
    private DoubleClickDetector doubleClickDetector;
    Bitmap[] m = {new Status(4).thumbnail(),new Status(4).thumbnail(),new Status(4).thumbnail(),new Status(4).thumbnail()};
    ImageView imageView ;
    TextView textView ;
    String[] myText = {"继续游戏", "新游戏", "存档1", "存档2", "存档3"};
    String[] sco = {"","","",""};
    String[] time = {"","","",""};

    private SoundManager mySoundManager ;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mySoundManager=new SoundManager(this.getApplicationContext());
        Setting.mySoundManager = mySoundManager;
        mySoundManager.execute();
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);
        ListView list = (ListView) findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                //i 第几个
                //startActivity()
                Intent intent = new Intent();
                //MainActivity a = new MainActivity();
                switch (i) {
                    case 0:
                        Setting.savemodel = 0;
                        intent.setClass(Main2Activity.this, MainActivity.class);
                        break;
                    case 1:
                        Setting.savemodel = 1;
                        //Intent intent0 = new Intent(Main2Activity.this, NewGameActivity.class);
                        //startActivity(intent0);
                        Setting.mySoundManager.clear();
                        intent.setClass(Main2Activity.this, NewGameActivity.class);
                        break;
                    case 2:
                        Setting.savemodel = 2;
                        intent.setClass(Main2Activity.this, MainActivity.class);
                        break;
                    case 3:
                        Setting.savemodel = 3;
                        intent.setClass(Main2Activity.this, MainActivity.class);
                        break;
                    case 4:
                        Setting.savemodel = 4;
                        intent.setClass(Main2Activity.this, MainActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
        gestureDetector = new GestureDetector(this);
        doubleClickDetector = new DoubleClickDetector(Setting.UI.DOUBLE_HIT_INTERVAL, this);
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
            imageView = (ImageView) root.findViewById(R.id.item_ion);
            textView = (TextView) root.findViewById(R.id.item_text);

            //2.为ItemView设置上显示的数据
            imageView.setImageResource(R.mipmap.ic_launcher);
            textView.setText(myText[i]);
            textView.setTextSize(30);
            if(i > 1){
                read(i-1);
                imageView.setImageBitmap(m[i-1]);
                textView.setText(myText[i] + "  分数：" + sco[i-1] + "\n" + time[i-1]);
                textView.setTextSize(25);
            }
            //3.返回到ListView显示
            return root;
        }
    }

    public void read(int i) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = openFileInput("image" + String.valueOf(i) + ".txt");
            Log.e("aaaaa", "read image 111 "+i);
            ois = new ObjectInputStream(fis);
            Log.e("aaaaa", "read image 222 "+i);
            m[i] = BitmapFactory.decodeStream(fis);
            fis.close();
            fis = openFileInput("score" + String.valueOf(i) + ".txt");
            ois = new ObjectInputStream(fis);
            int score = ((int) ois.readObject());
            sco[i] = String.valueOf(score);
            Log.e("aaaaa", "read ok "+i);
            fis.close();
            fis = openFileInput("time" + String.valueOf(i) + ".txt");
            ois = new ObjectInputStream(fis);
            String date = ((String) ois.readObject());
            time[i] =date;
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            //这里是读取文件产生异常
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    //fis流关闭异常
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    //ois流关闭异常
                    e.printStackTrace();
                }
            }
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
