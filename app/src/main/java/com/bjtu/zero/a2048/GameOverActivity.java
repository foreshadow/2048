package com.bjtu.zero.a2048;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * 当游戏结束时出现的Activity
 * 背景为半透明灰色，修改res/values/color.xml中的transparent_background即可更改此Activity的透明度和颜色
 *
 * @author Lazy_sheep
 */

public class GameOverActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
//        wl.alpha = 0.6f; //更改按钮的透明度(0,1)
        window.setAttributes(wl);
        setTheme(R.style.Transparent);
        setContentView(R.layout.activity_game_over);
        Button newGameButton = (Button) findViewById(R.id.newGameButton);
        newGameButton.setText(R.string.newGameMainMenu);
        TextView gameOverShow = (TextView) this.findViewById(R.id.textView2);
        if (Setting.Runtime.STRING_LIST == Setting.UI.STRING_LIST_CLASSICAL) {
            gameOverShow.setText(R.string.gameOverClassical);
        } else if (Setting.Runtime.STRING_LIST == Setting.UI.STRING_LIST_DYNASTY) {
            gameOverShow.setText(R.string.gameOverDynasty);
        } else {
            gameOverShow.setText(R.string.gameOverAcademy);
        }
        gameOverShow.setTextColor(Color.WHITE);
        newGameButton.setTextColor(Color.WHITE);
        newGameButton.setBackgroundColor(Color.BLACK);
        gameOverShow.getPaint().setFakeBoldText(true);
        newGameButton.getPaint().setFakeBoldText(true);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(GameOverActivity.this, NewGameActivity.class);
                MainActivity.instance.finish();
                Intent intent = new Intent(GameOverActivity.this, Main2Activity.class);
                finish();
                // startActivity(intent);
            }
        });
    }

    /**
     * 改变返回按钮的响应事件。当弹出次Activity后，按手机返回键无效
     *
     * @param keyCode int: The value in event.getKeyCode().
     * @param event   KeyEvent: Description of the key event.
     * @return 按下返回键时不执行任何操作，返回false
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode != KeyEvent.KEYCODE_BACK && super.onKeyDown(keyCode, event);
    }
}
