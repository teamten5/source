package io.github.teamten5;

public class Station extends Entity {

    final boolean serving;
    final StationType type;
    Orientation orientation;

    public Station(int x, int y, Orientation orientation, StationType type) {
        super(type.sizeX, type.sizeY, type.image, x, y);
        this.serving = type.serving;

        this.type = type;
        this.orientation = orientation;
    }

    @Override
    void render() {

    }

    @Override
    void update(int time) {

    }
}
