package com.bjtu.zero.a2048;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewGameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LinearLayout NewGameLayout = new LinearLayout(this);
        NewGameLayout.setOrientation(LinearLayout.VERTICAL);

        this.setFinishOnTouchOutside(false);
        TextView title = new TextView(this);
        title.setText(" 2 0 4 8 ");
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 56);
        title.setGravity(Gravity.CENTER);
        title.getPaint().setFakeBoldText(true);

        LinearLayout NewGameSize = new LinearLayout(this);
        NewGameSize.setOrientation(LinearLayout.VERTICAL);
        TextView sizeSetting = new TextView(this);
        sizeSetting.setText("游戏规模");
        sizeSetting.setGravity(Gravity.CENTER);
        sizeSetting.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);


        final Button size4 = new Button(this);
        final Button size5 = new Button(this);
        final Button size6 = new Button(this);


        size4.setText("4×4");
        size5.setText("5×5");
        size6.setText("6×6");
        size4.setTextColor(Color.BLUE);
        size4.getPaint().setFakeBoldText(true);
        Setting.Runtime.BOARD_SIZE = 4;

        size4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setting.Runtime.BOARD_SIZE = 4;
                size4.setTextColor(Color.BLUE);
                size4.getPaint().setFakeBoldText(true);
                size5.setTextColor(Color.BLACK);
                size5.getPaint().setFakeBoldText(false);
                size6.setTextColor(Color.BLACK);
                size6.getPaint().setFakeBoldText(false);
            }
        });

        size5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setting.Runtime.BOARD_SIZE = 5;
                size5.setTextColor(Color.BLUE);
                size5.getPaint().setFakeBoldText(true);
                size4.setTextColor(Color.BLACK);
                size4.getPaint().setFakeBoldText(false);
                size6.setTextColor(Color.BLACK);
                size6.getPaint().setFakeBoldText(false);
            }
        });

        size6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setting.Runtime.BOARD_SIZE = 6;
                size6.setTextColor(Color.BLUE);
                size6.getPaint().setFakeBoldText(true);
                size5.setTextColor(Color.BLACK);
                size5.getPaint().setFakeBoldText(false);
                size4.setTextColor(Color.BLACK);
                size4.getPaint().setFakeBoldText(false);
            }
        });
        LinearLayout sizeBar = new LinearLayout(this);
        sizeBar.setOrientation(LinearLayout.HORIZONTAL);
        sizeBar.addView(size4);
        sizeBar.addView(size5);
        sizeBar.addView(size6);
        NewGameSize.addView(sizeSetting);
        NewGameSize.addView(sizeBar);

        LinearLayout NewGameShow = new LinearLayout(this);
        NewGameShow.setOrientation(LinearLayout.VERTICAL);
        TextView showSetting = new TextView(this);
        showSetting.setText("文字显示");
        showSetting.setGravity(Gravity.CENTER);
        showSetting.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        final Button dynasty = new Button(this);
        dynasty.setText("朝代版");
        final Button academic = new Button(this);
        academic.setText("学历版");
        final Button number = new Button(this);
        number.setText("经典版");
        number.setTextColor(Color.BLUE);
        number.getPaint().setFakeBoldText(true);
        Setting.Runtime.STRING_LIST = Setting.UI.STRING_LIST_CLASSICAL;

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

        LinearLayout showBar = new LinearLayout(this);
        sizeBar.setOrientation(LinearLayout.HORIZONTAL);
        showBar.addView(number);
        showBar.addView(dynasty);
        showBar.addView(academic);
        NewGameShow.addView(showSetting);
        NewGameShow.addView(showBar);

        LinearLayout NewGameSound = new LinearLayout(this);
        NewGameSound.setOrientation(LinearLayout.VERTICAL);
        final TextView soundSetting = new TextView(this);
        soundSetting.setText("音效    关");

        soundSetting.setGravity(Gravity.CENTER);
        soundSetting.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        final Button simple = new Button(this);
        simple.setText("精简版");
        final Button happy = new Button(this);
        happy.setText("开心消消乐版");
        final Button lol = new Button(this);
        lol.setText("lol版");
        final Button dota = new Button(this);
        dota.setText("dota版");
        Setting.Runtime.Sound.enabled = false;

        simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Setting.Runtime.Sound.enabled && Setting.Runtime.Sound.SOUND_PACK == 3) {
                    Setting.Runtime.Sound.enabled = false;
                    soundSetting.setText("音效    关");
                    simple.setTextColor(Color.BLACK);
                    simple.getPaint().setFakeBoldText(false);
                } else {
                    Setting.Runtime.Sound.enabled = true;
                    soundSetting.setText("音效    开");
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

        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Setting.Runtime.Sound.enabled && Setting.Runtime.Sound.SOUND_PACK == 2) {
                    Setting.Runtime.Sound.enabled = false;
                    soundSetting.setText("音效    关");
                    happy.setTextColor(Color.BLACK);
                    happy.getPaint().setFakeBoldText(false);
                } else {
                    Setting.Runtime.Sound.enabled = true;
                    soundSetting.setText("音效    开");
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

        dota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Setting.Runtime.Sound.enabled && Setting.Runtime.Sound.SOUND_PACK == 0) {
                    Setting.Runtime.Sound.enabled = false;
                    soundSetting.setText("音效    关");
                    dota.setTextColor(Color.BLACK);
                    dota.getPaint().setFakeBoldText(false);
                } else {
                    Setting.Runtime.Sound.enabled = true;
                    soundSetting.setText("音效    开");
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

        lol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Setting.Runtime.Sound.enabled && Setting.Runtime.Sound.SOUND_PACK == 1) {
                    Setting.Runtime.Sound.enabled = false;
                    soundSetting.setText("音效    关");
                    lol.setTextColor(Color.BLACK);
                    lol.getPaint().setFakeBoldText(false);
                } else {
                    Setting.Runtime.Sound.enabled = true;
                    soundSetting.setText("音效    开");
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
        NewGameSound.addView(soundSetting);
        NewGameSound.addView(soundBar1);
        NewGameSound.addView(soundBar2);
        Button makeSure = new Button(this);
        makeSure.setText("确定");
        makeSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewGameActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        TextView blank = new TextView(this);
        blank.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        NewGameLayout.addView(title);
        NewGameLayout.addView(NewGameSize);
        NewGameLayout.addView(NewGameShow);
        NewGameLayout.addView(NewGameSound);
        NewGameLayout.addView(blank);
        NewGameLayout.addView(makeSure);
        setContentView(NewGameLayout);
    }
}
