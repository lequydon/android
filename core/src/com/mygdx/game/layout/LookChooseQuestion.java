package com.mygdx.game.layout;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.images.VocaImage;
import com.mygdx.game.items.LookChooseAnswerItem;
import com.mygdx.game.models.LookchooseModel;
import com.mygdx.game.models.VocaModel;

import java.util.Random;

public abstract class LookChooseQuestion extends Group{
    private LookchooseModel lookchooseModel;
    private ShaderProgram shaderProgram;
    private Sound sound;

    public LookChooseQuestion(LookchooseModel itemModel, Sound sound, ShaderProgram shaderProgram) {
        super();
        this.lookchooseModel = itemModel;
        this.shaderProgram = shaderProgram;
        this.sound = sound;
    }
    public void initInterface(){
        this.CreateImageBackground();

        this.CreateImage();

        this.CreateButtonSound();

        this.CreateAnswer();

        this.CreateImageGirl();

        this.addAction(Actions.sequence(Actions.delay(0.2f), Actions.run(new Runnable() {
            @Override
            public void run() {
                sound.play();
            }
        })));
    }

    private void CreateImageGirl() {
        Texture girl = new Texture("lookchoose/girl.png");
        girl.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image image4 = new Image(girl);
        image4.setPosition(568, 638);
        this.addActor(image4);
    }

    private void CreateImageBackground() {
        Texture bglc3 = new Texture("lookchoose/bglc3.png");
        bglc3.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image image2 = new Image(bglc3);
        image2.setPosition(83, 695);
        this.addActor(image2);
    }

    private void CreateAnswer() {
        // tạo table chứa 4 đáp án
        Table table = new Table();
        table.setSize(this.getWidth(), 420);
        table.align(Align.center);
        table.setPosition(0, 150);
        this.addActor(table);

        Random random = new Random();
        // lấy vị trí đáp án đúng
        int indexAnswerTrue = random.nextInt(4);
        // add vị trí đáp án vào danh sách đáp án
        for (int i = 0; i < 4; i++) {
            if (i>0 && i % 2 == 0) table.row();
            VocaModel vocaModel;
            boolean check;
            // nếu i = vị trí đáp án đúng thì add index đáp án đúng
            if (i == indexAnswerTrue) {
                vocaModel = lookchooseModel;
                check = true;
            } else {
                vocaModel = lookchooseModel.getOther().get(i);
                check = false;
            }
            LookChooseAnswerItem answerItem = new LookChooseAnswerItem(vocaModel, check) {

                @Override
                protected void NextQuestions() {
                    // xử lý chuyển sang câu hỏi mới
                    LookChooseQuestion.this.NextQuestions();
                }
            };
            table.add(answerItem).pad(15);
        }
    }
    public abstract void NextQuestions();

    private void CreateImage() {
        Texture lc_image = new Texture(lookchooseModel.getImage());
        VocaImage lcImage = new VocaImage(lc_image, shaderProgram);
        lcImage.setSize(513, 379);
        lcImage.setPosition(104, 725);
        this.addActor(lcImage);
    }

    private void CreateButtonSound() {
        Texture bgvc5 = new Texture("vocabulary/icon_sound.png");
        bgvc5.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ImageButton btnSound = new ImageButton(new TextureRegionDrawable(new TextureRegion(bgvc5)));
        btnSound.setPosition(67, 655);
        btnSound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (sound != null) {
                    sound.play();
                }
            }
        });
        this.addActor(btnSound);
    }

}
