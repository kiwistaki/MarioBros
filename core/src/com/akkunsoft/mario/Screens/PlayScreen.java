package com.akkunsoft.mario.Screens;

import com.akkunsoft.mario.MarioBros;
import com.akkunsoft.mario.Scenes.Hud;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Akkun on 4/30/2017.
 */

public class PlayScreen implements Screen {
    private MarioBros game;
    private OrthographicCamera gameCamera;
    private Viewport gamePort;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public PlayScreen(MarioBros mb){
        this.game = mb;
        // create a camera to follow Mario through cam world
        this.gameCamera = new OrthographicCamera();
        // create a fitviewport to maintain virtual aspect ratio defined in MarioBros
        this.gamePort = new FitViewport(MarioBros.V_WIDTH,MarioBros.V_HEIGTH,this.gameCamera);
        // create HUD to show score, level and other informations
        this.hud = new Hud(this.game.batch);
        // create a tmx map loader
        mapLoader = new TmxMapLoader();
        // load level 1-1
        map = mapLoader.load("level1.tmx");
        // render level 1-1
        renderer = new OrthogonalTiledMapRenderer(map);
        // center the level
        this.gameCamera.position.set(this.gamePort.getWorldWidth()/2, this.gamePort.getWorldHeight()/2,0);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        update(delta);
        //Clear the screen with black
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Render the level
        renderer.render();
        //Set the batch to now draw what the HUD camera sees
        this.game.batch.setProjectionMatrix(this.hud.stage.getCamera().combined);
        this.hud.stage.draw();
    }

    public void handleInput(float dt){
        if(Gdx.input.isTouched()){
            this.gameCamera.position.x += 100*dt;
        }
    }

    public void update(float dt){
        handleInput(dt);
        this.gameCamera.update();
        renderer.setView(this.gameCamera);
    }

    @Override
    public void resize(int width, int height) {
        this.gamePort.update(width, height);
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
