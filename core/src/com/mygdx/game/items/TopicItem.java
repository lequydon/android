package com.mygdx.game.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.images.TopicImage;
import com.mygdx.game.models.TopicModel;

public class TopicItem extends Group {
    private TopicModel topicModel;
    private ShaderProgram shaderProgram;

    public TopicItem(TopicModel topicModel, ShaderProgram shaderProgram) {
        super();
        this.topicModel = topicModel;
        this.shaderProgram = shaderProgram;
        this.setSize(268, 261);

        this.CreateImage();

        this.CreateName();
    }

    private void CreateName() {
        BitmapFont bf = new BitmapFont(Gdx.files.internal("font/Roboto_VI_30.fnt"));
        Label.LabelStyle lb = new Label.LabelStyle(bf, Color.valueOf("#d45b01"));
        Label label = new Label(topicModel.getName(), lb);
        label.setWidth(this.getWidth());
        label.setPosition(0, 29);
        label.setAlignment(Align.center);
        this.addActor(label);
    }

    private void CreateImage() {
        Texture t_i_bg = new Texture("topic/topic_item_bg.png");
        this.addActor(new Image(t_i_bg));
        Texture t_image = new Texture(topicModel.getImage());
        TopicImage topicImage = new TopicImage(t_image, shaderProgram);
        topicImage.setSize(245, 184);
        topicImage.setPosition(12, 67);
        this.addActor(topicImage);
    }
}