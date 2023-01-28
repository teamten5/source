package io.github.teamten5;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Level {

    final LevelType type;
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
        for (Station station : stations) {
            station.update(delta);
        }
        for (Chef chef : chefs) {
            chef.update(delta);
        }
    }

    public void render(Batch batch) {
        ScreenUtils.clear(0, 0, 0, 1);
        for (Station station : stations) {
            station.render(batch);
        }
        for (Chef chef : chefs) {
            chef.render(batch);
        }
    }
    public Station ClosestStation(int x, int y) {
        for (Station station : stations) {
            if ((station.stationLevel.x * 32 <= x && x <= station.stationLevel.x * 32 + 32) && station.stationLevel.y * 32 <= y
                  && y <= station.stationLevel.y * 32 + 32) {
                return station;
            }
        }
        return null;
    }

    public void swapChefs(Chef oldChef, Controller controller) {
        chefs.get(unactiveChefs.pop()).setController(controller);
        oldChef.setController(new NullController(controller.x, controller.y, controller.doAction));
        unactiveChefs.add(chefs.indexOf(oldChef));

    }

}
