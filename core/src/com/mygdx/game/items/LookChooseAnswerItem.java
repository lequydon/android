package com.mygdx.game.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.models.VocaModel;

public abstract class LookChooseAnswerItem extends Group {
    private VocaModel vocaModel;
    private boolean check;
    private Image imageAnswer;

    public LookChooseAnswerItem(VocaModel vocaModel, boolean check) {
        this.vocaModel = vocaModel;
        this.check = check;
        this.setSize(317, 164);
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                OnClick();
            }
        });

        this.CreateImageBackground();

        this.CreateImageAnswer();

        this.CreateLabelWord();
    }

    private void CreateImageBackground() {
        Texture texture = new Texture("lookchoose/answer1.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ImageButton answer = new ImageButton(new TextureRegionDrawable(new TextureRegion(texture)));
        answer.setSize(317, 164);
        answer.setPosition(0, 0);
        this.addActor(answer);
    }

    private void CreateImageAnswer() {
        Texture textureBackground = new Texture("lookchoose/answer2.png");
        textureBackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        imageAnswer = new Image(textureBackground);
        imageAnswer.setVisible(false);
        imageAnswer.setSize(317, 164);
        imageAnswer.setPosition(0, 0);
        this.addActor(imageAnswer);
    }
    private void CreateLabelWord() {
        BitmapFont bf = new BitmapFont(Gdx.files.internal("font/Baloo_38.fnt"));
        Label.LabelStyle lb = new Label.LabelStyle(bf, Color.BLACK);
        Label label = new Label(vocaModel.getWord(), lb);
        Group labelGroup = new Group();
        labelGroup.setTransform(true);
        labelGroup.setSize(label.getWidth(), label.getHeight());

        if (this.getWidth() - 20 < label.getHeight()){
            labelGroup.setScale((this.getWidth() - 20) / label.getWidth());
            labelGroup.setPosition(10, this.getHeight() / 2f - labelGroup.getHeight() / 2f);
        }
        else {
            labelGroup.setPosition(this.getWidth() / 2f - labelGroup.getWidth() / 2f, this.getHeight() / 2f - labelGroup.getHeight() / 2f);
        }
        this.addActor(labelGroup);

        label.setPosition(labelGroup.getWidth() / 2f - label.getWidth() / 2f, labelGroup.getHeight() / 2f - label.getHeight() / 2f);
        labelGroup.addActor(label);
    }

    private void OnClick() {
        // xử lý kiểm tra đúng sai khi click item
        Texture textureDone;

        if(check){
            // xử lý khi chọn đúng
            textureDone = new Texture(Gdx.files.internal("lookchoose/astrue.png"));
            this.addAction(Actions.sequence(Actions.delay(0.5f), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            NextQuestions();
                        }})
            ));
        }else{
            // xử lý khi chọn sai
            textureDone = new Texture(Gdx.files.internal("lookchoose/asfalse.png"));
            imageAnswer.setVisible(true);
            this.setTouchable(Touchable.disabled);
        }
        textureDone.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image imageDone = new Image(textureDone);
        imageDone.setPosition(this.getWidth() - imageDone.getWidth() / 3f * 2f, this.getHeight() - imageDone.getHeight() / 3f * 2f);
        this.addActor(imageDone);
    }

    protected abstract void NextQuestions();
}
