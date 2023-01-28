package io.github.teamten5;

import com.badlogic.gdx.utils.JsonValue;
import java.util.HashMap;

public class ChefAction {

    final StationType station;
    final ItemType input;
    final ItemType output;
    final int timeToComplete;
    final boolean chefRequired;
    final boolean pickupOutput;
    final boolean resetTime = true;

    public ChefAction(StationType station, ItemType input, ItemType output, int time,
          boolean chefRequired, boolean pickupOutput) {

        if (!chefRequired && pickupOutput) {
            throw new InvalidGameDataException(
                  "If pick-output is true chef-required must also be ture");
        }

        this.station = station;
        this.input = input;
        this.output = output;
        this.timeToComplete = time;
        this.chefRequired = chefRequired;
        this.pickupOutput = pickupOutput;
    }

    public static ChefAction read(
          JsonValue jsonValue,
          HashMap<String, ItemType> items,
          HashMap<String, StationType> stations
    ) {
        return new ChefAction(
              stations.get(jsonValue.getString("station")),
              items.get(jsonValue.getString("input")),
              items.get(jsonValue.getString("output")),
              jsonValue.getInt("time"),
              jsonValue.getBoolean("chef-required"),
              jsonValue.getBoolean("pickup-output")
        );
    }
}
