package com.mygdx.game.models;

import java.util.ArrayList;

public class LookchooseModel extends VocaModel{
    private ArrayList<VocaModel> other;

    public LookchooseModel(int wid, String word, String spelling, String image, String sound, String mean, ArrayList<VocaModel> other) {
        super(wid, word, spelling, image, sound, mean);

        this.other = other;
    }

    public ArrayList<VocaModel> getOther() {
        return other;
    }

    public void setOther(ArrayList<VocaModel> other) {
        this.other = other;
    }
}
