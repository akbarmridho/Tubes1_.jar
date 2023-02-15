package Agents.Ling.Strategy;

import Agents.Ling.Priority;
import Agents.Ling.StrategyInterface;
import Enums.PlayerActions;
import Models.PlayerAction;
import Services.GameWatcher;
import Services.GameWatcherManager;
import Utils.Math;

public class ToCenter implements StrategyInterface {
    private final GameWatcher watcher = GameWatcherManager.getWatcher();

    @Override
    public PlayerAction computeNextAction() {
        PlayerAction act = new PlayerAction();
        act.setHeading(Math.getCenterHeading(this.watcher.player));
        act.setAction(PlayerActions.FORWARD);
        return act;
    }

    @Override
    public int getPriorityLevel() {
        if (Math.isOutOfBound(this.watcher.player, this.watcher.world.radius)) {
            return Priority.EMERGENCY;
        }
        return Priority.NONE;
    }
}
