package com.bjtu.zero.a2048;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bjtu.zero.a2048.core.GameModel;
import com.bjtu.zero.a2048.core.GamePresenter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                switch(i){
                    case 0:Setting.savemodel = 0;intent.setClass(Main2Activity.this,MainActivity.class);break;
                    case 1:Setting.savemodel = 1;intent.setClass(Main2Activity.this,MainActivity.class);break;
                    case 2:Setting.savemodel = 2;intent.setClass(Main2Activity.this,MainActivity.class);break;
                    case 3:Setting.savemodel = 3;intent.setClass(Main2Activity.this,MainActivity.class);break;
                    case 4:Setting.savemodel = 4;intent.setClass(Main2Activity.this,MainActivity.class);break;
                }
                startActivity(intent);
            }
        });
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




}
