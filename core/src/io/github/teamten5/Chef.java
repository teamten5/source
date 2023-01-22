package io.github.teamten5;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Chef extends Entity {

    final Level level;
    private Controller controller;
    private Item holding;

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
            holding.render(batch, x * 32, y * 32);
        }
    }

    Station ClosestStation() {
        for (Station station : level.type.stations) {
            if ((station.x * 32 <= x && x <= station.x * 32 + 32) && station.y * 32 <= y
                  && y <= station.y * 32 + 32) {
                return station;
            }
        }
        return null;
    }

    @Override
    void update(float delta) {
        controller.update(delta);
        x = x + (int) (controller.x);
        y = y + (int) (controller.y);
        if (controller.doCombination) {
            Station closestStation = ClosestStation();
            if (closestStation != null) {
                doCombination(closestStation);
            }
        }
        if (controller.doAction) {
            Station closestStation = ClosestStation();
            if (closestStation != null) {
                doAction(closestStation);
            }
        }
    }

    void SetController(Controller controller) {
        this.controller = controller;
    }

    void doCombination(Station station) {
        for (Combination combination : level.type.combinations) {
            if (combination.isValid(station.type, station.getHolding(), this.getHolding())) {

                holding = combination.endingChefHolding.instantiate();
                station.setHolding(combination.endingOnStation.instantiate());

                break;
            }
            ;
        }
    }

    void doAction(Station station) {
        station.doAction();
    }

    ItemType getHolding() {
        if (holding != null) {
            return holding.type;
        }
        return null;
    }
}
