package com.sirstrone.videoplayer.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.sirstrone.videoplayer.R;
import com.sirstrone.videoplayer.models.VideoModel;
import com.sirstrone.videoplayer.utils.TempVideoList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sirstrone
 * @date: 9/10/21
 */
public class VideoPlayerActivity extends Activity {

    PlayerView playerView;
    SimpleExoPlayer player;
    List<VideoModel> video_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player_activity);


        Intent intent = getIntent();
        int video_id = intent.getIntExtra("video_id", 0);
        video_list = new ArrayList<>();
        video_list = TempVideoList.getInstance().getData();

        playerView = findViewById(R.id.player);
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        player.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, getPackageName()));
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();


        MediaSource[] mediaSources = new MediaSource[video_list.size()];
        for (int i = 0; i < mediaSources.length; i++) {
            String s = video_list.get(i).getPath();
            mediaSources[i] = new ProgressiveMediaSource.Factory(dataSourceFactory, extractorsFactory)
                    .createMediaSource(Uri.parse(s));
        }
        MediaSource mediaSource = mediaSources.length == 1 ? mediaSources[0]
                : new ConcatenatingMediaSource(mediaSources);

        player.prepare(mediaSource);
        player.seekTo(video_id, C.TIME_UNSET);
        player.setPlayWhenReady(true);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }


}
