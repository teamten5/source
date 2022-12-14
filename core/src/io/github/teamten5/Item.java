package io.github.teamten5;

import com.badlogic.gdx.graphics.Texture;

public class Item extends Entity {

    public Item(int sizeX, int sizeY, Texture image, int x, int y) {
        super(sizeX, sizeY, image, x, y);
    }

    @Override
    void render() {

    }

    @Override
    void update(int time) {
        throw new UnsupportedOperationException();
    }
}
