package com.sirstrone.videoplayer.models;

/**
 * @author sirstrone
 * @date: 9/10/21
 */
public class FolderModel {
    private String name;
    private String path;

    public FolderModel(String name, String path) {
        this.name = name;
        this.path = path;
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
}
