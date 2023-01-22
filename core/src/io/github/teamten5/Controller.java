package io.github.teamten5;

public abstract class Controller {

    double x = 0;
    double y = 0;
    boolean doAction = false;
    boolean doCombination = false;

    abstract void update(float delta);
}