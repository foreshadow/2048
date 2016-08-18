package com.bjtu.zero.a2048.ui;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.util.Log;

import com.bjtu.zero.a2048.R;
import com.bjtu.zero.a2048.Setting;

import java.io.Serializable;

public class SoundManager extends AsyncTask<Void, Void, Void> implements Serializable {

    private final Context context;
    private final int[][] rank;
    private final int[][] mergeCount;
    private final int[] firstBlood;
    private boolean isFirstBlood;
    private SoundPool soundPool;
    private SoundPool simpleSoundPool;
    private SoundPool happySoundPool;
    private SoundPool lolSoundPool;
    private SoundPool dotaSoundPool;
    private int happyMove, simpleMerge, simpleMove;
    private long lastDoubleKillTime = 0;

    public SoundManager(Context context) {
        this.context = context;
        Log.e("gao", "build Successfully");
        isFirstBlood = true;
        rank = new int[4][17];
        mergeCount = new int[4][6];
        firstBlood = new int[4];
    }

    public void setSoundType() {
        if (Setting.Runtime.Sound.enabled) {
            switch (Setting.Runtime.Sound.SOUND_PACK) {
                case 0:
                    soundPool = dotaSoundPool;
                    break;
                case 1:
                    soundPool = lolSoundPool;
                    break;
                case 2:
                    soundPool = happySoundPool;
                    break;
                case 3:
                    soundPool = simpleSoundPool;
                    break;
            }
        }
    }

    /**
     * 清空游戏中的音效记录
     */
    public void clear() {
        isFirstBlood = true;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        happySoundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 0);
        simpleSoundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 0);
        lolSoundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 0);
        dotaSoundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 0);
        //load dota
        firstBlood[0] = dotaSoundPool.load(context, R.raw.dotafirstblood, 1);
        rank[0][4] = dotaSoundPool.load(context, R.raw.dotakillingspree, 1); //16 三杀
        rank[0][5] = dotaSoundPool.load(context, R.raw.dotadominating, 1);
        rank[0][6] = dotaSoundPool.load(context, R.raw.dotamegakill, 1);
        rank[0][7] = dotaSoundPool.load(context, R.raw.dotaunstoppable, 1);
        rank[0][8] = dotaSoundPool.load(context, R.raw.dotawhickedsick, 1);
        rank[0][9] = dotaSoundPool.load(context, R.raw.dotamonsterkill, 1);
        rank[0][10] = dotaSoundPool.load(context, R.raw.dotagdlike, 1);
        rank[0][11] = dotaSoundPool.load(context, R.raw.dotaholyshit, 1);

        mergeCount[0][2] = dotaSoundPool.load(context, R.raw.dotadoublekill, 1);
        mergeCount[0][3] = dotaSoundPool.load(context, R.raw.dotatriplekill, 1);
        mergeCount[0][4] = dotaSoundPool.load(context, R.raw.dotaultrakill, 1);
        mergeCount[0][5] = dotaSoundPool.load(context, R.raw.dotarampage, 1);

        //load lol
        firstBlood[1] = lolSoundPool.load(context, R.raw.lolfirstblood, 1);
        rank[1][4] = lolSoundPool.load(context, R.raw.lolkillingspree, 1); //16 三杀
        rank[1][5] = lolSoundPool.load(context, R.raw.lolrampage, 1);
        rank[1][6] = lolSoundPool.load(context, R.raw.lolunstopped, 1);
        rank[1][7] = lolSoundPool.load(context, R.raw.loldominating, 1);
        rank[1][8] = lolSoundPool.load(context, R.raw.lolgodlike, 1);
        rank[1][9] = lolSoundPool.load(context, R.raw.lollegendary, 1);
        rank[1][10] = lolSoundPool.load(context, R.raw.lolshutdown, 1);
        rank[1][11] = lolSoundPool.load(context, R.raw.lolshutdown, 1);

        mergeCount[1][2] = lolSoundPool.load(context, R.raw.loldoublekill, 1);
        mergeCount[1][3] = lolSoundPool.load(context, R.raw.loltriplekill, 1);
        mergeCount[1][4] = lolSoundPool.load(context, R.raw.lolquatrekill, 1);
        mergeCount[1][5] = lolSoundPool.load(context, R.raw.lolpentakill, 1);
        //load happy
        rank[2][3] = happySoundPool.load(context, R.raw.happygood, 1);
        rank[2][4] = happySoundPool.load(context, R.raw.happygreat, 1);
        rank[2][5] = happySoundPool.load(context, R.raw.happygreat, 1);
        rank[2][6] = happySoundPool.load(context, R.raw.happyexcellent, 1);
        rank[2][7] = happySoundPool.load(context, R.raw.happyexcellent, 1);
        rank[2][8] = happySoundPool.load(context, R.raw.happyamazing, 1);
        rank[2][9] = happySoundPool.load(context, R.raw.happyamazing, 1);
        rank[2][10] = happySoundPool.load(context, R.raw.happyunbelieveable, 1);
        rank[2][11] = happySoundPool.load(context, R.raw.happyunbelieveable, 1);
        happyMove = happySoundPool.load(context, R.raw.happymove, 1);

        //load simple
        simpleMove = simpleSoundPool.load(context, R.raw.simplemove, 1);
        simpleMerge = simpleSoundPool.load(context, R.raw.simplemerge, 1);
        Log.e("gao", "load Successfully");
        return null;
    }

    /**
     * 对用户的有效操作播放音效
     *
     * @param maxRank  本次操作产生的方块的的最大rank
     * @param mergeNum 本次操作同时合并的方块的对数
     */
    public void playProcess(int maxRank, int mergeNum) {
        if (Setting.Runtime.Sound.enabled) {
            switch (Setting.Runtime.Sound.SOUND_PACK) {
                case 3:
                    if (maxRank > 1) {
                        soundPool.play(simpleMerge, 1, 1, 0, 0, 1);
                    } else {
                        soundPool.play(simpleMove, 1, 1, 0, 0, 1);
                    }
                    break;

                case 2:
                    if (maxRank < 3) {
                        soundPool.play(happyMove, 1, 1, 0, 0, 1);
                    } else {
                        soundPool.play(rank[2][Math.min(maxRank, 11)], 1, 1, 0, 0, 1);
                    }
                    break;

                default:
                    if (maxRank >= 2 && isFirstBlood) {
                        soundPool.play(firstBlood[Setting.Runtime.Sound.SOUND_PACK], 1, 1, 0, 0, 1);
                        isFirstBlood = false;
                    }
                    if (maxRank >= 7) {
                        soundPool.play(rank[Setting.Runtime.Sound.SOUND_PACK][Math.min(maxRank, 11)], 1, 1, 0, 0, 1);
                    } else if (mergeNum >= 2 && System.currentTimeMillis() - lastDoubleKillTime > 2000) {
                        soundPool.play(mergeCount[Setting.Runtime.Sound.SOUND_PACK][Math.min(mergeNum, 5)], 1, 1, 0, 0, 1);
                        lastDoubleKillTime = System.currentTimeMillis();
                    } else if (maxRank >= 4) {
                        soundPool.play(rank[Setting.Runtime.Sound.SOUND_PACK][Math.min(maxRank, 11)], 1, 1, 0, 0, 1);
                    }
            }
        }
    }
}


