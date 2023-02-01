package io.github.teamten5;

import com.badlogic.gdx.utils.JsonValue;
import java.util.HashMap;

public class OrderType {

    final ItemType itemOrdered;
    final float expiresAfter = 6f;

    public OrderType(ItemType itemOrdered) {
        this.itemOrdered = itemOrdered;
    }

    public static  OrderType read(
          JsonValue jsonValue,
          HashMap<String, ItemType> items
    ) {

        return new OrderType(
              items.get(jsonValue.getString("item"))
        );
    }

    public Order placeOrder() {
        return new Order(this);
    }
}
