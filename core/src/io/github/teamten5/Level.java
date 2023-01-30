package io.github.teamten5;

import com.badlogic.gdx.graphics.g2d.Batch;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Level {

    public final LevelType type;
    private LinkedList<Order> orders;
    private ArrayList<Chef> chefs;
    private Station[] stations;
    private ArrayDeque<Integer> unactiveChefs;


    public Level(LevelType type) {
        this.type = type;

        orders = new LinkedList<>();
        unactiveChefs = new ArrayDeque<>(List.of(1));
        chefs = new ArrayList<>(Arrays.asList(
              new Chef(0, 0, this, new PlayerController()),
              new Chef(0, 1, this, new NullController())
        ));
        stations = Arrays.stream(type.stations).map(x -> x.instantiate(this)).toArray(Station[]::new);


    }

    public void update(float delta) {
        for (Chef chef : chefs) {
            chef.update(delta);
        }
        for (Station station : stations) {
            station.update(delta);
        }
    }

    public void render(Batch batch) {

        // TextureRegion test = new TextureRegion(type.backgroundTexture);
        // test.setRegion(0,0, type.backgroundTexture.getWidth()*50, type.backgroundTexture.getHeight()*50);
        // batch.draw(test, -1000, -1000, 2000, 2000);
        for (Station station : stations) {
            station.render(batch);
        }
        for (Chef chef : chefs) {
            chef.render(batch);
        }
    }
    public Station closestStation(float x, float y) {
        for (Station station : stations) {
            if ((station.stationLevel.x <= x && x <= station.stationLevel.x + station.stationLevel.type.sizeX) && station.stationLevel.y <= y
                  && y <= station.stationLevel.y + station.stationLevel.type.sizeY) {
                return station;
            }
        }
        return null;
    }

    public void swapChefs(Chef oldChef, Controller controller) {
        chefs.get(unactiveChefs.pop()).setController(controller);
        oldChef.setController(new NullController(controller.x, controller.y,  controller.facing_y, controller.facing_x, controller.doAction));
        unactiveChefs.add(chefs.indexOf(oldChef));

    }

}
