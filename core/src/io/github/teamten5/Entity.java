package io.github.teamten5;

import com.badlogic.gdx.graphics.Texture;

public abstract class Entity extends EntityType {

    int x;
    int y;

    public Entity(int sizeX, int sizeY, Texture image, int x, int y) {
        super(sizeX, sizeY, image);
        this.x = x;
        this.y = y;
    }

    abstract void render();

    abstract void update(int time);
}
