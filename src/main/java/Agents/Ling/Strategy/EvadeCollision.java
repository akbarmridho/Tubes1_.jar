package Agents.Ling.Strategy;

import Actions.SearchEnemy;
import Agents.Ling.Priority;
import Agents.Ling.StrategyInterface;
import Enums.PlayerActions;
import Models.GameObject;
import Models.PlayerAction;
import Services.GameWatcher;
import Services.GameWatcherManager;
import Utils.Math;

public class EvadeCollision implements StrategyInterface {
    private final GameWatcher watcher = GameWatcherManager.getWatcher();
    private GameObject target = null;

    @Override
    public PlayerAction computeNextAction() {
        if (this.target == null) {
            return null;
        }

        PlayerAction action = new PlayerAction();
        action.setAction(PlayerActions.FORWARD);

        var targetHeading = this.target.currentHeading;
        var headingBetween = Math.getHeadingBetween(this.target, this.watcher.player);
        var headingDiff = targetHeading - headingBetween;

        if (headingDiff >= -180 && headingDiff <= 180) {
            // starboard side
            action.setHeading(Math.getModulus(watcher.player.currentHeading - 90, 360));
        } else {
            // port side
            action.setHeading(Math.getModulus(watcher.player.currentHeading + 90, 360));
        }

        return action;
    }

    @Override
    public int getPriorityLevel() {
        var target = SearchEnemy.closestEnemy();

        if (target == null) {
            this.target = null;
            return Priority.NONE;
        }

        if (Math.getTrueDistanceBetween(target, this.watcher.player) > 200) {
            return Priority.NONE;
        }

        double tick = Math.tickTillCollision(target, this.watcher.player);

        if (tick <= 2.5 && target.size > watcher.player.size) {
            this.target = target;
            return Priority.EMERGENCY;
        }

        return Priority.NONE;
    }
}
