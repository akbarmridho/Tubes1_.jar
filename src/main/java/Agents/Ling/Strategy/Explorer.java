package Agents.Ling.Strategy;

import Actions.SearchFood;
import Agents.Ling.Priority;
import Agents.Ling.StrategyInterface;
import Enums.PlayerActions;
import Models.PlayerAction;
import Services.GameWatcher;
import Services.GameWatcherManager;
import Utils.Math;

public class Explorer implements StrategyInterface {
    public static final int SHIP_SIZE_CRITICAL = 20;
    public static final int SHIP_SIZE_IDEAL = 50;
    private GameWatcher watcher= GameWatcherManager.getWatcher();

    @Override
    public PlayerAction computeNextAction() {
        PlayerAction act = new PlayerAction();

        var Food = SearchFood.closestSafestFood();
        if (Food == null){
            Food = SearchFood.closestFood();
            if (Food == null){
                // act.setAction(PlayerActions.STOP);
                return null;
            }
        }
        act.setHeading(Math.getHeadingBetween(watcher.player, Food));
        act.setAction(PlayerActions.FORWARD);

        return act;
    }

    @Override
    public int getPriorityLevel() {
        if (watcher.player.size <= SHIP_SIZE_CRITICAL){
            return Priority.HIGH;
        } else if (watcher.player.size <= SHIP_SIZE_IDEAL){
            return Priority.NORMAL;
        } else {
            return Priority.LOW;
        }
    }
    
}
