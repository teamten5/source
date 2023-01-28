package io.github.teamten5;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.JsonValue;

public class ItemType extends Entity {

    public ItemType(Texture image) {
        super(0, 0, image, 0, 0);
    }

    static public ItemType read( JsonValue jsonValue) {
        return new ItemType(
              new Texture("images/" + jsonValue.getString("image"))
        );
    }

    @Override
    void render(Batch batch) {
        batch.draw(image, x, y);
    }

    void render(Batch batch, int x, int y) {
        this.x = x;
        this.y = y;
        render(batch);
    }

    @Override
    void update(float delta) {
        throw new UnsupportedOperationException();
    }
}
