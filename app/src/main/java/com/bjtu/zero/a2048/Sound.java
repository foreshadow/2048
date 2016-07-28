package com.bjtu.zero.a2048;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Sound {

    private  boolean haveSound ;
    private SoundPool mSoundPool ;
    private int move,merge , good ,great,excellent ,amazing ,unbelieveable ;

    public Sound(){
        haveSound=true ;
        mSoundPool =new SoundPool(2, AudioManager.STREAM_MUSIC,0);
    }

    public void load(Context context){
        merge= mSoundPool.load(context,R.raw.merge,1);
        move = mSoundPool.load(context,R.raw.move,1);
        good = mSoundPool.load(context,R.raw.good,1);
        great = mSoundPool.load(context,R.raw.great,1);
        excellent = mSoundPool.load(context,R.raw.excellent,1);
        amazing = mSoundPool.load(context,R.raw.amazing,1);
        unbelieveable = mSoundPool.load(context,R.raw.unbelieveable,1);
    }

    public void setSound(boolean  haveSound){
        this.haveSound = haveSound ;
    }

    public  void playProcess(int maxRank){
        if(haveSound)
        {
            switch (maxRank){
                case 1:  mSoundPool.play(move,1,1,0,0,1); break ;
                case 5:  mSoundPool.play(good,1,1,0,0,1); break ;
                case 6:  mSoundPool.play(good,1,1,0,0,1); break ;
                case 7:  mSoundPool.play(great,1,1,0,0,1); break ;
                case 8:  mSoundPool.play(great,1,1,0,0,1); break ;
                case 9:  mSoundPool.play(excellent,1,1,0,0,1); break ;
                case 10:  mSoundPool.play(amazing,1,1,0,0,1); break ;
                case 11:  mSoundPool.play(unbelieveable,1,1,0,0,1); break ;
                default: mSoundPool.play(merge,1,1,0,0,1); break ;
            }
        }
    }

    public void playGameOver(){

    }

    public void startGame(){

    }

}
