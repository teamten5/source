package io.github.teamten5;

import com.badlogic.gdx.graphics.Texture;

public abstract class EntityType {

    int sizeX;
    int sizeY;
    Texture image;

    public EntityType(int sizeX, int sizeY, Texture image) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.image = image;
    }

}
