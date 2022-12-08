package io.github.teamten5;

import com.badlogic.gdx.utils.JsonValue;
import java.util.HashMap;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ChefAction {

    final StationType station;
    final ItemType input;
    final ItemType output;
    final int time;
    final boolean chefRequired;
    final boolean pickupOutput;

    public ChefAction(StationType station, ItemType input, ItemType output, int time,
          boolean chefRequired, boolean pickupOutput) {

        if (!chefRequired && pickupOutput) {
            throw new InvalidGameDataException(
                  "If pick-output is true chef-required must also be ture");
        }

        this.station = station;
        this.input = input;
        this.output = output;
        this.time = time;
        this.chefRequired = chefRequired;
        this.pickupOutput = pickupOutput;
    }

    @Contract("_, _, _ -> new")
    public static @NotNull HashMap<StationType, ChefAction> read(
          @NotNull JsonValue jsonValue,
          @NotNull HashMap<String, ItemType> items,
          @NotNull HashMap<String, StationType> stations
    ) {
        HashMap<StationType, ChefAction> actions = new HashMap<>();
        for (String actionValidStation : jsonValue.get("stations").asStringArray()) {
            StationType actionStation = stations.get(actionValidStation);
            actions.put(
                  actionStation,
                  new ChefAction(
                        actionStation,
                        items.get(jsonValue.getString("input")),
                        items.get(jsonValue.getString("output")),
                        jsonValue.getInt("time"),
                        jsonValue.getBoolean("chef-required"),
                        jsonValue.getBoolean("pickup-output")
                  )
            );
        }

        return actions;
    }
}
