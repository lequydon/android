package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.items.VocaItem;
import com.mygdx.game.models.VocaModel;
import com.mygdx.game.types.ScreenType;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class VocaScreen implements Screen {
    MyGdxGame game;
    Group group, groupItem;
    public Label.LabelStyle lb;
    private int topicid;
    private ArrayList<VocaModel> mLists;
    private int index;

    public VocaScreen(MyGdxGame game, int topicid) {
        this.game = game;
        this.topicid = topicid;
        this.index = 0;
        this.mLists = new ArrayList<VocaModel>();
        this.group = new Group();
        this.groupItem = new Group();
        Gdx.app.error("ID Voca", "" + topicid);
    }

    @Override
    public void show() {
        game.stage.addActor(this.group);
        game.stage.addActor(this.groupItem);

        Texture bgvoca1 = new Texture("vocabulary/bgvoca1.png");
        bgvoca1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image image = new Image(new TextureRegion(bgvoca1));
        this.group.addActor(image);

        Texture bgvc2 = new Texture("vocabulary/bgvoca2.png");
        bgvc2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image image1 = new Image(bgvc2);
        image1.setPosition(0, 0);
        this.group.addActor(image1);

        Texture girl = new Texture("vocabulary/girl.png");
        girl.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image image2 = new Image(girl);
        image2.setPosition(207, 816);
        this.group.addActor(image2);

        // tạo actor nằm trong group item
        Texture bgvc3 = new Texture("vocabulary/bgvoca3.png");
        bgvc3.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image image3 = new Image(bgvc3);
        image3.setPosition(84, 521);
        this.group.addActor(image3);

        Texture icon = new Texture("topic/back.png");
        icon.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ImageButton back = new ImageButton(new TextureRegionDrawable(new TextureRegion(icon)));
        back.setPosition(18, 1180);
        this.group.addActor(back);
        back.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                VocaScreen.this.game.setScreen(new TopicScreen(VocaScreen.this.game, ScreenType.Vocabulary));
            }
        });

        BitmapFont bf = new BitmapFont(Gdx.files.internal("font/Roboto_VI_30.fnt"));
        lb = new Label.LabelStyle(bf, Color.WHITE);

        this.loadData();
    }

    private void loadData() {
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        //request.setUrl("http://relax365.net:3000/vocalbulary/review/words/getlist?topicid=" + topicid + "&size=100&index=0");
        request.setUrl("https://testapi.io/api/lequydon/learnenglish/voca/"+topicid+"");
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                final String str = httpResponse.getResultAsString();
                Gdx.app.error("Voca", "Im your response: " + str);
                try {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            Gdx.app.error("Voca Runnable", str);
                            JsonValue jsonValue = new JsonReader().parse(str);
                            JsonValue data = jsonValue.get("data");
                            int i = 0;
                            for (JsonValue jv : data) {
                                i++;
                                Gdx.app.error("item", jv.getString("word"));
                                VocaModel model = new VocaModel(
                                        jv.getInt("wid"),
                                        jv.getString("word"),
                                        jv.getString("spelling"),
                                        jv.getString("image"),
                                        jv.getString("sound"),
                                        jv.getString("mean")
                                );
                                Gdx.app.error("model", model.getWord());
                                Gdx.app.error("model", model.getSpelling());
                                mLists.add(model);
                            }

                            if (mLists.size() > 0) {
                                index = 0;
                                NewItemVoca(index);
                            }
                        }
                    });
                } catch (Exception ignored) {
                }
            }

            @Override
            public void failed(Throwable t) {

            }

            @Override
            public void cancelled() {

            }
        });
    }

    private void NewItemVoca(int i) {
        // lấy đối tượng voca model
        VocaModel itemModel = mLists.get(i);
        Gdx.app.error("NewItemvoca", itemModel.getWord());
        Gdx.app.error("NewItemvoca", itemModel.getSpelling());

        // kiểm tra và xóa item cũ nếu có
        if (groupItem != null) {
            groupItem.clearChildren();
        }

        // tạo và hiển thị từ vựng đầu tiên
        VocaItem vocaItem = new VocaItem(itemModel, game.shaderProgram) {
            @Override
            protected void OnClickNext() {
                index++;
                // kiểm tra còn đối tượng thì tiếp tục hiển thị
                if (index < mLists.size()) {
                    NewItemVoca(index);
                } else {
                    // nếu hết đối tượng thì dừng
                }
            }
        };
        groupItem.addActor(vocaItem);


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
