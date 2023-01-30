package io.github.teamten5;

public class NullController extends Controller {

    public boolean doCombination = false;

    public void update(float delta) {}

    public NullController() {
        this(0, 0, 0, 0, false);
    }

    public NullController(float x, float y, float facing_y, float facing_x, boolean doAction) {
        this.x = x;
        this.y = y;
        this.doAction = doAction;
        this.facing_x = facing_x;
        this.facing_y = facing_y;

        doCombination = false;
    }

}
