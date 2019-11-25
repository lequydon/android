package com.mygdx.game.models;

public class TopicModel {
    private int tid;
    private String name;
    private String image;

    public TopicModel(int tid, String name, String image) {
        this.tid = tid;
        this.name = name;
        this.image = image;
    }

    public int getTid() { return tid; }

    public void setTid(int tid) { this.tid = tid; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }
}
