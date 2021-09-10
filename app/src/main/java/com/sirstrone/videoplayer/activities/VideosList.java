package com.sirstrone.videoplayer.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;

import com.sirstrone.videoplayer.R;
import com.sirstrone.videoplayer.adapters.VideoAdapter;
import com.sirstrone.videoplayer.models.VideoModel;
import com.sirstrone.videoplayer.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author sirstrone
 * @date: 9/10/21
 */
public class VideosList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<VideoModel> videoModelList;
    private VideoAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_list);


        String path = getIntent().getStringExtra("folder_path");
        videoModelList = new ArrayList<>();
        recyclerView = findViewById(R.id.videoRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        parseAllVideo(path);
        adapter = new VideoAdapter(this, videoModelList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void parseAllVideo(String folderpath) {
        try {
            String[] proj = {MediaStore.Video.Media._ID,
                    MediaStore.Video.Media.DATA,
                    MediaStore.Video.Media.TITLE,
                    MediaStore.Video.Media.SIZE,
                    MediaStore.Video.Media.DURATION};
            Cursor videocursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    proj, null, null, null);
            if (videocursor != null) {
                if (videocursor.moveToFirst()) {
                    int name_index = videocursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE);
                    int duration_index = videocursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
                    int path_index = videocursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                    int video_id = 0;
                    do {
                        String name = videocursor.getString(name_index);
                        String filepath = videocursor.getString(path_index);
                        String duration = MyUtils.milisecondToHour(videocursor.getLong(duration_index));
                        if (filepath.contains(folderpath)) {
                            VideoModel video = new VideoModel(video_id, name, filepath, duration);
                            videoModelList.add(video);
                            video_id++;
                        }
                    } while (videocursor.moveToNext());
                }
                videocursor.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
