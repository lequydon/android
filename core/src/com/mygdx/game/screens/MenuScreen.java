package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.types.ScreenType;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class MenuScreen implements Screen {
    MyGdxGame game;
    Group group;


    public MenuScreen(MyGdxGame game){
        this.game=game;
        this.group=new Group();
    }
    @Override
    public void show() {
        game.stage.addActor(this.group);

        Texture bgmenu = new Texture("menu/bgmenu.png");
        bgmenu.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        Image image=new Image(new TextureRegion(bgmenu));
        this.group.addActor(image);

        Texture group = new Texture("menu/group6.png");
        group.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        Image image1 = new Image(group);
        image1.setPosition(0,0);
        this.group.addActor(image1);

        Texture vc = new Texture(Gdx.files.internal("menu/vocabulary.png"));
        vc.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ImageButton voca = new ImageButton(new TextureRegionDrawable(new TextureRegion(vc)));
        voca.setPosition(205, 772);
        this.group.addActor(voca);
        voca.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        voca.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuScreen.this.game.setScreen(new TopicScreen(MenuScreen.this.game, ScreenType.Vocabulary));
            }
        });

        Texture boy = new Texture("menu/boy.png");
        boy.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image image3 = new Image(boy);
        image3.setPosition(115, 735);
        this.group.addActor(image3);

        Texture lc = new Texture(Gdx.files.internal("menu/lookchoose.png"));
        lc.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ImageButton lookchoose = new ImageButton(new TextureRegionDrawable(new TextureRegion(lc)));
        lookchoose.setPosition(205, 420);
        this.group.addActor(lookchoose);
        lookchoose.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        lookchoose.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MenuScreen.this.game.setScreen(new TopicScreen(MenuScreen.this.game, ScreenType.LookAndChoose));
            }
        });

        Texture girl = new Texture("menu/girl.png");
        girl.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image image5 = new Image(girl);
        image5.setPosition(52, 406);
        this.group.addActor(image5);

        /*Texture sound = new Texture(Gdx.files.internal("menu/sound.png"));
        ImageButton music = new ImageButton(new TextureRegionDrawable(new TextureRegion(sound)));
        music.setPosition(612,1160);
        this.group.addActor(music);*/

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

    }

    @Override
    public void dispose() {

    }
}
