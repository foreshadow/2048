package com.bjtu.zero.a2048.ui;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.bjtu.zero.a2048.R;
import com.bjtu.zero.a2048.Setting;

public class SoundManager {

    int move;
    private boolean enabled;
    private boolean isFirstBlood;
    private SoundPool soundPool;
    private int[] rank;
    private int[] merge;
    private int firstblood;

    public SoundManager() {
        enabled = true;
        isFirstBlood = true;
        rank = new int[17];
        merge = new int[6];
    }

    public void clear() {
        isFirstBlood = true;
    }

    public void load(Context context) {
        soundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 0);

        if (Setting.Sound.SoundPack == 2)
            isFirstBlood = false;
        switch (Setting.Sound.SoundPack) {
            case 0: //dota
                firstblood = soundPool.load(context, R.raw.dotafirstblood, 1);

                rank[4] = soundPool.load(context, R.raw.dotakillingspree, 1); //16 三杀
                rank[5] = soundPool.load(context, R.raw.dotadominating, 1);
                rank[6] = soundPool.load(context, R.raw.dotamegakill, 1);
                rank[7] = soundPool.load(context, R.raw.dotaunstoppable, 1);
                rank[8] = soundPool.load(context, R.raw.dotawhickedsick, 1);
                rank[9] = soundPool.load(context, R.raw.dotamonsterkill, 1);
                rank[10] = soundPool.load(context, R.raw.dotagodlike, 1);
                rank[11] = soundPool.load(context, R.raw.dotaholyshit, 1);

                merge[2] = soundPool.load(context, R.raw.dotadoublekill, 1);
                merge[3] = soundPool.load(context, R.raw.dotatriplekill, 1);
                merge[4] = soundPool.load(context, R.raw.dotaultrakill, 1);
                merge[5] = soundPool.load(context, R.raw.dotarampage, 1);

                break;
            case 1:
                firstblood = soundPool.load(context, R.raw.lolfirstblood, 1);

                rank[4] = soundPool.load(context, R.raw.lolkillingspree, 1); //16 三杀
                rank[5] = soundPool.load(context, R.raw.lolrampage, 1);
                rank[6] = soundPool.load(context, R.raw.lolunstopped, 1);
                rank[7] = soundPool.load(context, R.raw.loldominating, 1);
                rank[8] = soundPool.load(context, R.raw.lolgodlike, 1);
                rank[9] = soundPool.load(context, R.raw.lollegendary, 1);
                rank[10] = soundPool.load(context, R.raw.lolshutdown, 1);
                rank[11] = soundPool.load(context, R.raw.lolshutdown, 1);

                merge[2] = soundPool.load(context, R.raw.loldoublekill, 1);
                merge[3] = soundPool.load(context, R.raw.loltriplekill, 1);
                merge[4] = soundPool.load(context, R.raw.lolquatrekill, 1);
                merge[5] = soundPool.load(context, R.raw.lolpentakill, 1);

                break;
            default:
                rank[4] = soundPool.load(context, R.raw.happygood, 1); //16 三杀
                rank[5] = soundPool.load(context, R.raw.happygreat, 1);
                rank[6] = soundPool.load(context, R.raw.happyexcellent, 1);
                rank[7] = soundPool.load(context, R.raw.happyexcellent, 1);
                rank[8] = soundPool.load(context, R.raw.happyamazing, 1);
                rank[9] = soundPool.load(context, R.raw.happyamazing, 1);
                rank[10] = soundPool.load(context, R.raw.happyunbelieveable, 1);
                rank[11] = soundPool.load(context, R.raw.happyunbelieveable, 1);
                move = soundPool.load(context, R.raw.happymove, 1);
                soundPool.setVolume(move, (float) 0.5, (float) 0.5);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void playProcess(int maxRank, int mergeNum) {
        if (enabled) {
            if (maxRank >= 2 && isFirstBlood) {
                soundPool.play(firstblood, 1, 1, 0, 0, 1);
                isFirstBlood = false;
            }
            if (maxRank >= 7)
                soundPool.play(rank[Math.min(maxRank, 11)], 1, 1, 0, 0, 1);
            else if (mergeNum >= 2 && Setting.Sound.SoundPack != 2)
                soundPool.play(merge[Math.min(mergeNum, 5)], 1, 1, 0, 0, 1);
            else if (maxRank >= 4)
                soundPool.play(rank[Math.min(maxRank, 11)], 1, 1, 0, 0, 1);
            else if (Setting.Sound.SoundPack == 2) {
                soundPool.play(move, 1, 1, 0, 0, 1);
            }
        }
    }

    public void playGameOver() {

    }

    public void startGame() {

    }

}
