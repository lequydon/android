package com.mygdx.game.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.images.VocaImage;
import com.mygdx.game.models.VocaModel;


public abstract class VocaItem extends Group {
    private VocaModel vocaModel;
    private ShaderProgram shaderProgram;
    private Sound sound;

    public VocaItem(VocaModel vocaModel, ShaderProgram shaderProgram) {
        super();
        this.vocaModel = vocaModel;
        this.shaderProgram = shaderProgram;
        try {
            this.sound = Gdx.audio.newSound(Gdx.files.internal(vocaModel.getSound()));
            sound.setVolume(sound.loop(), 100f);
        } catch (Exception ignored) {

        }

        this.CreateImage();

        this.CreateButtonSound();

        this.CreateButtonNext();

        this.CreateWord();

        //this.CreateSpelling();

        this.CreateMean();
    }

    private void CreateWord() {
        BitmapFont bf = new BitmapFont(Gdx.files.internal("font/Baloo_72.fnt"));
        Label.LabelStyle lb = new Label.LabelStyle(bf, Color.BLACK);
        Label label = new Label(vocaModel.getWord(), lb);
        label.setWidth(this.getWidth());
        //label.setPosition(360, 412);
        label.setPosition(360, 380);
        label.setAlignment(Align.center);
        this.addActor(label);

    }

    private void CreateSpelling() {
        BitmapFont bf = new BitmapFont(Gdx.files.internal("font/Baloo_pa.fnt"));
        Label.LabelStyle lb = new Label.LabelStyle(bf, Color.valueOf("#676767"));
        Label label = new Label(vocaModel.getSpelling(), lb);
        label.setWidth(this.getWidth());
        label.setPosition(360, 335);
        label.setAlignment(Align.center);
        this.addActor(label);
    }

    private void CreateMean() {
        BitmapFont bf = new BitmapFont(Gdx.files.internal("font/Baloo_50.fnt"));
        Label.LabelStyle lb = new Label.LabelStyle(bf, Color.valueOf("#0083ed"));
        Label label = new Label(vocaModel.getMean(), lb);
        label.setWidth(this.getWidth());
        label.setPosition(360, 290);
        label.setAlignment(Align.center);
        this.addActor(label);
    }

    private void CreateButtonNext() {
        Texture bgvc6 = new Texture("vocabulary/icon_next.png");
        bgvc6.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ImageButton next = new ImageButton(new TextureRegionDrawable(new TextureRegion(bgvc6)));
        next.setPosition(552, 486);
        next.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    OnClickNext();
                } catch (Exception ignored) {

                }
            }
        });
        this.addActor(next);
    }

    protected abstract void OnClickNext();

    private void CreateImage() {
        Texture v_image = new Texture(vocaModel.getImage());
        VocaImage vocaImage = new VocaImage(v_image, shaderProgram);
        vocaImage.setSize(515, 380);
        vocaImage.setPosition(104, 549.5f);
        this.addActor(vocaImage);
    }

    private void CreateButtonSound() {
        Texture bgvc5 = new Texture("vocabulary/icon_sound.png");
        bgvc5.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ImageButton btnSound = new ImageButton(new TextureRegionDrawable(new TextureRegion(bgvc5)));
        btnSound.setPosition(71, 486);
        btnSound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (sound != null) {
                    sound.play();
                }
                // phát âm thanh
            }
        });
        this.addActor(btnSound);
    }
}
