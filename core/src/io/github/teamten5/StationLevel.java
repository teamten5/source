package io.github.teamten5;

public class StationLevel {
    final StationType type;
    final int x;
    final int y;
    Orientation orientation;

    public StationLevel(int x, int y, Orientation orientation, StationType type) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.type = type;
    }

    public Station instantiate(Level level) {
        return new Station(this, level);
    }
}
