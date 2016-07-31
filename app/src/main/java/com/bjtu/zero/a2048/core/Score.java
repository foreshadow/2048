package com.bjtu.zero.a2048.core;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjtu.zero.a2048.MainActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by asus-pc on 2016/7/28.
 */
public class Score {
    public TextView score;
    public TextView highScore;
    public TextView t1;
    public TextView t2;
    public int now;
    public int high;
    private static final String FILE_NAME = "sorce.txt";
    SharedPreferences sp = null;
    SharedPreferences.Editor editor = null;
    public final String KEY = "HIGH";

    //public Score(){

    //}

    public Score(Context con){
        this.sp =  con.getSharedPreferences("test", Activity.MODE_PRIVATE);
        this.editor = sp.edit();

    }

    public void init(){
        String a = sp.getString(KEY,"");
        Log.e("aaaaa",a);
        if(a == null)
            setHighScore(0);
        else
            setHighScore(Integer.parseInt(a));
        //this.high = Integer.parseInt(a);
        Log.e("aaaaa","Score");
    }

    public void setScore(int a){
        now = a;
        score.setText(String.valueOf(now));
        Log.e("aaaaa","setScore");
    }

    public void setHighScore(int a){
        high = a;
        highScore.setText(String.valueOf(high));

        //write(String.valueOf(high));
        //sp =  getSharedPreferences("test", Activity.MODE_PRIVATE);
        //editor = sp.edit();
       //sp =  this.getSharedPreferences("test", Activity.MODE_PRIVATE);
        //editor = sp.edit();
        //Context ctx = this;
        //sp =  ctx.getSharedPreferences("test", Activity.MODE_PRIVATE);
        //editor = sp.edit();
        editor.putString(KEY, String.valueOf(high));
        editor.commit();
        Log.e("aaaaa","setHigh");
    }

    private class AnotherTask extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... params)
        {
            return params[0];
        }
        @Override
        protected void onPostExecute(String result)
        {
            //更新UI的操作，这里面的内容是在UI线程里面执行的
            //setScore(Integer.parseInt(result));
            //if(now > high)
                setHighScore(now);
        }
    }

}
