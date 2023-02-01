package io.github.teamten5;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Order {

    OrderType type;
    float timeSinceMade = 0f;

    public Order(OrderType type) {
        this.type = type;
    }

    public void render(Batch batch, int position) {

    }
    public boolean update(float delta) {
        timeSinceMade += delta;
        return timeSinceMade < type.expiresAfter;
    }
}
