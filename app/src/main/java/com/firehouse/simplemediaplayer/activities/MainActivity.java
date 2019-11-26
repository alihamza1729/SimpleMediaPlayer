package com.firehouse.simplemediaplayer.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.firehouse.simplemediaplayer.R;


public class MainActivity extends AppCompatActivity {

    VideoView mVideoView;
    MediaController mediaController;
    int postion;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mVideoView = findViewById(R.id.videoView);
        mediaPlayer = MediaPlayer.create(this, Uri.fromFile(VideoListActivity.fileArrayList.get(postion)));
        postion = getIntent().getIntExtra("position", -1);
        getSupportActionBar().hide();
        playVideo();
    }

    private void playVideo() {
        mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mediaController);
        mVideoView.setVideoPath(String.valueOf(VideoListActivity.fileArrayList.get(postion)));
        mVideoView.requestFocus();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoView.start();
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                 mVideoView.setVideoPath(String.valueOf(VideoListActivity.fileArrayList.get(postion = postion+1)));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mVideoView.stopPlayback();
    }
}
