package io.github.teamten5;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import io.github.teamten5.views.LevelScreen;
import io.github.teamten5.views.MenuScreen;
import io.github.teamten5.views.Views;
import java.util.ArrayList;
import java.util.HashMap;


public class Team15Game extends Game{
	private MenuScreen menuscreen;
	SpriteBatch batch;
	Texture img;
	HashMap<String, ItemType> itemsTypes = new HashMap<>();
	HashMap<String, OrderType[]> orderTypes = new HashMap<>();
	HashMap<String, StationType> stationTypes = new HashMap<>();
	HashMap<String, ChefAction> chefActions = new HashMap<>();
	HashMap<String, Combination[]> combinations = new HashMap<>();
	HashMap<String, LevelType> levelTypes = new HashMap<>();


	@Override
	public void create() {
		loadJSON();
		// menuscreen = new MenuScreen(this);
		Screen testLevelScreen = new LevelScreen(levelTypes.get("level1"));
		setScreen(testLevelScreen);
	}

	public void changeScreen(Views screen) {
		switch (screen) {
			case MENU:
				if (menuscreen == null) {
					menuscreen = new MenuScreen(this);
					this.setScreen(menuscreen);
					break;
				}

		}
	}

	void loadJSON() {
		JsonReader jsonReader = new JsonReader();

		// items.json

		JsonValue itemJSONRoot = jsonReader.parse(Gdx.files.internal("data/items.json"));
		Gdx.app.log("tag", String.format("there are %d ItemTypes found", itemJSONRoot.size));
		for (int i = 0; i < itemJSONRoot.size; i++) {
			itemsTypes.put(itemJSONRoot.get(i).name, ItemType.read(itemJSONRoot.get(i)));
		}

		// orders.json

		JsonValue orderJSONRoot = jsonReader.parse(Gdx.files.internal("data/orders.json"));

		// orders
		JsonValue ordersJSON = orderJSONRoot.get("orders");
		for (int i = 0; i < ordersJSON.size; i++) {
			orderTypes.put(ordersJSON.get(i).name,
				new OrderType[]{OrderType.read(ordersJSON.get(i), itemsTypes)});
		}

		// order groups
		JsonValue orderGroupsJSON = orderJSONRoot.get("order-groups");
		for (int i = 0; i < orderGroupsJSON.size; i++) {
			OrderType[] orderGroup = new OrderType[orderGroupsJSON.get(i).size];
			for (int j = 0; j < orderGroupsJSON.get(i).size; j++) {
				orderGroup[j] = orderTypes.get(orderGroupsJSON.get(i).get(j).asString())[0];
			}
			orderTypes.put(orderGroupsJSON.get(i).name, orderGroup);
		}

		// stations.json

		JsonValue stationJSONRoot = jsonReader.parse(Gdx.files.internal("data/stations.json"));
		for (int i = 0; i < stationJSONRoot.size; i++) {
			stationTypes.put(stationJSONRoot.get(i).name, StationType.read(stationJSONRoot.get(i)));
		}

		// actions.json

		JsonValue actionJSONRoot = jsonReader.parse(Gdx.files.internal("data/actions.json"));
		for (int i = 0; i < actionJSONRoot.size; i++) {
			chefActions.put(actionJSONRoot.get(i).name, ChefAction.read(
				actionJSONRoot.get(i),
				itemsTypes,
				stationTypes
			));
		}

		// combination.json
		JsonValue combinationsJSONRoot = jsonReader.parse(
			Gdx.files.internal("data/combinations.json"));

		// combinations
		JsonValue combinationsJSON = combinationsJSONRoot.get("combinations");
		for (int i = 0; i < combinationsJSON.size; i++) {
			combinations.put(combinationsJSON.get(i).name, Combination.read(
				combinationsJSON.get(i),
				itemsTypes,
				stationTypes
			));
		}

		// combination groups
		JsonValue combinationGroupsJSON = combinationsJSONRoot.get("combination-groups");
		for (JsonValue combinationGroupData: combinationGroupsJSON) {
			ArrayList<Combination> combinationGroup = new ArrayList<>();
			int i = 0;
			int j = 0;
			while (i < combinationGroupData.size) {
				// System.out.println(combinationGroupData.get(i).asString());
				combinationGroup.add(combinations.get(combinationGroupData.get(i).asString())[j]);
				j++;
				if (j >= combinations.get(combinationGroupData.get(i).asString()).length) {
					j = 0;
					i++;
				}
			}
			combinations.put(combinationGroupData.name, combinationGroup.toArray(Combination[]::new));
		}
		// levels.json

		JsonValue levelsJSONRoot = jsonReader.parse(Gdx.files.internal("data/levels.json"));
		for (int i = 0; i < levelsJSONRoot.size; i++) {
			levelTypes.put(levelsJSONRoot.get(i).name,
				LevelType.read(levelsJSONRoot.get(i), orderTypes, stationTypes, chefActions,
					combinations));
		}
	}
}

