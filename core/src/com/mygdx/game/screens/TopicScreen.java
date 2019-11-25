package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.items.TopicItem;
import com.mygdx.game.models.TopicModel;
import com.mygdx.game.types.ScreenType;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeAction;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class TopicScreen implements Screen {
    public MyGdxGame game;
    private Group group;
    private Table table;
    public ScreenType screenType;
    private Image imgLoading;

    public TopicScreen(MyGdxGame game, ScreenType screenType) {
        this.game = game;
        this.group = new Group();
        this.screenType = screenType;
    }

    @Override
    public void show() {
        game.stage.addActor(this.group);

        Texture bgtopic1 = new Texture("menu/bgmenu.png");
        bgtopic1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image image = new Image(new TextureRegion(bgtopic1));
        this.group.addActor(image);

        Texture bgtopic2 = new Texture("topic/bgtopic2.png");
        bgtopic2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image image1 = new Image(bgtopic2);
        image1.setPosition(0, 1143);
        this.group.addActor(image1);

        Texture select = new Texture("topic/select.png");
        select.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image image2 = new Image(select);
        image2.setPosition(160, 1187);
        this.group.addActor(image2);
        Texture icon = new Texture("topic/back.png");
        icon.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ImageButton back = new ImageButton(new TextureRegionDrawable(new TextureRegion(icon)));
        back.setPosition(16, 1180);
        this.group.addActor(back);
        back.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TopicScreen.this.game.setScreen(new MenuScreen(TopicScreen.this.game));
            }
        });
        Texture icon1 = new Texture("topic/icon.png");
        icon1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        imgLoading = new Image(icon1);
        imgLoading.setPosition(319, 640);
        imgLoading.setOrigin(imgLoading.getWidth() / 2f, imgLoading.getHeight() / 2f);
        this.group.addActor(imgLoading);
        imgLoading.addAction(Actions.forever(Actions.rotateBy(-360, 0.5f)));

        table = new Table();
        table.columnDefaults(2);
        table.setWidth(720);

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setSize(720, 1133);
        this.group.addActor(scrollPane);

        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        //request.setUrl("http://relax365.net:3000/vocalbulary/topic/getlist");
        request.setUrl("https://testapi.io/api/lequydon/learnenglish/getlist");
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                final String str = httpResponse.getResultAsString();
                try {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            Gdx.app.error("list", str);
                            if (imgLoading != null) {
                                imgLoading.remove();
                            }
                            JsonValue jsonValue = new JsonReader().parse(str);
                            JsonValue data = jsonValue.get("data");
                            int i = 0;
                            for (JsonValue jv : data) {
                                i++;
                                TopicModel model = new TopicModel(jv.getInt("tid"), jv.getString("name"), jv.getString("image"));
                                // khởi tạo class item topic
                                TopicItem topicItem = new TopicItem(model, game.shaderProgram);
                                topicItem.setPosition(55, 15);
                                // set listener cho item
                                topicItem.addListener(new Item_OnClick(jv.getInt("tid")));
                                // Khởi tạo group item
                                Group groupItem = new Group();
                                groupItem.setSize(360, 298);
                                groupItem.addActor(topicItem);
                                // thêm item vào table
                                table.add(groupItem);
                                if (i % 2 == 0) {
                                    table.row();
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                }
            }

            @Override
            public void failed(Throwable t) {
            }

            @Override
            public void cancelled() {
            }
        });

 /*       for (int i=0; i<5; i++){
            Group group = new Group();
            group.setSize(360,298);
            TopicItem animals = new TopicItem("abc","Animal","topic/flower.png",game.shaderProgram);
            animals.setPosition(55,15);
            group.addActor(animals);

            Group group2=new Group();
            group2.setSize(360,298);
            TopicItem animals1 = new TopicItem("abc","Animal","topic/flower.png",game.shaderProgram);

            animals1.setPosition(34,15);
            group2.addActor(animals1);

            table.add(group);
            table.add(group2);
            table.row();
        }*/

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

    public class Item_OnClick extends ClickListener {
        public int topicid;

        public Item_OnClick(int topicid) {
            Item_OnClick.this.topicid = topicid;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            if (screenType == ScreenType.Vocabulary) {
                TopicScreen.this.game.setScreen(new VocaScreen(TopicScreen.this.game, topicid));
            } else {
                TopicScreen.this.game.setScreen(new LookchooseScreen(TopicScreen.this.game, topicid));
            }

        }
    }
}
