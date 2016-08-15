package com.bjtu.zero.a2048;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class NewGameActivity extends Activity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LinearLayout NewGameLayout = new LinearLayout(this);
        NewGameLayout.setOrientation(LinearLayout.VERTICAL);

        TextView title = new TextView(this);
        title.setText(" 2 0 4 8 ");
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 56);
        title.setGravity(Gravity.CENTER);
        title.getPaint().setFakeBoldText(true);

        LinearLayout NewGameSize = new LinearLayout(this);
        NewGameSize.setOrientation(LinearLayout.VERTICAL);
        TextView sizeSeting = new TextView(this);
        sizeSeting.setText("游戏规模");
        sizeSeting.setGravity(Gravity.CENTER);
        sizeSeting.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);


        final Button size4 = new Button(this);
        final Button size5 = new Button(this);
        final Button size6 = new Button(this);


        size4.setText("4×4");
        size5.setText("5×5");
        size6.setText("6×6");
        size4.setTextColor(Color.BLUE);
        size4.getPaint().setFakeBoldText(true);
        Setting.Game.DEFAULT_SIZE =4;

        size4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setting.Game.DEFAULT_SIZE=4;
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
                Setting.Game.DEFAULT_SIZE=5;
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
                Setting.Game.DEFAULT_SIZE=6;
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
        NewGameSize.addView(sizeSeting);
        NewGameSize.addView(sizeBar);

        LinearLayout NewGameShow = new LinearLayout(this);
        NewGameShow.setOrientation(LinearLayout.VERTICAL);
        TextView showSeting = new TextView(this);
        showSeting.setText("文字显示");
        showSeting.setGravity(Gravity.CENTER);
        showSeting.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        final Button dynasty = new Button(this);
        dynasty.setText("朝代版");
        final Button academic = new Button(this);
        academic.setText("学历版");
        final Button number = new Button(this);
        number.setText("经典版");
        number.setTextColor(Color.BLUE);
        number.getPaint().setFakeBoldText(true);

        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setting.UI.STRING_LIST = Setting.UI.STRING_LIST_CLASSICAL ;
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
                Setting.UI.STRING_LIST = Setting.UI.STRING_LIST_ACADEMIC;
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
                Setting.UI.STRING_LIST = Setting.UI.STRING_LIST_DYNASTY ;
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
        NewGameShow.addView(showSeting);
        NewGameShow.addView(showBar);

        LinearLayout NewGameSound = new LinearLayout(this);
        NewGameSound.setOrientation(LinearLayout.VERTICAL);
        final TextView soundSeting = new TextView(this);
        soundSeting.setText("音效    关");

        soundSeting.setGravity(Gravity.CENTER);
        soundSeting.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        final Button simple = new Button(this);
        simple.setText("精简版");
//        simple.setMinimumWidth(size4.getWidth()*3/2);
        final Button happy = new Button(this);
        happy.setText("开心消消乐版");
       // happy.setMinimumWidth(450);
        final Button lol = new Button(this);
        lol.setText("lol版");
        final Button dota  = new Button(this);
        dota.setText("dota版");

        simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Setting.Sound.enabled && Setting.Sound.SoundPack==3){
                    Setting.Sound.enabled =false ;
                    soundSeting.setText("音效    关");
                    simple.setTextColor(Color.BLACK);
                    simple.getPaint().setFakeBoldText(false);
                }
                else {
                    Setting.Sound.enabled =true ;
                    soundSeting.setText("音效    开");
                    Setting.Sound.SoundPack=3;
                    simple.setTextColor(Color.BLUE);
                    simple.getPaint().setFakeBoldText(true);
                    happy.setTextColor(Color.BLACK);
                    happy.getPaint().setFakeBoldText(false);
                    lol.setTextColor(Color.BLACK);
                    lol.getPaint().setFakeBoldText(false);
                    dota.setTextColor(Color.BLACK);
                    dota.getPaint().setFakeBoldText(false);
                }
            }
        });

        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Setting.Sound.enabled && Setting.Sound.SoundPack==2){
                    Setting.Sound.enabled =false ;
                    soundSeting.setText("音效    关");
                    happy.setTextColor(Color.BLACK);
                    happy.getPaint().setFakeBoldText(false);
                }
                else {
                    Setting.Sound.enabled =true ;
                    soundSeting.setText("音效    开");
                    Setting.Sound.SoundPack=2;
                    happy.setTextColor(Color.BLUE);
                    happy.getPaint().setFakeBoldText(true);
                    simple.setTextColor(Color.BLACK);
                    simple.getPaint().setFakeBoldText(false);
                    lol.setTextColor(Color.BLACK);
                    lol.getPaint().setFakeBoldText(false);
                    dota.setTextColor(Color.BLACK);
                    dota.getPaint().setFakeBoldText(false);
                }
            }
        });

        dota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Setting.Sound.enabled && Setting.Sound.SoundPack==0){
                    Setting.Sound.enabled =false ;
                    soundSeting.setText("音效    关");
                    dota.setTextColor(Color.BLACK);
                    dota.getPaint().setFakeBoldText(false);
                }
                else {
                    Setting.Sound.enabled =true ;
                    soundSeting.setText("音效    开");
                    Setting.Sound.SoundPack=0;
                    dota.setTextColor(Color.BLUE);
                    dota.getPaint().setFakeBoldText(true);
                    happy.setTextColor(Color.BLACK);
                    happy.getPaint().setFakeBoldText(false);
                    lol.setTextColor(Color.BLACK);
                    lol.getPaint().setFakeBoldText(false);
                    simple.setTextColor(Color.BLACK);
                    simple.getPaint().setFakeBoldText(false);
                }
            }
        });

        lol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Setting.Sound.enabled && Setting.Sound.SoundPack==1){
                    Setting.Sound.enabled =false ;
                    soundSeting.setText("音效    关");
                    lol.setTextColor(Color.BLACK);
                    lol.getPaint().setFakeBoldText(false);
                }
                else {
                    Setting.Sound.enabled =true ;
                    soundSeting.setText("音效    开");
                    Setting.Sound.SoundPack=1;
                    lol.setTextColor(Color.BLUE);
                    lol.getPaint().setFakeBoldText(true);
                    happy.setTextColor(Color.BLACK);
                    happy.getPaint().setFakeBoldText(false);
                    simple.setTextColor(Color.BLACK);
                    simple.getPaint().setFakeBoldText(false);
                    dota.setTextColor(Color.BLACK);
                    dota.getPaint().setFakeBoldText(false);
                }
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
        NewGameSound.addView(soundSeting);
        NewGameSound.addView(soundBar1);
        NewGameSound.addView(soundBar2);
        Button  makeSure = new Button(this);
        makeSure.setText("确定");
        makeSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                "NewGame Page", // TODO: Define a title for the content shown.
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
                "NewGame Page", // TODO: Define a title for the content shown.
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
}