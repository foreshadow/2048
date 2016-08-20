package com.bjtu.zero.a2048;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * 当游戏结束时出现的Activity
 * 背景为半透明灰色，修改res/values/color.xml中的transparent_background即可更改此Activity的透明度和颜色
 * @author Lazy_sheep
 */

public class GameOverActivity extends Activity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        wl.alpha = 0.6f; //更改按钮的透明度(0,1)
        window.setAttributes(wl);
        setTheme(R.style.Transparent);
        setContentView(R.layout.activity_game_over);
        Button newGameButton = (Button) findViewById(R.id.newGameButton);
        newGameButton.setText(" 主菜单 ");
        TextView gameOverShow = (TextView) this.findViewById(R.id.textView2);
        if (Setting.Runtime.STRING_LIST == Setting.UI.STRING_LIST_CLASSICAL) {
            gameOverShow.setText("游戏结束!");
        } else if (Setting.Runtime.STRING_LIST == Setting.UI.STRING_LIST_DYNASTY) {
            gameOverShow.setText("江山难坐!");
        } else {
            gameOverShow.setText("学不好上!");
        }
        gameOverShow.setTextColor(Color.BLACK);
        newGameButton.setTextColor(Color.BLACK);
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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "GameOver Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.bjtu.zero.a2048/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "GameOver Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.bjtu.zero.a2048/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    /**
     * 改变返回按钮的响应事件。当弹出次Activity后，按手机返回键无效
     * @param keyCode  int: The value in event.getKeyCode().
     * @param event KeyEvent: Description of the key event.
     * @return  按下返回键时不执行任何操作，返回false
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
