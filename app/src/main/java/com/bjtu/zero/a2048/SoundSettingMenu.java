package com.bjtu.zero.a2048;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.zero.a2048.core.GamePresenter;

public class SoundSettingMenu extends Activity {

    GamePresenter gamePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setFinishOnTouchOutside(false);
        LinearLayout SoundSettingLayout = new LinearLayout(this);
        SoundSettingLayout.setOrientation(LinearLayout.VERTICAL);
        TextView title = new TextView(this);
        title.setText(" 2 0 4 8 ");
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 56);
        title.setGravity(Gravity.CENTER);
        title.getPaint().setFakeBoldText(true);
        LinearLayout SoundSetting = new LinearLayout(this);
        SoundSetting.setOrientation(LinearLayout.VERTICAL);
        final TextView soundSeting = new TextView(this);
        soundSeting.setGravity(Gravity.CENTER);
        soundSeting.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        soundSeting.getPaint().setFakeBoldText(true);
        final Button simple = new Button(this);
        simple.setText("精简版");
        final Button happy = new Button(this);
        happy.setText("开心消消乐版");
        final Button lol = new Button(this);
        lol.setText("lol版");
        final Button dota = new Button(this);
        dota.setText("dota版");

        simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Setting.Sound.enabled && Setting.Sound.SOUND_PACK == 3) {
                    Setting.Sound.enabled = false;
                    soundSeting.setText("音效  关");
                    simple.setTextColor(Color.BLACK);
                    simple.getPaint().setFakeBoldText(false);
                } else {
                    Setting.Sound.enabled = true;
                    soundSeting.setText("音效  开");
                    Setting.Sound.SOUND_PACK = 3;
                    simple.setTextColor(Color.BLUE);
                    simple.getPaint().setFakeBoldText(true);
                    happy.setTextColor(Color.BLACK);
                    happy.getPaint().setFakeBoldText(false);
                    lol.setTextColor(Color.BLACK);
                    lol.getPaint().setFakeBoldText(false);
                    dota.setTextColor(Color.BLACK);
                    dota.getPaint().setFakeBoldText(false);
                }
                Setting.mySoundManager.setSoundType();
            }
        });

        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Setting.Sound.enabled && Setting.Sound.SOUND_PACK == 2) {
                    Setting.Sound.enabled = false;
                    soundSeting.setText("音效  关");
                    happy.setTextColor(Color.BLACK);
                    happy.getPaint().setFakeBoldText(false);
                } else {
                    Setting.Sound.enabled = true;
                    soundSeting.setText("音效  开");
                    Setting.Sound.SOUND_PACK = 2;
                    happy.setTextColor(Color.BLUE);
                    happy.getPaint().setFakeBoldText(true);
                    simple.setTextColor(Color.BLACK);
                    simple.getPaint().setFakeBoldText(false);
                    lol.setTextColor(Color.BLACK);
                    lol.getPaint().setFakeBoldText(false);
                    dota.setTextColor(Color.BLACK);
                    dota.getPaint().setFakeBoldText(false);
                }
                Setting.mySoundManager.setSoundType();
            }
        });

        dota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Setting.Sound.enabled && Setting.Sound.SOUND_PACK == 0) {
                    Setting.Sound.enabled = false;
                    soundSeting.setText("音效  关");
                    dota.setTextColor(Color.BLACK);
                    dota.getPaint().setFakeBoldText(false);
                } else {
                    Setting.Sound.enabled = true;
                    soundSeting.setText("音效  开");
                    Setting.Sound.SOUND_PACK = 0;
                    dota.setTextColor(Color.BLUE);
                    dota.getPaint().setFakeBoldText(true);
                    happy.setTextColor(Color.BLACK);
                    happy.getPaint().setFakeBoldText(false);
                    lol.setTextColor(Color.BLACK);
                    lol.getPaint().setFakeBoldText(false);
                    simple.setTextColor(Color.BLACK);
                    simple.getPaint().setFakeBoldText(false);
                }
                Setting.mySoundManager.setSoundType();
            }
        });

        lol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Setting.Sound.enabled && Setting.Sound.SOUND_PACK == 1) {
                    Setting.Sound.enabled = false;
                    soundSeting.setText("音效  关");
                    lol.setTextColor(Color.BLACK);
                    lol.getPaint().setFakeBoldText(false);
                } else {
                    Setting.Sound.enabled = true;
                    soundSeting.setText("音效  开");
                    Setting.Sound.SOUND_PACK = 1;
                    lol.setTextColor(Color.BLUE);
                    lol.getPaint().setFakeBoldText(true);
                    happy.setTextColor(Color.BLACK);
                    happy.getPaint().setFakeBoldText(false);
                    simple.setTextColor(Color.BLACK);
                    simple.getPaint().setFakeBoldText(false);
                    dota.setTextColor(Color.BLACK);
                    dota.getPaint().setFakeBoldText(false);
                }
                Setting.mySoundManager.setSoundType();
            }
        });

        if(Setting.Sound.enabled){
            soundSeting.setText("音效  开");
            switch (Setting.Sound.SOUND_PACK){
                case 0:
                    dota.setTextColor(Color.BLUE);
                    dota.getPaint().setFakeBoldText(true);
                    break ;
                case 1:
                    lol.setTextColor(Color.BLUE);
                    lol.getPaint().setFakeBoldText(true);
                    break ;
                case 2:
                    happy.setTextColor(Color.BLUE);
                    happy.getPaint().setFakeBoldText(true);
                    break ;
                case 3:
                    simple.setTextColor(Color.BLUE);
                    simple.getPaint().setFakeBoldText(true);
                    break ;
            }
        }
        else
            soundSeting.setText("音效  关");
        LinearLayout soundBar1 = new LinearLayout(this);
        soundBar1.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout soundBar2 = new LinearLayout(this);
        soundBar2.setOrientation(LinearLayout.HORIZONTAL);
        simple.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f));
        happy.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f));
        lol.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f));
        dota.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f));
        soundBar1.addView(simple);
        soundBar1.addView(happy);
        soundBar1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        soundBar2.addView(lol);
        soundBar2.addView(dota);
        soundBar2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));






        final TextView saveView = new TextView(this);
        saveView.setGravity(Gravity.CENTER);
        saveView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        saveView.getPaint().setFakeBoldText(true);
        saveView.setText("存      档");
        LinearLayout saveBar = new LinearLayout(this);
        saveBar.setOrientation(LinearLayout.HORIZONTAL);
        final Button save1 = new Button(this);
        save1.setText("存档1");
        final Button save2 = new Button(this);
        save2.setText("存档2");
        final Button save3 = new Button(this);
        save3.setText("存档3");
        saveBar.addView(save1);
        saveBar.addView(save2);
        saveBar.addView(save3);

        save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("UI", "undo clicked");
                Setting.gamePresenter.write(1);
                Toast.makeText(getApplicationContext(), "已存入存档1", Toast.LENGTH_SHORT).show();
            }
        });
        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("UI", "undo clicked");
                Setting.gamePresenter.write(2);
                Toast.makeText(getApplicationContext(), "已存入存档2", Toast.LENGTH_SHORT).show();
            }
        });
        save3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("UI", "undo clicked");
                Setting.gamePresenter.write(3);
                Toast.makeText(getApplicationContext(), "已存入存档3", Toast.LENGTH_SHORT).show();
            }
        });

        TextView blank = new TextView(this);
        blank.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        final Button makesure = new Button(this);
        makesure.setText("确定");
        makesure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        SoundSettingLayout.addView(title);
        SoundSettingLayout.addView(soundSeting);
        SoundSettingLayout.addView(soundBar1);
        SoundSettingLayout.addView(soundBar2);
        SoundSettingLayout.addView(saveView);
        SoundSettingLayout.addView(saveBar);
        SoundSettingLayout.addView(blank);
        SoundSettingLayout.addView(makesure);
        setContentView( SoundSettingLayout);
    }
}
