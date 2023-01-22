package io.github.teamten5;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class Entity extends EntityType {

    int x;
    int y;

    public Entity(int sizeX, int sizeY, Texture image, int x, int y) {
        super(sizeX, sizeY, image);
        this.x = x;
        this.y = y;
    }

    abstract void render(Batch batch);

    abstract void update(float delta);
}
