package io.github.teamten5;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ItemType extends EntityType {

    public ItemType(Texture image) {
        super(0, 0, image);
    }

    @Contract("_ -> new")
    static public @NotNull ItemType read(@NotNull JsonValue jsonValue) {
        return new ItemType(
              new Texture("images/" + jsonValue.getString("image"))
        );
    }

    public Item instantiate() {
        return new Item(this, this.sizeX, this.sizeY, this.image, 0, 0);
    }
}
