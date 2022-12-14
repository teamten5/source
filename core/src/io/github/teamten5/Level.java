package io.github.teamten5;

import java.util.LinkedList;

public class Level {

    final LevelType type;
    private LinkedList<Order> orders;
    private Chef[] chefs;

    public Level(LevelType type) {
        this.type = type;

        orders = new LinkedList<>();
        chefs = new Chef[]{new Chef(0, 0)};


    }
}
