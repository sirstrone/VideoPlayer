package com.sirstrone.videoplayer.models;

/**
 * @author sirstrone
 * @date: 9/10/21
 */
public class VideoModel {
    private int id;
    private String name;
    private String path;
    private String duration;

    public VideoModel(int id, String name, String path, String duration) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
