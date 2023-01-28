package io.github.teamten5;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class PlayerController extends Controller {

    private boolean combinationJustDone = false;
    private boolean chefSwapJustDone = false;

    @Override
    public void update(float delta) {
        y = 0;
        x = 0;
        doCombination = false;
        doAction = false;
        swapChef = false;
        if (Gdx.input.isKeyPressed(Keys.W)) {
            y = y + 1.0;
        }

        if (Gdx.input.isKeyPressed(Keys.S)) {
            y = y - 1.0;
        }

        if (Gdx.input.isKeyPressed(Keys.A)) {
            x = x - 3.0;
        }

        if (Gdx.input.isKeyPressed(Keys.D)) {
            x = x + 3.0;
        }

        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) && !combinationJustDone) {
            doCombination = true;
            combinationJustDone = true;
        } else if (!Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
            combinationJustDone = false;
        }

        if (Gdx.input.isKeyPressed(Keys.TAB) && !chefSwapJustDone) {
            swapChef = true;
            chefSwapJustDone = true;
        } else if (!Gdx.input.isKeyPressed(Keys.TAB)) {
            chefSwapJustDone = false;
        }

        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
            doAction = true;
        }
    }
}
