package com.bjtu.zero.a2048;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.zero.a2048.core.Status;
import com.bjtu.zero.a2048.ui.DoubleClickDetector;
import com.bjtu.zero.a2048.ui.SoundManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Main2Activity extends Activity implements
        DoubleClickDetector.OnClickListener {

    private final Bitmap[] bitmaps = {
            new Status(4).thumbnail(), new Status(4).thumbnail(),
            new Status(4).thumbnail(), new Status(4).thumbnail()
    };
    private final String[] texts = {"继续游戏", "新游戏", "存档1", "存档2", "存档3"};
    private final String[] scores = {"", "", "", ""};
    private final String[] times = {"", "", "", ""};
    private MyAdapter adapter;
    private DoubleClickDetector doubleClickDetector;

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        SoundManager mySoundManager = new SoundManager(this.getApplicationContext());
        Setting.Runtime.soundManager = mySoundManager;
        mySoundManager.execute();
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                //i 第几个
                //startActivity()
                Intent intent = new Intent();
                //MainActivity a = new MainActivity();
                Setting.Runtime.FILE_ID = i;
                if (i == 1) {
                    //Intent intent0 = new Intent(Main2Activity.this, NewGameActivity.class);
                    //startActivity(intent0);
                    Setting.Runtime.soundManager.clear();
                    intent.setClass(Main2Activity.this, NewGameActivity.class);
                } else {
                    intent.setClass(Main2Activity.this, MainActivity.class);
                }
                startActivityForResult(intent, 0);
            }
        });
        doubleClickDetector = new DoubleClickDetector(Setting.UI.DOUBLE_HIT_INTERVAL, this);
    }

    /**
     * 由子界面返回时刷新ListView
     *
     * @param requestCode  确认返回的数据是从哪个Activity返回的
     * @param resultCode  整数resultCode是由子Activity通过其setResult()方法返回
     * @param intent  一个Intent对象，带有返回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.e("eeeee", "result");
        adapter.notifyDataSetChanged();
    }

    /**
     * 读取第i个存档
     *
     * @param i 代表第i个存档。i = 1,2,3
     */
    public void read(int i) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            //保存棋盘的缩略图
            fis = openFileInput("image" + String.valueOf(i) + ".txt");
            Log.e("aaaaa", "read image 111 " + i);
            ois = new ObjectInputStream(fis);
            Log.e("aaaaa", "read image 222 " + i);
            bitmaps[i] = BitmapFactory.decodeStream(fis);
            fis.close();

            //保存分数
            fis = openFileInput("score" + String.valueOf(i) + ".txt");
            ois = new ObjectInputStream(fis);
            int score = ((int) ois.readObject());
            scores[i] = String.valueOf(score);
            Log.e("aaaaa", "read ok " + i);
            fis.close();

            //保存当前时间
            fis = openFileInput("time" + String.valueOf(i) + ".txt");
            ois = new ObjectInputStream(fis);
            String date = ((String) ois.readObject());
            times[i] = date;
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

    /**
     * 二次退出确认
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            doubleClickDetector.onClick();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSingleClick() {
        Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDoubleClick() {
        finish();
    }

    /**
     * ListView的适配器
     */
    class MyAdapter extends BaseAdapter {

        //要显示多少条数据
        @Override
        public int getCount() {
            return texts.length;
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
            textView.setText(texts[i]);
            textView.setTextSize(30);

            //第三四五行为存档，需要显示分数和时间
            if (i > 1) {
                read(i - 1);
                imageView.setImageBitmap(bitmaps[i - 1]);
                textView.setText(String.format("%s  分数：%s\n%s", texts[i], scores[i - 1], times[i - 1]));
                textView.setTextSize(25);
            }
            //3.返回到ListView显示
            return root;
        }
    }
}
