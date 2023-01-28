package io.github.teamten5;

public class NullController extends Controller {

    public boolean doCombination = false;

    public void update(float delta) {}

    public NullController() {
        this(0, 0, false);
    }

    public NullController(double x, double y, boolean doAction) {
        this.x = x;
        this.y = y;
        this.doAction = doAction;

        doCombination = false;
    }

}
