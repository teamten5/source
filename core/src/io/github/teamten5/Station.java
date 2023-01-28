package io.github.teamten5;

import com.badlogic.gdx.graphics.g2d.Batch;
import java.util.HashMap;

public class Station {

    final StationLevel stationLevel;
    ItemType holding = null;
    private boolean chefDoingAction = false;

    private float actionProgress = 0;
    private ChefAction currentAction = null;
    final Level level;

    public Station(StationLevel stationLevel, Level level) {
        this.stationLevel = stationLevel;
        this.level = level;

    }

    void render(Batch batch) {
        batch.draw(stationLevel.type.image, stationLevel.x * 32, stationLevel.y * 32);
        if (holding != null) {
            holding.render(batch, stationLevel.x * 32, stationLevel.y * 32);
        }

    }

    void update(float delta) {
        if (currentAction != null) {

            if (currentAction.chefRequired) {
                if (chefDoingAction) {
                    actionProgress += delta * 1000;
                }
            } else {
                actionProgress += delta * 1000;
            }

            if (actionProgress >= currentAction.timeToComplete) {
                holding = currentAction.output;
                actionProgress = 0;
            }
        }
        chefDoingAction = false;
    }

    ItemType getHolding() {
        return holding;
    }

    ChefAction getAction() { // TODO bad function name
        if (level.type.chefActions.containsKey(this.stationLevel.type)) {
            HashMap<ItemType, ChefAction> temp = level.type.chefActions.get(this.stationLevel.type);
            if (temp.containsKey(holding)) {
                return temp.get(holding);
            }
        }
        return null;
    }

    void setHolding(ItemType holding) {
        this.holding = holding;
        if (currentAction == null) {
            actionProgress = 0;
        }
        currentAction = getAction();
        if (currentAction != null) {
            if (currentAction.resetTime) {
                actionProgress = 0;
            }
        }
    }

    void doAction() {
        chefDoingAction = true;
    }
}
