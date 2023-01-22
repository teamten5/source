package io.github.teamten5.views;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.teamten5.Level;
import io.github.teamten5.LevelType;

public class LevelScreen implements Screen {

    private Level level;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    public LevelScreen(LevelType levelType) {
        this.level = levelType.instantiate();

        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        batch = new SpriteBatch();
        // batch.setProjectionMatrix();
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {

    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        // Matrix4 test = new Matrix4(new float[] {1,0,0,0, 0,1,0,0, 0,1,0,0, 0,0,0, 32});
        // camera.update();
        // System.out.println(camera.combined.mul(test));
        // batch.setProjectionMatrix(camera.combined.mul(test));
        level.update(delta);
        batch.begin();
        level.render(batch);
        batch.end();
    }

    /**
     * @param width
     * @param height
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {

    }
}
