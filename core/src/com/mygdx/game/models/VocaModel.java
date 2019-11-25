package com.mygdx.game.models;

public class VocaModel {
    private int wid;
    private String word;
    private String spelling;
    private String image;
    private String sound;
    private String mean;

    public VocaModel(int wid, String word, String spelling, String image, String sound, String mean) {
        this.wid = wid;
        this.word = word;
        this.spelling = spelling;
        this.image = image;
        this.sound = sound;
        this.mean = mean;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }
}
