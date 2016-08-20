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

/**
 * 用户在游戏进行过程中对音效、文字显示更改的菜单
 * @author Lazy_sheep
 */

public class SoundSettingMenuActivity extends Activity {

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

        final TextView showView = new TextView(this);
        showView.setGravity(Gravity.CENTER);
        showView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        showView.getPaint().setFakeBoldText(true);
        showView.setText("文      字");
        LinearLayout showBar = new LinearLayout(this);
        showBar.setOrientation(LinearLayout.HORIZONTAL);
        final Button dynasty = new Button(this);
        dynasty.setText("朝代版");
        final Button academic = new Button(this);
        academic.setText("学历版");
        final Button number = new Button(this);
        number.setText("经典版");
        showBar.addView(number);
        showBar.addView(dynasty);
        showBar.addView(academic);
        if(Setting.Runtime.STRING_LIST == Setting.UI.STRING_LIST_CLASSICAL){
            number.setTextColor(Color.BLUE);
            number.getPaint().setFakeBoldText(true);
        }
        else if(Setting.Runtime.STRING_LIST == Setting.UI.STRING_LIST_ACADEMIC){
            academic.setTextColor(Color.BLUE);
            academic.getPaint().setFakeBoldText(true);
        }
        else {
            dynasty.setTextColor(Color.BLUE);
            dynasty.getPaint().setFakeBoldText(true);
        }

