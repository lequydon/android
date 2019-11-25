package com.mygdx.game.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TopicImage extends Actor {
    private ShaderProgram shaderProgram;
    private TextureRegion textureRegion;

    public TopicImage(Texture ttopic, ShaderProgram shaderProgram) {
        super();
        this.shaderProgram = shaderProgram;
        this.textureRegion = new TextureRegion(ttopic);

        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE1);
        Texture topicmask = new Texture(Gdx.files.internal("topic/topicmask.png"));
        topicmask.bind();
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (shaderProgram != null)
            batch.setShader(shaderProgram);
        if (textureRegion != null)
            batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        batch.setShader(null);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}

