package Agents.Ling.Strategy;

import Actions.SearchFood;
import Agents.Ling.StrategyInterface;
import Models.PlayerAction;
import Services.GameWatcherManager;

public class Explorer implements StrategyInterface {
    @Override
    public PlayerAction computeNextAction() {
        var watcher = GameWatcherManager.getWatcher();
        PlayerAction act = new PlayerAction();
        act.setPlayerId(watcher.player.getId());

        var food = SearchFood.closestSafestFood();
        if (food == null){

        }

        return null;
    }

    @Override
    public int getPriorityLevel() {
        return 0;
    }
    
}
