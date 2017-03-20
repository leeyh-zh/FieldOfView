package com.lyh.fieldofview.activity;

import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import com.lyh.fieldofview.R;
import com.lyh.fieldofview.base.ToolbarActivity;
import com.lyh.fieldofview.model.ItemList;
import com.lyh.fieldofview.widget.VideoController;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by lyh on 2017/3/20.
 */

public class PlayActivity extends ToolbarActivity implements SurfaceHolder.Callback,
        IMediaPlayer.OnBufferingUpdateListener, IMediaPlayer.OnCompletionListener, IMediaPlayer.OnPreparedListener,
        IMediaPlayer.OnInfoListener, IMediaPlayer.OnVideoSizeChangedListener {

    private static final int FLAG_HIDE_SYSTEM_UI = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    private SurfaceHolder surfaceHolder;
    private VideoController videoController;

    private IjkMediaPlayer mediaPlayer;

    private ItemList item;

    @Override
    public int setLayoutId() {
        return R.layout.play_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(FLAG_HIDE_SYSTEM_UI);
        videoController = (VideoController) findViewById(R.id.video_controller);
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        item = getIntent().getParcelableExtra("item");
        String playUrl = item.data.playUrl;
        getSupportActionBar().setTitle(item.data.title);

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFormat(PixelFormat.RGBA_8888);

        playVideo(playUrl);
    }

    private void playVideo(String path) {
        try {
            mediaPlayer = new IjkMediaPlayer();
            videoController.attachPlayer(mediaPlayer, item);
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnInfoListener(this);
            mediaPlayer.setOnVideoSizeChangedListener(this);
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer.setScreenOnWhilePlaying(true);
            videoController.startVideoPlayback(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean surfaceCreated;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!surfaceCreated) {
            videoController.surfaceCreated();
            surfaceCreated = true;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        videoController.calculateVideoWidthAndHeight();
        videoController.startVideoPlayback(surfaceHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        videoController.surfaceDestory();
    }

    @Override
    public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int percent) {
        videoController.onBufferUpdate(percent);
    }

    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {
        videoController.onCompleted();
    }

    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer, int what, int extra) {
        videoController.onInfo(what);
        return true;
    }

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        videoController.onPrepared(surfaceHolder);
    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int width, int height, int sar_num, int sar_den) {
        videoController.onVideoChanged(surfaceHolder, width, height);
    }

    private void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mediaPlayer.isPlaying()) {
            videoController.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer.isPlaying()) {
            videoController.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoController.surfaceDestory();
        release();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
