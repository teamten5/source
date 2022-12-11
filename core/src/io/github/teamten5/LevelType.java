package io.github.teamten5;

import com.badlogic.gdx.utils.JsonValue;
import java.util.Arrays;
import java.util.HashMap;
import org.jetbrains.annotations.NotNull;

public class LevelType {

    final OrderType[] orderTypes;
    final Station[] stations;
    final HashMap<ItemType, HashMap<StationType, ChefAction>> chefActions;
    final Combination[] combinations;

    public LevelType(OrderType[] orderTypes, Station[] stations,
          HashMap<ItemType, HashMap<StationType, ChefAction>> chefActions,
          Combination[] combinations) {
        this.orderTypes = orderTypes;
        this.stations = stations;
        this.chefActions = chefActions;
        this.combinations = combinations;
    }

    public static @NotNull LevelType read(
          @NotNull JsonValue jsonValue,
          @NotNull HashMap<String, OrderType[]> allOrderTypes,
          @NotNull HashMap<String, StationType> allStationTypes,
          @NotNull HashMap<String, HashMap<StationType, ChefAction>> allChefActions,
          @NotNull HashMap<String, Combination[]> allCombinations
    ) {

        JsonValue mapJSON = jsonValue.get("map");

        // Stations

        JsonValue stationJSON = mapJSON.get("stations");
        Station[] stations = new Station[stationJSON.size];
        for (int i = 0; i < stationJSON.size; i++) {
            JsonValue currentStation = stationJSON.get(i);
            stations[i] = allStationTypes.get(currentStation.getString("type")).instantiate(
                  currentStation.getInt("x"),
                  currentStation.getInt("y"),
                  Orientation.fromString(currentStation.getString("facing"))
            );
        }

        // Actions

        @SuppressWarnings("unchecked")
        HashMap<StationType, ChefAction>[] levelChefActionsArray = Arrays.stream(
                    jsonValue.get("actions").asStringArray())
              .map(allChefActions::get).toArray(HashMap[]::new);

        HashMap<ItemType, HashMap<StationType, ChefAction>> chefActions = new HashMap<>();
        for (HashMap<StationType, ChefAction> chefActionHashMap : levelChefActionsArray) {
            if (chefActionHashMap.isEmpty()) {
                continue;
            }

            ItemType inputType = chefActionHashMap.entrySet().iterator().next().getValue().input;

            if (!chefActions.containsKey(inputType)) {
                chefActions.put(inputType, new HashMap<>());
            }
            chefActions.get(inputType).putAll(chefActionHashMap);
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
