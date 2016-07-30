package com.bjtu.zero.a2048.ui;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.bjtu.zero.a2048.R;

public class SoundManager {

    private boolean enabled;
    private SoundPool soundPool;
    private int move, merge, good, great, excellent, amazing, unbelievable;

    public SoundManager() {
        enabled = true;
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
    }

    public void load(Context context) {
        merge = soundPool.load(context, R.raw.merge, 1);
        move = soundPool.load(context, R.raw.move, 1);
        good = soundPool.load(context, R.raw.good, 1);
        great = soundPool.load(context, R.raw.great, 1);
        excellent = soundPool.load(context, R.raw.excellent, 1);
        amazing = soundPool.load(context, R.raw.amazing, 1);
        unbelievable = soundPool.load(context, R.raw.unbelievable, 1);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void playProcess(int maxRank) {
        if (enabled) {
            switch (maxRank) {
                case 1:
                    soundPool.play(move, 1, 1, 0, 0, 1);
                    break;
                case 5:
                    soundPool.play(good, 1, 1, 0, 0, 1);
                    break;
                case 6:
                    soundPool.play(good, 1, 1, 0, 0, 1);
                    break;
                case 7:
                    soundPool.play(great, 1, 1, 0, 0, 1);
                    break;
                case 8:
                    soundPool.play(great, 1, 1, 0, 0, 1);
                    break;
                case 9:
                    soundPool.play(excellent, 1, 1, 0, 0, 1);
                    break;
                case 10:
                    soundPool.play(amazing, 1, 1, 0, 0, 1);
                    break;
                case 11:
                    soundPool.play(unbelievable, 1, 1, 0, 0, 1);
                    break;
                default:
                    soundPool.play(merge, 1, 1, 0, 0, 1);
                    break;
            }
        }
    }

    public void playGameOver() {

    }

    public void startGame() {

    }

}
