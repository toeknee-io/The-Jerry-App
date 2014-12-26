package com.loosefang.thejerryapp.soundboard;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.loosefang.thejerryapp.R;

/**
 * Created by Tony on 12/26/2014.
 */
public class SoundboardActivity extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soundboard_layout);

    }

    public void soundOne(View view){

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.test);
        mediaPlayer.start();

    }
}
