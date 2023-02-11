package Agents.Ling.Strategy;

import Actions.Armory;
import Actions.SearchEnemy;
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
    private final GameWatcher watcher = GameWatcherManager.getWatcher();

    @Override
    public PlayerAction computeNextAction() {
        PlayerAction act = new PlayerAction();

        var food = SearchFood.closestSafestFood();
        if (food == null) {
            food = SearchFood.closestFood();
            if (food == null) {
                // act.setAction(PlayerActions.STOP);
                return null;
            }
        }

        act.setHeading(Math.getHeadingBetween(watcher.player, food));
        act.setAction(PlayerActions.FORWARD);

        var currentHeading = this.watcher.radar.heading;

        if (currentHeading != null) {
            // unstuck player
            if (java.lang.Math.abs(watcher.player.currentHeading - act.getHeading()) > 150) {
                act.setHeading(watcher.player.currentHeading);
            } else {
                // todo: tembak bot atau gas cloud terdekat
                if (this.watcher.player.torpedoSalvoCount >= 5 && this.watcher.player.getSize() >= SHIP_SIZE_CRITICAL * 2) {
                    var closestEnemy = SearchEnemy.closestEnemy();
                    System.out.println("Explorer firing torpedo");
                    return Armory.fireTorpedo(closestEnemy);
                }
            }
        }

        return act;
    }

    @Override
    public int getPriorityLevel() {
        if (watcher.player.size <= SHIP_SIZE_CRITICAL) {
            return Priority.HIGH;
        } else if (watcher.player.size <= SHIP_SIZE_IDEAL) {
            return Priority.NORMAL;
        } else {
            return Priority.LOW;
        }
    }

}
