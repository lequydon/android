package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.MyGdxGame;

public class LoadingScreen implements Screen {
    MyGdxGame game;
    Group group;
    public Label.LabelStyle lb;

    public LoadingScreen(MyGdxGame game){
        this.game = game;
        this.group = new Group();
    }
    @Override
    public void show() {
        game.stage.addActor(this.group);

        Texture bg1 = new Texture("loading/bg1.png");
        bg1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image image = new Image(new TextureRegion(bg1));
        this.group.addActor(image);

        Texture bg2 = new Texture("loading/bg2.png");
        bg2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image image1 = new Image(bg2);
        image1.setPosition(0, 790);
        this.group.addActor(image1);

        Texture book = new Texture("loading/book.png");
        book.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image image2 = new Image(book);
        image2.setPosition(7, 960);
        this.group.addActor(image2);

        Texture hoctv = new Texture("loading/hoctv.png");
        hoctv.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image image3 = new Image(hoctv);
        image3.setPosition(205, 1111);
        this.group.addActor(image3);

        Texture tienganh = new Texture("loading/tienganh.png");
        tienganh.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image image4 = new Image(tienganh);
        image4.setPosition(205, 994);
        this.group.addActor(image4);

        final Texture group = new Texture("loading/group.png");
        bg2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image image5 = new Image(group);
        image5.setPosition(15, 201);
        this.group.addActor(image5);

        Texture icon = new Texture("loading/icon.png");
        icon.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image image6 = new Image(icon);
        image6.setPosition(319, 117);
        image6.setOrigin(image6.getWidth() / 2f, image6.getHeight() / 2f);
        this.group.addActor(image6);
        image6.addAction(Actions.forever(Actions.rotateBy(-360, 0.5f)));
        this.group.addAction(Actions.delay(3f, Actions.run(new Runnable() {
            @Override
            public void run() {
                LoadingScreen.this.game.setScreen(new MenuScreen(LoadingScreen.this.game));
            }
        })));



    }
    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        this.group.remove();

    }

    @Override
    public void dispose() {

    }

}
