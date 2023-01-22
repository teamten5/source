package io.github.teamten5;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.LinkedList;

public class Level {

    final LevelType type;
    private LinkedList<Order> orders;
    private Chef[] chefs;
    private Station[] stations;


    public Level(LevelType type) {
        this.type = type;

        orders = new LinkedList<>();
        chefs = new Chef[]{new Chef(0, 0, this, new PlayerController())};
        stations = new Station[type.stations.length];
        System.arraycopy(type.stations, 0, stations, 0, type.stations.length);

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

}