        /**
         * “经典版”文字选择按钮按下后的响应事件
         */
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setting.Runtime.STRING_LIST = Setting.UI.STRING_LIST_CLASSICAL;
                number.setTextColor(Color.BLUE);
                number.getPaint().setFakeBoldText(true);
                dynasty.setTextColor(Color.BLACK);
                dynasty.getPaint().setFakeBoldText(false);
                academic.setTextColor(Color.BLACK);
                academic.getPaint().setFakeBoldText(false);
            }
        });

        /**
         * “学历版”文字选择按钮按下后的响应事件
         */
        academic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setting.Runtime.STRING_LIST = Setting.UI.STRING_LIST_ACADEMIC;
                academic.setTextColor(Color.BLUE);
                academic.getPaint().setFakeBoldText(true);
                dynasty.setTextColor(Color.BLACK);
                dynasty.getPaint().setFakeBoldText(false);
                number.setTextColor(Color.BLACK);
                number.getPaint().setFakeBoldText(false);
            }
        });


        /**
         * “朝代版”文字选择按钮按下后的响应事件
         */
        dynasty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setting.Runtime.STRING_LIST = Setting.UI.STRING_LIST_DYNASTY;
                dynasty.setTextColor(Color.BLUE);
                dynasty.getPaint().setFakeBoldText(true);
                academic.setTextColor(Color.BLACK);
                academic.getPaint().setFakeBoldText(false);
                number.setTextColor(Color.BLACK);
                number.getPaint().setFakeBoldText(false);
            }
        });

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

        /**
         * “精简版”音效按钮按下后的响应事件
         */
        simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Setting.Runtime.Sound.enabled && Setting.Runtime.Sound.SOUND_PACK == 3) {
                    Setting.Runtime.Sound.enabled = false;
                    soundSeting.setText("音效  关");
                    simple.setTextColor(Color.BLACK);
                    simple.getPaint().setFakeBoldText(false);
                } else {
                    Setting.Runtime.Sound.enabled = true;
                    soundSeting.setText("音效  开");
                    Setting.Runtime.Sound.SOUND_PACK = 3;
                    simple.setTextColor(Color.BLUE);
                    simple.getPaint().setFakeBoldText(true);
                    happy.setTextColor(Color.BLACK);
                    happy.getPaint().setFakeBoldText(false);
                    lol.setTextColor(Color.BLACK);
                    lol.getPaint().setFakeBoldText(false);
                    dota.setTextColor(Color.BLACK);
                    dota.getPaint().setFakeBoldText(false);
                }
                Setting.Runtime.soundManager.setSoundType();
            }
        });


        /**
         * “开心消消乐”音效按钮按下后的响应事件
         */
        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Setting.Runtime.Sound.enabled && Setting.Runtime.Sound.SOUND_PACK == 2) {
                    Setting.Runtime.Sound.enabled = false;
                    soundSeting.setText("音效  关");
                    happy.setTextColor(Color.BLACK);
                    happy.getPaint().setFakeBoldText(false);
                } else {
                    Setting.Runtime.Sound.enabled = true;
                    soundSeting.setText("音效  开");
                    Setting.Runtime.Sound.SOUND_PACK = 2;
                    happy.setTextColor(Color.BLUE);
                    happy.getPaint().setFakeBoldText(true);
                    simple.setTextColor(Color.BLACK);
                    simple.getPaint().setFakeBoldText(false);
                    lol.setTextColor(Color.BLACK);
                    lol.getPaint().setFakeBoldText(false);
                    dota.setTextColor(Color.BLACK);
                    dota.getPaint().setFakeBoldText(false);
                }
                Setting.Runtime.soundManager.setSoundType();
            }
        });


        /**
         * “dota版”音效按钮按下后的响应事件
         */
        dota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Setting.Runtime.Sound.enabled && Setting.Runtime.Sound.SOUND_PACK == 0) {
                    Setting.Runtime.Sound.enabled = false;
                    soundSeting.setText("音效  关");
                    dota.setTextColor(Color.BLACK);
                    dota.getPaint().setFakeBoldText(false);
                } else {
                    Setting.Runtime.Sound.enabled = true;
                    soundSeting.setText("音效  开");
                    Setting.Runtime.Sound.SOUND_PACK = 0;
                    dota.setTextColor(Color.BLUE);
                    dota.getPaint().setFakeBoldText(true);
                    happy.setTextColor(Color.BLACK);
                    happy.getPaint().setFakeBoldText(false);
                    lol.setTextColor(Color.BLACK);
                    lol.getPaint().setFakeBoldText(false);
                    simple.setTextColor(Color.BLACK);
                    simple.getPaint().setFakeBoldText(false);
                }
                Setting.Runtime.soundManager.setSoundType();
            }
        });


        /**
         * “lol版”音效按钮按下后的响应事件
         */
        lol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Setting.Runtime.Sound.enabled && Setting.Runtime.Sound.SOUND_PACK == 1) {
                    Setting.Runtime.Sound.enabled = false;
                    soundSeting.setText("音效  关");
                    lol.setTextColor(Color.BLACK);
                    lol.getPaint().setFakeBoldText(false);
                } else {
                    Setting.Runtime.Sound.enabled = true;
                    soundSeting.setText("音效  开");
                    Setting.Runtime.Sound.SOUND_PACK = 1;
                    lol.setTextColor(Color.BLUE);
                    lol.getPaint().setFakeBoldText(true);
                    happy.setTextColor(Color.BLACK);
                    happy.getPaint().setFakeBoldText(false);
                    simple.setTextColor(Color.BLACK);
                    simple.getPaint().setFakeBoldText(false);
                    dota.setTextColor(Color.BLACK);
                    dota.getPaint().setFakeBoldText(false);
                }
                Setting.Runtime.soundManager.setSoundType();
            }
        });

        if (Setting.Runtime.Sound.enabled) {
            soundSeting.setText("音效  开");
            switch (Setting.Runtime.Sound.SOUND_PACK) {
                case 0:
                    dota.setTextColor(Color.BLUE);
                    dota.getPaint().setFakeBoldText(true);
                    break;
                case 1:
                    lol.setTextColor(Color.BLUE);
                    lol.getPaint().setFakeBoldText(true);
                    break;
                case 2:
                    happy.setTextColor(Color.BLUE);
                    happy.getPaint().setFakeBoldText(true);
                    break;
                case 3:
                    simple.setTextColor(Color.BLUE);
                    simple.getPaint().setFakeBoldText(true);
                    break;
            }
        } else {
            soundSeting.setText("音效  关");
        }
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

        /**
         * “存档1”按钮按下后的响应事件，将当前游戏状态保存至存档1
         */
        save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("UI", "undo clicked");
                //将棋盘信息写入存档1
                Setting.Runtime.gamePresenter.writee(1);
                //将棋盘内容写入存档1
                Setting.Runtime.gamePresenter.write(1);
                Toast.makeText(getApplicationContext(), "已存入存档1", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * “存档2”按钮按下后的响应事件，将当前游戏状态保存至存档2
         */
        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("UI", "undo clicked");
                //将棋盘信息写入存档2
                Setting.Runtime.gamePresenter.writee(2);
                //将棋盘内容写入存档2
                Setting.Runtime.gamePresenter.write(2);
                Toast.makeText(getApplicationContext(), "已存入存档2", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * “存档3”按钮按下后的响应事件，将当前游戏状态保存至存档3
         */
        save3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("UI", "undo clicked");
                //将棋盘信息写入存档3
                Setting.Runtime.gamePresenter.writee(3);
                //将棋盘内容写入存档3
                Setting.Runtime.gamePresenter.write(3);
                Toast.makeText(getApplicationContext(), "已存入存档3", Toast.LENGTH_SHORT).show();
            }
        });

        TextView blank = new TextView(this);
        blank.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        final Button makeSure = new Button(this);

        /**
         * “确定”按钮按下后的响应事件。更新棋盘状态，关闭此Activity
         */
        makeSure.setText("确定");
        makeSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setting.Runtime.gamePresenter.refresh();
                finish();
            }
        });
        SoundSettingLayout.addView(title);
        SoundSettingLayout.addView(showView);
        SoundSettingLayout.addView(showBar);
        SoundSettingLayout.addView(soundSeting);
        SoundSettingLayout.addView(soundBar1);
        SoundSettingLayout.addView(soundBar2);
        SoundSettingLayout.addView(saveView);
        SoundSettingLayout.addView(saveBar);
        SoundSettingLayout.addView(blank);
        SoundSettingLayout.addView(makeSure);
        setContentView(SoundSettingLayout);
    }
}
