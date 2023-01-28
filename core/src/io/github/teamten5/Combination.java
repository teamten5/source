package io.github.teamten5;

import com.badlogic.gdx.utils.JsonValue;
import java.util.HashMap;

public class Combination {

    final public StationType station;
    final public ItemType startingChefHolding;
    final public ItemType startingOnStation;
    final public ItemType endingChefHolding;
    final public ItemType endingOnStation;

    public Combination(
          StationType station,
          ItemType startingChefHolding,
          ItemType startingOnStation,
          ItemType endingChefHolding,
          ItemType endingOnStation) {
        this.station = station;
        this.startingChefHolding = startingChefHolding;
        this.startingOnStation = startingOnStation;
        this.endingChefHolding = endingChefHolding;
        this.endingOnStation = endingOnStation;
    }

    public boolean isValid(StationType station, ItemType startingChefHolding,
          ItemType startingOnStation) {
        return this.station == station
              && this.startingChefHolding == startingChefHolding
              && this.startingOnStation == startingOnStation;
    }

    public static Combination[] read(
          JsonValue jsonValue,
          HashMap<String, ItemType> items,
          HashMap<String, StationType> stations
    ) {
        String[] combinationValidStations = jsonValue.get("allowed-stations").asStringArray();
        Combination[] combinations = new Combination[combinationValidStations.length];
        for (int i = 0; i < combinationValidStations.length; i++) {
            StationType combinationStation = stations.get(
                  jsonValue.get("allowed-stations").asStringArray()[i]);
            combinations[i] = new Combination(
                  combinationStation,
                  items.get(jsonValue.getString("start-chef")),
                  items.get(jsonValue.getString("start-station")),
                  items.get(jsonValue.getString("end-chef")),
                  items.get(jsonValue.getString("end-station"))
            );
        }

        return combinations;
    }
}
