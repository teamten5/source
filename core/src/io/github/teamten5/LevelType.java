package io.github.teamten5;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.JsonValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LevelType {

    final OrderType[] orderTypes;
    final StationLevel[] stations;
    final HashMap<StationType, HashMap<ItemType, ChefAction>> chefActions;
    final Combination[] combinations;
    final Texture backgroundTexture;
    public List<Rectangle> chefValidAreas;

    public int levelSizeX;
    public int levelSizeY;

    final public int startingReputation = 3;

    Texture floorTexture;

    public LevelType(OrderType[] orderTypes, StationLevel[] stations,
          HashMap<StationType, HashMap<ItemType, ChefAction>> chefActions,
          Combination[] combinations, Texture backgroundTexture,
          List<Rectangle> chefValidAreas, int levelSizeX, int levelSizeY, Texture floorTexture) {
        this.orderTypes = orderTypes;
        this.stations = stations;
        this.chefActions = chefActions;
        this.combinations = combinations;

        backgroundTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
        this.backgroundTexture = backgroundTexture;



        this.chefValidAreas = chefValidAreas;
        this.levelSizeX = levelSizeX;
        this.levelSizeY = levelSizeY;
        floorTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
        this.floorTexture = floorTexture;

    }

    public static LevelType read(
          JsonValue levelJson,
          HashMap<String, OrderType[]> allOrderTypes,
          HashMap<String, StationType> allStationTypes,
          HashMap<String, ChefAction> allChefActions,
          HashMap<String, Combination[]> allCombinations
    ) {

        JsonValue mapJSON = levelJson.get("map");

        // Textures

        JsonValue textureJSON = mapJSON.get("textures");

        Texture floorTexture = new Texture("images/" + textureJSON.getString("floor"));

        // Actions
        HashMap<StationType, HashMap<ItemType, ChefAction>> chefActions = new HashMap<>();

        for (String levelChefAction : levelJson.get("actions").asStringArray()) {
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

        // Background

        Texture backgroundTexture = new Texture("images/" + levelJson.getString("background"));

        // Map data

        // Full (for edge drawing)

        List<Rectangle> validAreaRectangles = new ArrayList<>();


        for (JsonValue rect: mapJSON.get("valid areas")) {
            validAreaRectangles.add(new Rectangle(
                  rect.getInt("x"),
                  rect.getInt("y"),
                  rect.getInt("dx"),
                  rect.getInt("dy")
                  ));
        }

        int max_x = Integer.MIN_VALUE, min_x = Integer.MAX_VALUE, max_y = Integer.MIN_VALUE, min_y = Integer.MAX_VALUE;
        for (Rectangle rect: validAreaRectangles) {
            // these rectangles always have integer values
            min_x = rect.getX() < min_x ? (int) rect.getX() : min_x;
            min_y = rect.getY() < min_y ? (int) rect.getY() : min_y;
            max_x = rect.getX() + rect.width > max_x ? (int) (rect.getX() + rect.width) : max_x;
            max_y = rect.getY() + rect.height > max_y ? (int) (rect.getY() + rect.height) : max_y;
        }

        // for edge pieces
        max_y += 1;
        max_x += 1;
        min_x -= 1;
        min_y -= 1;

        // adjustment so centre of the map is 0,0
        int bl_x = -(max_x-min_x)/2 + 1;
        int bl_y = -(max_y-min_y)/2 + 1;

        // Stations

        List<Rectangle> cutouts = new ArrayList<>(); // used when making levels

        JsonValue stationJSON = mapJSON.get("stations");
        StationLevel[] stations = new StationLevel[stationJSON.size];
        for (int i = 0; i < stationJSON.size; i++) {
            JsonValue currentStation = stationJSON.get(i);
            stations[i] = allStationTypes.get(currentStation.getString("type")).instantiate(
                  currentStation.getInt("x") + bl_x,
                  currentStation.getInt("y") + bl_y,
                  Orientation.fromString(currentStation.getString("facing"))
            );
            cutouts.add(new Rectangle(
                  stations[i].x - bl_x,
                  stations[i].y - bl_y,
                  stations[i].type.sizeX,
                  stations[i].type.sizeY
            ));
        }

        int map[][] = new int[max_x-min_x+1][max_y-min_y+1];

        for (Rectangle rect: validAreaRectangles) {
            for (int i = (int) rect.getX() - min_x; i < rect.getX() + rect.getWidth() - min_x; i++) {
                for (int j = (int) rect.getY() - min_y; j < rect.getY() + rect.getHeight() - min_y; j++) {
                    map[i][j] = 1;
                }
            }
        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 1) {
                    continue;
                }
                int[][] surronding = new int[3][3];
                int m_start = i == 0 ? 0 : -1;
                int m_end = i == map.length - 1 ? 0 : 1;
                int n_start = j == 0 ? 0 : -1;
                int n_end = j == map[0].length - 1 ? 0 : 1;
                for (int m = m_start; m <= m_end; m++) {
                    for (int n = n_start; n <= n_end; n++) {
                        surronding[m+1][n+1] = map[i+m][j+n];
                    }
                }
                int adjacent_sum = surronding[1][0] + surronding[0][1] + surronding[2][1] + surronding[1][2];
                if (adjacent_sum == 0) {
                    continue;
                } else if (adjacent_sum == 1) {

                } else if (adjacent_sum == 2) {

                } else if (adjacent_sum == 3) {

                } else if (adjacent_sum == 4) {

                }
            }

        }
        for (Rectangle rect: cutouts) {
            for (int i = (int) rect.getX() - min_x; i < rect.getX() + rect.getWidth() - min_x; i++) {
                for (int j = (int) rect.getY() - min_y; j < rect.getY() + rect.getHeight() - min_y; j++) {
                    map[i][j] = 0;
                }
            }
        }

        List<Rectangle> chefCollisionArea = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 1) {
                    map[i][j] = 0;
                    int n = i;
                    while (map[n + 1][j] == 1) {
                        n += 1;
                        map[n][j] = 0;

                    }
                    int m = j - 1;
                    boolean carryOn = true;
                    while (carryOn) {
                        m += 1;
                        for (int k = i; k <= n; k++) {
                            map[k][m] = 0;
                        }
                        for (int k = i; k <= n; k++) {
                            if (map[k][m + 1] == 0) {
                                carryOn = false;
                                break;
                            }
                        }
                    }
                    chefCollisionArea.add(new Rectangle(i +min_x + bl_x, j + min_y + bl_y, n - i + 1, m - j + 1));

                }
            }
        }
        System.out.println(chefCollisionArea);

        return new LevelType(
              Arrays.stream(levelJson.get("orders").asStringArray())
                    .flatMap(x -> Arrays.stream(allOrderTypes.get(x))).toArray(OrderType[]::new),
              stations,
              chefActions,
              Arrays.stream(levelJson.get("combinations").asStringArray())
                    .flatMap(x -> Arrays.stream(allCombinations.get(x))).toArray(Combination[]::new),
              backgroundTexture, chefCollisionArea, max_x-min_x, max_y-min_y, floorTexture);
    }

    public Level instantiate() {
        return new Level(this);
    }


    static boolean CheckArrayIsAllZero(int array[][]) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (array[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
