package com.akkunsoft.mario.Screens;

import com.akkunsoft.mario.MarioBros;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Akkun on 4/30/2017.
 */

public class PlayScreen implements Screen {
    private MarioBros game;
    Texture texture;

    public PlayScreen(MarioBros mb){
        this.game = mb;
        texture = new Texture("badLogic.jpg");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.game.batch.begin();
        this.game.batch.draw(texture,0,0);
        this.game.batch.end();
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
