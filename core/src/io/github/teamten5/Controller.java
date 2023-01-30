package io.github.teamten5;

public abstract class Controller {

    float x = 0;
    float y = 0;

    float facing_x = 0;
    float facing_y = 0;
    boolean doAction = false;
    boolean doCombination = false;

    boolean swapChef = false;

    abstract void update(float delta);
}