//public class SoundManager {
//
//    private boolean isFirstBlood;
//    private SoundPool soundPool;
//    private int[] rank;
//    private int mergeCount[];
//    private int firstBlood;
//    private int move, merge;
//    private long lastDoubleKillTime = 0;
//
//    public SoundManager() {
//        isFirstBlood = true;
//        rank = new int[17];
//        mergeCount = new int[6];
//    }
//
//    /**
//     * 清空游戏中的音效记录
//     */
//    public void clear() {
//        isFirstBlood = true;
//    }
//
//    /**
//     * 在游戏开始前加载音频文件
//     * @param context  音效播放的上下文
//     */
//    public void load(Context context) {
//        soundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 0);
//        if (Setting.Sound.SOUND_PACK >= 2) {
//            isFirstBlood = false;
//        }
//        switch (Setting.Sound.SOUND_PACK) {
//            case 0: //dota
//                firstBlood = soundPool.load(context, R.raw.dotafirstblood, 1);
//                rank[4] = soundPool.load(context, R.raw.dotakillingspree, 1); //16 三杀
//                rank[5] = soundPool.load(context, R.raw.dotadominating, 1);
//                rank[6] = soundPool.load(context, R.raw.dotamegakill, 1);
//                rank[7] = soundPool.load(context, R.raw.dotaunstoppable, 1);
//                rank[8] = soundPool.load(context, R.raw.dotawhickedsick, 1);
//                rank[9] = soundPool.load(context, R.raw.dotamonsterkill, 1);
//                rank[10] = soundPool.load(context, R.raw.dotagdlike, 1);
//                rank[11] = soundPool.load(context, R.raw.dotaholyshit, 1);
//
//                mergeCount[2] = soundPool.load(context, R.raw.dotadoublekill, 1);
//                mergeCount[3] = soundPool.load(context, R.raw.dotatriplekill, 1);
//                mergeCount[4] = soundPool.load(context, R.raw.dotaultrakill, 1);
//                mergeCount[5] = soundPool.load(context, R.raw.dotarampage, 1);
//
//                break;
//            case 1://lol
//
//                firstBlood = soundPool.load(context, R.raw.lolfirstblood, 1);
//                rank[4] = soundPool.load(context, R.raw.lolkillingspree, 1); //16 三杀
//                rank[5] = soundPool.load(context, R.raw.lolrampage, 1);
//                rank[6] = soundPool.load(context, R.raw.lolunstopped, 1);
//                rank[7] = soundPool.load(context, R.raw.loldominating, 1);
//                rank[8] = soundPool.load(context, R.raw.lolgodlike, 1);
//                rank[9] = soundPool.load(context, R.raw.lollegendary, 1);
//                rank[10] = soundPool.load(context, R.raw.lolshutdown, 1);
//                rank[11] = soundPool.load(context, R.raw.lolshutdown, 1);
//
//                mergeCount[2] = soundPool.load(context, R.raw.loldoublekill, 1);
//                mergeCount[3] = soundPool.load(context, R.raw.loltriplekill, 1);
//                mergeCount[4] = soundPool.load(context, R.raw.lolquatrekill, 1);
//                mergeCount[5] = soundPool.load(context, R.raw.lolpentakill, 1);
//                break;
//
//            case 2://happy
//                rank[3] = soundPool.load(context, R.raw.happygood, 1);
//                rank[4] = soundPool.load(context, R.raw.happygreat, 1);
//                rank[5] = soundPool.load(context, R.raw.happygreat, 1);
//                rank[6] = soundPool.load(context, R.raw.happyexcellent, 1);
//                rank[7] = soundPool.load(context, R.raw.happyexcellent, 1);
//                rank[8] = soundPool.load(context, R.raw.happyamazing, 1);
//                rank[9] = soundPool.load(context, R.raw.happyamazing, 1);
//                rank[10] = soundPool.load(context, R.raw.happyunbelieveable, 1);
//                rank[11] = soundPool.load(context, R.raw.happyunbelieveable, 1);
//                move  = soundPool.load(context, R.raw.happymove, 1);
//                break ;
//
//            default: //simple
//                move  = soundPool.load(context, R.raw.simplemove, 1);
//                merge  = soundPool.load(context, R.raw.simplemerge , 1);
//        }
//    }
//
//    /**
//     * 对用户的有效操作播放音效
//     * @param maxRank  本次操作产生的方块的的最大rank
//     * @param mergeNum  本次操作同时合并的方块的对数
//     */
//    public void playProcess(int maxRank, int mergeNum) {
//        if (Setting.Sound.enabled) {
//            switch (Setting.Sound.SOUND_PACK) {
//                case 3:
//                    if(maxRank>1)
//                        soundPool.play(merge, 1, 1, 0, 0, 1);
//                    else
//                        soundPool.play(move, 1, 1, 0, 0, 1);
//                    break ;
//
//                case 2:
//                    if(maxRank<3)
//                        soundPool.play(move, 1, 1, 0, 0, 1);
//                    else
//                        soundPool.play(rank[Math.min(maxRank, 11)], 1, 1, 0, 0, 1);
//                    break ;
//
//                default:
//                    if (maxRank >= 2 && isFirstBlood) {
//                        soundPool.play(firstBlood, 1, 1, 0, 0, 1);
//                        isFirstBlood = false;
//                    }
//                    if (maxRank >= 7)
//                        soundPool.play(rank[Math.min(maxRank, 11)], 1, 1, 0, 0, 1);
//                    else if (mergeNum >= 2&& System.currentTimeMillis()-lastDoubleKillTime > 2000){
//                        soundPool.play(mergeCount[Math.min(mergeNum, 5)], 1, 1, 0, 0, 1);
//                        lastDoubleKillTime = System.currentTimeMillis();
//                    }
//                    else if(maxRank >= 4)
//                        soundPool.play(rank[Math.min(maxRank, 11)], 1, 1, 0, 0, 1);
//            }
//        }
//    }
//}

