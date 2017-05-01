package com.akkunsoft.mario.Screens;

import com.akkunsoft.mario.MarioBros;
import com.akkunsoft.mario.Scenes.Hud;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
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
    //Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;

    public PlayScreen(MarioBros mb){
        this.game = mb;
        // create a camera to follow Mario through cam world
        this.gameCamera = new OrthographicCamera();
        // create a fitviewport to maintain virtual aspect ratio defined in MarioBros
        this.gamePort = new FitViewport(MarioBros.V_WIDTH,MarioBros.V_HEIGHT,this.gameCamera);
        // create HUD to show score, level and other informations
        this.hud = new Hud(this.game.batch);
        // create a tmx map loader
        this.mapLoader = new TmxMapLoader();
        // load level 1-1
        this.map = mapLoader.load("level1.tmx");
        // render level 1-1
        this.renderer = new OrthogonalTiledMapRenderer(map);
        // center the level
        this.gameCamera.position.set(this.gamePort.getWorldWidth()/2, this.gamePort.getWorldHeight()/2,0);

        this.world = new World(new Vector2(0,0), true); //true to sleep objects at rest
        this.b2dr = new Box2DDebugRenderer();

        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixture = new FixtureDef();
        Body body;

        //Create ground bodies and fixtures
        for(MapObject ob : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) ob).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(rect.getX()+rect.getWidth()/2, rect.getY()+rect.getHeight()/2);

            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
            fixture.shape = shape;
            body.createFixture(fixture);
        }
        //Create pipe bodies and fixtures
        for(MapObject ob : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) ob).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(rect.getX()+rect.getWidth()/2, rect.getY()+rect.getHeight()/2);

            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
            fixture.shape = shape;
            body.createFixture(fixture);
        }
        //Create brick bodies and fixtures
        for(MapObject ob : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) ob).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(rect.getX()+rect.getWidth()/2, rect.getY()+rect.getHeight()/2);

            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
            fixture.shape = shape;
            body.createFixture(fixture);
        }
        //Create coin bodies and fixtures
        for(MapObject ob : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) ob).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(rect.getX()+rect.getWidth()/2, rect.getY()+rect.getHeight()/2);

            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
            fixture.shape = shape;
            body.createFixture(fixture);
        }
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
        this.renderer.render();
        //renderer the Box2DDebugLines
        this.b2dr.render(this.world, this.gameCamera.combined);
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
