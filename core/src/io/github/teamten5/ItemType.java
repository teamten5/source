package io.github.teamten5;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.JsonValue;

public class ItemType {

    Texture image;

    public ItemType(Texture image) {
        this.image = image;
    }

    static public ItemType read( JsonValue jsonValue) {
        return new ItemType(
              new Texture("images/" + jsonValue.getString("image"))
        );
    }

    void render(Batch batch, float x, float y) {
        batch.draw(image, x, y, 0.5f, 0.5f);
    }

}
