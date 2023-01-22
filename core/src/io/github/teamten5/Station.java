package io.github.teamten5;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Station extends Entity {

    final boolean serving;
    final StationType type;
    Orientation orientation;
    Item holding = null;
    private boolean doAction = false;

    public Station(int x, int y, Orientation orientation, StationType type) {
        super(type.sizeX, type.sizeY, type.image, x, y);
        this.serving = type.serving;

        this.type = type;
        this.orientation = orientation;
    }

    @Override
    void render(Batch batch) {
        batch.draw(type.image, x * 32, y * 32);
        if (holding != null) {
            holding.render(batch, x * 32, y * 32);
        }
        doAction = false;
    }

    @Override
    void update(float delta) {

    }

    ItemType getHolding() {
        if (holding != null) {
            return holding.type;
        }
        return null;
    }

    void setHolding(Item holding) {
        this.holding = holding;
    }

    void doAction() {
        doAction = true;
    }
}
