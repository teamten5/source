package io.github.teamten5;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Level {

    public final LevelType type;
    public LinkedList<Order> orders;
    private ArrayList<Chef> chefs;
    private Station[] stations;
    private ArrayDeque<Integer> unactiveChefs;
    private int reputation = 3;

    private float nextOrderIn = 3f;

    private float ordersRemaining = 5f;

    private final Random random;

    Texture full_reputation;
    Texture empty_reputation;


    public Level(LevelType type) {
        this.type = type;

        orders = new LinkedList<>();
        unactiveChefs = new ArrayDeque<>(List.of(1));
        chefs = new ArrayList<>(Arrays.asList(
              new Chef(0, 0, this, new PlayerController()),
              new Chef(0, 1, this, new NullController())
        ));
        stations = Arrays.stream(type.stations).map(x -> x.instantiate(this)).toArray(Station[]::new);

        random = new Random();
        full_reputation = new Texture("images/" + "full_heart.png");
        empty_reputation = new Texture("images/" + "empty_heart.png");


    }

    public void update(float delta) {
        System.out.println(reputation);
        for (Chef chef : chefs) {
            chef.update(delta);
        }
        for (Station station : stations) {
            station.update(delta);
        }
        nextOrderIn -= delta;
        if (nextOrderIn <= 0 && ordersRemaining > 0) {
            System.out.println("new order");
            ordersRemaining -= 1;
            orders.add(type.orderTypes[random.nextInt(type.orderTypes.length)].placeOrder());
            nextOrderIn = 10 + random.nextInt(20);
        }
        for (Order order: orders) {
            if (!order.update(delta)) {
                reputation -= 1;
                orders.remove(order);
                if (reputation <= 0) {
                    System.out.println("YOU lost :p");
                }
            }
        }

        if (ordersRemaining == 0 && orders.size() == 0) {
            //todo you win!
        }

    }

    public void render(Batch batch) {

        // TextureRegion test = new TextureRegion(type.backgroundTexture);
        // test.setRegion(0,0, type.backgroundTexture.getWidth()*50, type.backgroundTexture.getHeight()*50);
        // batch.draw(test, -1000, -1000, 2000, 2000);
        for (Rectangle rect: type.chefValidAreas) {
            TextureRegion test = new TextureRegion(type.floorTexture);
            test.setRegion(0,0, type.backgroundTexture.getWidth()*rect.width/2, type.backgroundTexture.getHeight()*rect.height/2);
            batch.draw(test, rect.x, rect.y, rect.width, rect.height);

        }
        for (Station station : stations) {
            station.render(batch);
        }
        for (Chef chef : chefs) {
            chef.render(batch);
        }

        for (int i = 0; i < orders.size(); i++) {
            orders.get(i).render(batch, i);
        }


    }

    public void renderUI(Batch batch) {
        for (int i = 0; i < type.startingReputation; i++) {
            if (reputation >= i + 1) {
                batch.draw(full_reputation, 0 , i*32);
            } else {
                batch.draw(empty_reputation, 0, i*32);
            }
        }
    }

    public Station closestStation(float x, float y) {
        for (Station station : stations) {
            if ((station.stationLevel.x <= x && x <= station.stationLevel.x + station.stationLevel.type.sizeX) && station.stationLevel.y <= y
                  && y <= station.stationLevel.y + station.stationLevel.type.sizeY) {
                return station;
            }
        }
        return null;
    }

    public void swapChefs(Chef oldChef, Controller controller) {
        chefs.get(unactiveChefs.pop()).setController(controller);
        oldChef.setController(new NullController(controller.x, controller.y,  controller.facing_y, controller.facing_x, controller.doAction));
        unactiveChefs.add(chefs.indexOf(oldChef));

    }

    public void completeOrder(ItemType holding) {
        for (Order order: orders) {
            if (order.type.itemOrdered == holding) {
                orders.remove(order);
                // TODO add score here
                break;
            }
        }
    }
}
