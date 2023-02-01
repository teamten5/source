package io.github.teamten5;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class Chef{

    final Level level;
    private Controller controller;
    private ItemType holding;
    float x;
    float y;
    Texture image;

    float sizeX = 0.5f;
    float sizeY = 0.5f;

    public Chef(int x, int y, Level level) {
        this(x, y, level, new NullController());
    }

    public Chef(int x, int y, Level level, Controller controller) {
        this.level = level;
        this.controller = controller;
        this.x = x;
        this.y = y;
        this.image = new Texture("images/chef_Forwards.png");
    }


    void render(Batch batch) {
        batch.draw(image, x, y, sizeX, sizeY * 2);
        if (holding != null) {
            holding.render(batch, x, y);
        }
    }

    Boolean isPositionValid(float x, float y) {
        boolean bl = false, br = false, tl = false, tr = false;
        for (Rectangle rect: level.type.chefValidAreas) {
            if (rect.contains(x,y)) {bl = true;}
            if (rect.contains(x + sizeX,y)) {br = true;}
            if (rect.contains(x,y + sizeY)) {tl = true;}
            if (rect.contains(x + sizeX,y + sizeY)) {tr = true;}
        }
        return bl && br && tl && tr;
    }

    void update(float delta) {
        controller.update(delta);
        float newx = x + controller.x;
        float newy = y + controller.y;

        if (isPositionValid(newx, newy)) {
            x = newx;
            y = newy;
        } else if (isPositionValid(x, newy)) {
            y = newy;
        } else if (isPositionValid(newx, y)) {
            x = newx;
        }
        if (controller.doCombination) {
            Station closestStation = level.closestStation(x + controller.facing_x, y + controller.facing_y);
            if (closestStation != null) {
                doCombination(closestStation);
            }
        }

        // You cannot do an action if you are holding something
        if (controller.doAction && holding == null) {
            Station closestStation = level.closestStation(x + controller.facing_y, y + controller.facing_y);
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
        }
    }

    void doAction(Station station) {
        station.doAction();
    }

    ItemType getHolding() {
        return holding;
    }
}
