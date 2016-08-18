package com.bjtu.zero.a2048;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends Activity {

    private TextView gameOverShow;
    private Button newGameButton ;
    @Override
    // 更改次界面的透明度和颜色color.xml <color name="transparent_background">#50FCFCFC</color>
    // 前两位是透明度 00 到99 ，后六位是背景的RGB值
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window=getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.flags=WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        wl.alpha=0.6f; //更改按钮的透明度(0,1)
        window.setAttributes(wl);
        setTheme(R.style.Transparent);
        setContentView(R.layout.activity_game_over);
        gameOverShow =new TextView(this);
        newGameButton =(Button) findViewById(R.id.newGameButton);
        newGameButton.setText(" 新游戏 ");
        gameOverShow = (TextView)this.findViewById(R.id.textView2);
        if(Setting.Runtime.STRING_LIST==Setting.UI.STRING_LIST_CLASSICAL)
            gameOverShow.setText("游戏结束!");
        else if(Setting.Runtime.STRING_LIST==Setting.UI.STRING_LIST_DYNASTY)
            gameOverShow.setText("江山难坐!");
        else
            gameOverShow.setText("学不好上!");
        gameOverShow.setTextColor(Color.BLACK);
        newGameButton.setTextColor(Color.BLACK);
        gameOverShow.getPaint().setFakeBoldText(true);
        newGameButton.getPaint().setFakeBoldText(true);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent (GameOverActivity.this, NewGameActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
