package io.github.teamten5;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;

public class StationType {

    final boolean serving;
    int sizeX;
    int sizeY;
    Texture image;
    public StationType(int sizeX, int sizeY, Texture image, boolean serving) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.serving = serving;
        this.image = image;
    }


    public static StationType read(JsonValue jsonValue) {
        return new StationType(
              jsonValue.getInt("size-x"),
              jsonValue.getInt("size-y"),
              new Texture("images/" + jsonValue.getString("image")),
              jsonValue.getBoolean("serving")
        );
    }

    public StationLevel instantiate(int x, int y, Orientation orientation) {
        return new StationLevel(x, y, orientation, this);
    }
}
