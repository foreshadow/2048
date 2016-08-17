package com.bjtu.zero.a2048.ui;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.bjtu.zero.a2048.R;
import com.bjtu.zero.a2048.Setting;

public class SoundManager {

    private boolean isFirstBlood;
    private SoundPool soundPool;
    private int[] rank;
    private int mergenum[];
    private int firstblood;
    private int move, merge;

    public SoundManager() {
        isFirstBlood = true;
        rank = new int[17];
        mergenum = new int[6];
    }

    /**
     * 将本局游戏的声音记录清空
     */
    public void clear() {
        isFirstBlood = true;
    }

    /**
     * 加载声音文件
     * @param context  上下文
     */
    public void load(Context context) {
        soundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 0);

        if (Setting.Sound.SoundPack >= 2)
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
                rank[10] = soundPool.load(context, R.raw.dotagdlike, 1);
                rank[11] = soundPool.load(context, R.raw.dotaholyshit, 1);

                mergenum[2] = soundPool.load(context, R.raw.dotadoublekill, 1);
                mergenum[3] = soundPool.load(context, R.raw.dotatriplekill, 1);
                mergenum[4] = soundPool.load(context, R.raw.dotaultrakill, 1);
                mergenum[5] = soundPool.load(context, R.raw.dotarampage, 1);

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

                mergenum[2] = soundPool.load(context, R.raw.loldoublekill, 1);
                mergenum[3] = soundPool.load(context, R.raw.loltriplekill, 1);
                mergenum[4] = soundPool.load(context, R.raw.lolquatrekill, 1);
                mergenum[5] = soundPool.load(context, R.raw.lolpentakill, 1);

                break;

            case 2:
                rank[3] = soundPool.load(context, R.raw.happygood, 1);
                rank[4] = soundPool.load(context, R.raw.happygreat, 1);
                rank[5] = soundPool.load(context, R.raw.happygreat, 1);
                rank[6] = soundPool.load(context, R.raw.happyexcellent, 1);
                rank[7] = soundPool.load(context, R.raw.happyexcellent, 1);
                rank[8] = soundPool.load(context, R.raw.happyamazing, 1);
                rank[9] = soundPool.load(context, R.raw.happyamazing, 1);
                rank[10] = soundPool.load(context, R.raw.happyunbelieveable, 1);
                rank[11] = soundPool.load(context, R.raw.happyunbelieveable, 1);
                move  = soundPool.load(context, R.raw.happymove, 1);
                //merge  = soundPool.load(context, R.raw.happymerge , 1);
                break ;

            default:
                move  = soundPool.load(context, R.raw.simplemove, 1);
                merge  = soundPool.load(context, R.raw.simplemerge , 1);
        }
    }

    /**
     * 对每一次合法滑动，播放相应的音效
     * @param maxRank  本次滑动使游戏产生的最大分数
     * @param mergeNum 本次滑动同时合并的块数
     */
    public void playProcess(int maxRank, int mergeNum) {
        if (Setting.Sound.enabled) {
            switch(Setting.Sound.SoundPack){
                case 3:
                    if(maxRank>1)
                        soundPool.play(merge, 1, 1, 0, 0, 1);
                    else
                        soundPool.play(move, 1, 1, 0, 0, 1);
                    break ;

                case 2:
                    if(maxRank<3)
                        soundPool.play(move, 1, 1, 0, 0, 1);
                    else
                        soundPool.play(rank[Math.min(maxRank, 11)], 1, 1, 0, 0, 1);
                    break ;

                default:
                    if (maxRank >= 2 && isFirstBlood) {
                        soundPool.play(firstblood, 1, 1, 0, 0, 1);
                        isFirstBlood = false;
                    }
                    if (maxRank >= 7)
                        soundPool.play(rank[Math.min(maxRank, 11)], 1, 1, 0, 0, 1);
                    else if (mergeNum >= 2)
                        soundPool.play(mergenum[Math.min(mergeNum, 5)], 1, 1, 0, 0, 1);
                    else if(maxRank >= 4)
                        soundPool.play(rank[Math.min(maxRank, 11)], 1, 1, 0, 0, 1);
            }
        }
    }
}
