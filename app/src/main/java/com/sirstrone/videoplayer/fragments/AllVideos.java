package com.sirstrone.videoplayer.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sirstrone.videoplayer.R;
import com.sirstrone.videoplayer.adapters.VideoAdapter;
import com.sirstrone.videoplayer.models.VideoModel;
import com.sirstrone.videoplayer.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author sirstrone
 * @date: 9/10/21
 */
public class AllVideos extends Fragment {

    private RecyclerView recyclerView;
    private List<VideoModel> videoModelList;
    private VideoAdapter adapter;


    public AllVideos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all_videos, container, false);

        videoModelList = new ArrayList<>();

        recyclerView = v.findViewById(R.id.videoRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        parseAllVideo();
        adapter = new VideoAdapter(getContext(), videoModelList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return v;
    }

    private void parseAllVideo() {
        try {
            String[] proj = {MediaStore.Video.Media._ID,
                    MediaStore.Video.Media.DATA,
                    MediaStore.Video.Media.TITLE,
                    MediaStore.Video.Media.SIZE,
                    MediaStore.Video.Media.DURATION};
            Cursor videocursor = getContext().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
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
                        VideoModel video = new VideoModel(video_id, name, filepath, duration);
                        videoModelList.add(video);
                        video_id++;
                    } while (videocursor.moveToNext());
                }
                videocursor.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
