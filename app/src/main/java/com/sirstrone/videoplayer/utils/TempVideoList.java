package com.sirstrone.videoplayer.utils;

import com.sirstrone.videoplayer.models.VideoModel;

import java.util.List;

/**
 * @author sirstrone
 * @date: 9/10/21
 */
public class TempVideoList {
    private static TempVideoList instance;
    private List<VideoModel> data = null;

    protected TempVideoList() {

    }

    public static TempVideoList getInstance() {
        if (instance == null) {
            instance = new TempVideoList();
        }
        return instance;
    }

    public List<VideoModel> getData() {
        return data;
    }

    public void setData(List<VideoModel> data) {
        this.data = data;
    }
}
