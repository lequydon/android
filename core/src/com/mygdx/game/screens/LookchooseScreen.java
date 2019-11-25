package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.layout.LookChooseQuestion;
import com.mygdx.game.models.LookchooseModel;
import com.mygdx.game.models.VocaModel;
import com.mygdx.game.types.ScreenType;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class LookchooseScreen implements Screen{
    public MyGdxGame game;
    private Group group;
    private Label.LabelStyle lb;
    private Image imgLoading;
    private int topicid, indexQuestion, total;
    private ArrayList<LookchooseModel> mLists;
    private ArrayList<Sound> mSoundLists;
    private LookChooseQuestion lookchooseQuestion;
    private Label totalQuestion;

    public LookchooseScreen(MyGdxGame game, int topicid) {
        this.game = game;
        this.topicid = topicid;
        this.indexQuestion = 0;
        this.group = new Group();
        this.total = 0;
        this.mLists = new ArrayList<LookchooseModel>();
        this.mSoundLists = new ArrayList<Sound>();
        BitmapFont bf = new BitmapFont(Gdx.files.internal("font/Baloo_50.fnt"));
        lb = new Label.LabelStyle(bf, Color.BLACK);

        this.group.setSize(720, 1280);
        game.stage.addActor(this.group);

        Texture bglc1 = new Texture("vocabulary/bgvoca1.png");
        bglc1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image image = new Image(new TextureRegion(bglc1));
        this.group.addActor(image);

        Texture bglc2 = new Texture("lookchoose/bglc2.png");
        bglc2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image image1 = new Image(bglc2);
        image1.setPosition(0, 0);
        this.group.addActor(image1);

        Texture icon = new Texture("topic/back.png");
        icon.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ImageButton back = new ImageButton(new TextureRegionDrawable(new TextureRegion(icon)));
        back.setPosition(18, 1180);
        this.group.addActor(back);
        back.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LookchooseScreen.this.game.setScreen(new TopicScreen(LookchooseScreen.this.game, ScreenType.LookAndChoose));
            }
        });

        Texture icon1 = new Texture("loading/icon.png");
        icon1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        imgLoading = new Image(icon1);
        imgLoading.setPosition(319, 640);
        imgLoading.setOrigin(imgLoading.getWidth() / 2f, imgLoading.getHeight() / 2f);
        imgLoading.addAction(Actions.forever(Actions.rotateBy(-360, 0.5f)));
        this.group.addActor(imgLoading);
    }
    @Override
    public void show() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        });
    }
    private void loadData() {
        this.indexQuestion = 0;
        this.mLists.clear();
        this.mSoundLists.clear();
        this.imgLoading.setVisible(true);

        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        //request.setUrl("http://relax365.net:3000/vocalbulary/play/words/getlist?topicid=" + topicid + "&size=100&index=0");
        request.setUrl("https://testapi.io/api/lequydon/learnenglish/lookchoose/" + topicid + "");
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String str = httpResponse.getResultAsString();
                Gdx.app.error("list", str);
                JsonValue jsonValue = new JsonReader().parse(str);
                final JsonValue data = jsonValue.get("data");
                try {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            if (imgLoading != null){
                                imgLoading.setVisible(false);
                            }
                            for (JsonValue jv : data) {
                                Gdx.app.error("answer", jv.getString("word"));

                                JsonValue others = jv.get("other");
                                ArrayList<VocaModel> mOtherLists = new ArrayList<VocaModel>();
                                for(JsonValue other : others){
                                    VocaModel vocaModel = new VocaModel(
                                            other.getInt("wid"),
                                            other.getString("word"),
                                            other.getString("spelling"),
                                            other.getString("image"),
                                            other.getString("sound"),
                                            other.getString("mean")
                                    );
                                    mOtherLists.add(vocaModel);
                                }
                                LookchooseModel model = new LookchooseModel(
                                        jv.getInt("wid"),
                                        jv.getString("word"),
                                        jv.getString("spelling"),
                                        jv.getString("image"),
                                        jv.getString("sound"),
                                        jv.getString("mean"),
                                        mOtherLists
                                );
                                mLists.add(model);

                                try {
                                    Sound sound = Gdx.audio.newSound(Gdx.files.internal(model.getSound()));
                                    mSoundLists.add(sound);
                                }
                                catch (Exception ignored){

                                }
                            }
                            if (mLists.size() > 0){
                                NewQuestion();
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
    private void NewQuestion() {
        total ++;
        LookchooseModel itemModel = mLists.get(indexQuestion);
        Sound sound = mSoundLists.get(indexQuestion);

        if (totalQuestion == null){
            totalQuestion = new Label("Câu"+ total,lb);
            totalQuestion.setWidth(group.getWidth() / 2f);
            totalQuestion.setAlignment(Align.center);
            totalQuestion.setPosition(group.getWidth() / 2f - totalQuestion.getWidth() / 2f, group.getHeight() - 100);
            group.addActor(totalQuestion);
        }
        totalQuestion.setText("Câu" + total);

        lookchooseQuestion = new LookChooseQuestion(itemModel,sound,game.shaderProgram) {
            @Override
            public void NextQuestions() {
                lookchooseQuestion.remove();
                indexQuestion ++;
                // kiểm tra còn câu hỏi không
                if (indexQuestion <mLists.size()){
                    NewQuestion();
                }else {
                    // hết câu hỏi hiển thị kết thúc
                    loadData();
                }
            }

        };
        lookchooseQuestion.setSize(720, 1150);
        lookchooseQuestion.setPosition(0,0);
        lookchooseQuestion.initInterface();
        this.group.addActor(lookchooseQuestion);
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
        this.group.clearChildren();
        this.group.remove();

    }
}
