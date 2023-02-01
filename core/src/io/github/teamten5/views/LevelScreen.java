package io.github.teamten5.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.teamten5.Level;
import io.github.teamten5.LevelType;

public class LevelScreen implements Screen {

    private Level level;
    private OrthographicCamera camera;
    //private OrthographicCamera cameraUI;
    private Viewport viewport;
    //private Viewport viewportUI;

    private SpriteBatch batch;
    //private SpriteBatch batchUI;

    private ShapeRenderer shapeRenderer;
    float time = 0;

    final int GAME_SCALING = 1;
    final boolean SHOW_BOUNDING = true;

    public LevelScreen(LevelType levelType) {
        this.level = levelType.instantiate();

        camera = new OrthographicCamera(1536, 1000);
        camera.setToOrtho(false);
        viewport = new ScreenViewport(camera);

        //cameraUI = new OrthographicCamera(1536, 1000);
        //viewportUI = new ScreenViewport(cameraUI);

        batch = new SpriteBatch();
        // batchUI = new SpriteBatch();
        // batch.setProjectionMatrix();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        level.update(delta);

        // Matrix4 test = new Matrix4(new float[] {1,0,0,0, 0,1,0,0, 0,1,0,0, 0,0,0, 32});
        // System.out.println(camera.combined.mul(test));
        // viewport.apply();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        camera.update();

        viewport.setWorldSize(level.type.levelSizeX, level.type.levelSizeY);
        viewport.setScreenBounds((Gdx.graphics.getWidth() - level.type.levelSizeX * 32)/2,(Gdx.graphics.getHeight() - level.type.levelSizeY * 32)/2,level.type.levelSizeX * 32 * GAME_SCALING,level.type.levelSizeY * 32 * GAME_SCALING);
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        level.render(batch);
        batch.end();


        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeType.Line);
        time += delta;
        if (SHOW_BOUNDING) {
            shapeRenderer.setColor(0, 1, 0,1);
            for (Rectangle rect: level.type.chefValidAreas) {
                shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
            }
        }
        shapeRenderer.end();

        //cameraUI.update();
        //viewportUI.apply();


        //batchUI.setProjectionMatrix(cameraUI.combined);
        //batchUI.begin();
        //level.renderUI(batchUI);
        //batchUI.end();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        // viewportUI.update(width, height);
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
