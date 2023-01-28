package io.github.teamten5;

import com.badlogic.gdx.utils.JsonValue;
import java.util.Arrays;
import java.util.HashMap;

public class LevelType {

    final OrderType[] orderTypes;
    final StationLevel[] stations;
    final HashMap<StationType, HashMap<ItemType, ChefAction>> chefActions;
    final Combination[] combinations;

    public LevelType(OrderType[] orderTypes, StationLevel[] stations,
          HashMap<StationType, HashMap<ItemType, ChefAction>> chefActions,
          Combination[] combinations) {
        this.orderTypes = orderTypes;
        this.stations = stations;
        this.chefActions = chefActions;
        this.combinations = combinations;
    }

    public static LevelType read(
          JsonValue jsonValue,
          HashMap<String, OrderType[]> allOrderTypes,
          HashMap<String, StationType> allStationTypes,
          HashMap<String, ChefAction> allChefActions,
          HashMap<String, Combination[]> allCombinations
    ) {

        JsonValue mapJSON = jsonValue.get("map");

        // Stations

        JsonValue stationJSON = mapJSON.get("stations");
        StationLevel[] stations = new StationLevel[stationJSON.size];
        for (int i = 0; i < stationJSON.size; i++) {
            JsonValue currentStation = stationJSON.get(i);
            stations[i] = allStationTypes.get(currentStation.getString("type")).instantiate(
                  currentStation.getInt("x"),
                  currentStation.getInt("y"),
                  Orientation.fromString(currentStation.getString("facing"))
            );
        }

        // Actions
        HashMap<StationType, HashMap<ItemType, ChefAction>> chefActions = new HashMap<>();

        for (String levelChefAction : jsonValue.get("actions").asStringArray()) {
            ChefAction chefAction = allChefActions.get(levelChefAction);
            HashMap<ItemType, ChefAction> stationChefActions;
            if (chefActions.containsKey(chefAction.station)) {
                stationChefActions = chefActions.get(chefAction.station);
            } else {
                stationChefActions = new HashMap<>();
                chefActions.put(chefAction.station, stationChefActions);
            }

            /*if (stationChefActions.containsKey(chefAction.input)) {
                throw new InvalidGameDataException("Level has at least two ChefActions with the same input and station");
            }*/

            stationChefActions.put(chefAction.input, chefAction);
        }

        return new LevelType(
              Arrays.stream(jsonValue.get("orders").asStringArray())
                    .flatMap(x -> Arrays.stream(allOrderTypes.get(x))).toArray(OrderType[]::new),
              stations,
              chefActions,
              Arrays.stream(jsonValue.get("combinations").asStringArray())
                    .flatMap(x -> Arrays.stream(allCombinations.get(x))).toArray(Combination[]::new)
        );
    }

    public Level instantiate() {
        return new Level(this);
    }
}
