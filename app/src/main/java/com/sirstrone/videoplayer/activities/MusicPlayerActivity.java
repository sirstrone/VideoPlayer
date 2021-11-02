package com.sirstrone.videoplayer.activities;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.sirstrone.videoplayer.R;
import com.sirstrone.videoplayer.models.MusicModel;
import com.sirstrone.videoplayer.utils.TempMusicList;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author sirstrone
 * @date: 9/10/21
 */
public class MusicPlayerActivity extends AppCompatActivity {
    List<MusicModel> musicList;

    PlayerView playerView;
    SimpleExoPlayer player;
    PlayerNotificationManager playerNotificationManager;
    int music_id;
    int play_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Music Player");
        setContentView(R.layout.activity_music_player);


        musicList = new ArrayList<>();
        musicList = TempMusicList.getInstance().getData();

        playerView = findViewById(R.id.exo_audio_player);
        PlayerControlView controls = findViewById(R.id.controls);

        music_id = getIntent().getIntExtra("music_id", 0);
        playerView.setControllerHideOnTouch(false);
        playerView.setShowShuffleButton(true);
        playerView.setDefaultArtwork(getApplicationContext().getResources().getDrawable(R.drawable.default_artwork));


        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        controls.setPlayer(player);


        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(
                        this, Util.getUserAgent(this, "Music Player"), null);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        MediaSource[] mediaSources = new MediaSource[musicList.size()];
        for (int i = 0; i < mediaSources.length; i++) {
            String s = musicList.get(i).getPath();
            mediaSources[i] = new ProgressiveMediaSource.Factory(dataSourceFactory, extractorsFactory)
                    .createMediaSource(Uri.parse(s));
        }
        MediaSource mediaSource = mediaSources.length == 1 ? mediaSources[0]
                : new ConcatenatingMediaSource(mediaSources);

        player.prepare(mediaSource);
        player.seekTo(music_id, C.TIME_UNSET);
        player.setPlayWhenReady(true);

        /*final NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(this,"exo_test",
                R.string.noti_id,
                5000,
                new PlayerNotificationManager.MediaDescriptionAdapter() {
                    @Override
                    public String getCurrentContentTitle(Player player) {
                        int track_number=0;
                        for (int i=0;i<musicList.size();i++) {
                            if (musicList.get(i).getId()==music_id) {
                                track_number=i;
                            }
                        }
                        return null;
                    }

                    @Nullable
                    @Override
                    public PendingIntent createCurrentContentIntent(Player player) {
                        Intent intent = new Intent(getApplicationContext(), MusicPlayerActivity.class);
                        intent.putExtra("music_id",player.getCurrentWindowIndex());
                        return PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    }

                    @Nullable
                    @Override
                    public String getCurrentContentText(Player player) {
                        int track_number=0;
                        for (int i=0;i<musicList.size();i++) {
                            if (musicList.get(i).getId()==music_id) {
                                track_number=i;
                            }
                        }
                        return null;
                    }

                    @Nullable
                    @Override
                    public Bitmap getCurrentLargeIcon(Player player, PlayerNotificationManager.BitmapCallback callback) {
                        return null;
                    }
                }
        );
        playerNotificationManager.setNotificationListener(new PlayerNotificationManager.NotificationListener() {
            @Override
            public void onNotificationStarted(int notificationId, Notification notification) {
                mNotificationManager.notify(notificationId,notification);
            }

            @Override
            public void onNotificationCancelled(int notificationId) {
                mNotificationManager.cancel(notificationId);
            }
        });
        playerNotificationManager.setPlayer(player);*/

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }

}
