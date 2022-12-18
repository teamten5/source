package io.github.teamten5;

import com.badlogic.gdx.utils.JsonValue;
import java.util.HashMap;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class OrderType {

    final ItemType itemOrdered;

    public OrderType(ItemType itemOrdered) {
        this.itemOrdered = itemOrdered;
    }

    @Contract("_, _ -> new")
    public static @NotNull OrderType read(
          @NotNull JsonValue jsonValue,
          @NotNull HashMap<String, ItemType> items
    ) {

        return new OrderType(
              items.get(jsonValue.getString("item"))
        );
    }

    public Order placeOrder() {
        return new Order(this);
    }
}
