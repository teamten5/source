package io.github.teamten5;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Chef extends Entity {

    final Level level;
    private Controller controller;
    private ItemType holding;

    public Chef(int x, int y, Level level) {
        this(x, y, level, new NullController());
    }

    public Chef(int x, int y, Level level, Controller controller) {
        super(80, 80, new Texture("images/onion.png"), x, y);
        this.level = level;
        this.controller = controller;
    }

    @Override
    void render(Batch batch) {
        batch.draw(image, x, y, 16, 16);
        if (holding != null) {
            holding.render(batch, x, y);
        }
    }

    @Override
    void update(float delta) {
        controller.update(delta);
        x = x + (int) (controller.x);
        y = y + (int) (controller.y);
        if (controller.doCombination) {
            Station closestStation = level.ClosestStation(x, y);
            if (closestStation != null) {
                doCombination(closestStation);
            }
        }

        // You cannot do an action if you are holding something
        if (controller.doAction && holding == null) {
            Station closestStation = level.ClosestStation(x, y);
            if (closestStation != null) {
                doAction(closestStation);
            }
        }
        if (controller.swapChef) {
            level.swapChefs(this, controller);
        }
    }

    void setController(Controller controller) {
        this.controller = controller;
    }

    void doCombination(Station station) {
        for (Combination combination : level.type.combinations) {
            if (combination.isValid(station.stationLevel.type, this.getHolding(), station.getHolding())) {

                holding = combination.endingChefHolding;
                station.setHolding(combination.endingOnStation);

                break;
            }
            ;
        }
    }

    void doAction(Station station) {
        station.doAction();
    }

    ItemType getHolding() {
        return holding;
    }
}
