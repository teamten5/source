package io.github.teamten5;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Item extends Entity {

    final ItemType type;

    public Item(ItemType type, int sizeX, int sizeY, Texture image, int x, int y) {
        super(sizeX, sizeY, image, x, y);
        this.type = type;
    }

    @Override
    void render(Batch batch) {
        batch.draw(type.image, x * 32, y * 32);
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
