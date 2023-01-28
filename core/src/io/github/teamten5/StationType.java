package io.github.teamten5;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class StationType extends EntityType {

    final boolean serving;

    public StationType(int sizeX, int sizeY, Texture image, boolean serving) {
        super(sizeX, sizeY, image);
        this.serving = serving;
    }

    @Contract("_ -> new")
    public static @NotNull StationType read(@NotNull JsonValue jsonValue) {
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
