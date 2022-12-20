package io.github.teamten5;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.JsonValue;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ItemType extends EntityType {

    public ItemType(Texture image) {
        super(0, 0, image);
    }

    @Contract("_ -> new")
    static public @NotNull ItemType read(@NotNull JsonValue jsonValue) {
        Texture texture;
        String imagePath;
        try {
            imagePath = jsonValue.getString("image");
        }
        catch (IllegalArgumentException a){
            throw new InvalidGameDataException(String.format("Couldn't create item type %s as no image attribute defined!", jsonValue.name));
        }
        try {
            texture = new Texture("images/" + imagePath);
        }
        catch (GdxRuntimeException a){
            throw new InvalidGameDataException(String.format("Couldn't create item type %s as file %s could not be found!", jsonValue.name, "images/" + jsonValue.getString("image")));
        }
        return new ItemType(
              texture
        );
    }

    public Item instantiate(int x, int y) {
        return new Item(this.sizeX, this.sizeY, this.image, x, y);
    }
}
