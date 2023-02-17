package Agents.Ling.Strategy;

import Actions.Armory;
import Actions.SearchEnemy;
import Actions.SearchFood;
import Agents.Ling.Priority;
import Agents.Ling.StrategyInterface;
import Enums.PlayerActions;
import Models.GameObject;
import Models.PlayerAction;
import Services.GameWatcher;
import Services.GameWatcherManager;
import Utils.Math;

public class Explorer implements StrategyInterface {
    public static final int SHIP_SIZE_CRITICAL = 30;
    public static final int SHIP_SIZE_IDEAL = 100;
    private final GameWatcher watcher = GameWatcherManager.getWatcher();

    @Override
    public PlayerAction computeNextAction() {
        PlayerAction act = new PlayerAction();

        var food = SearchFood.closestSafestFood();
        if (food == null) {
            food = SearchFood.closestFood();
            if (food == null) {
                act.setHeading(Math.getCenterHeading(this.watcher.player));
                act.setAction(PlayerActions.FORWARD);
                return act;
            }
        }

        act.setHeading(Math.getHeadingBetween(watcher.player, food));
        act.setAction(PlayerActions.FORWARD);

        var currentHeading = this.watcher.radar.heading;

        if (currentHeading != null) {
            var headingDiff = Math.getModulus(watcher.player.currentHeading - act.getHeading(), 360);
            // unstuck player
            if (headingDiff > 150 && headingDiff < 210) {
                act.setHeading(watcher.player.currentHeading);
            } else {
                if (this.watcher.player.torpedoSalvoCount >= 5
                        && this.watcher.player.getSize() >= SHIP_SIZE_CRITICAL * 2) {
                    // Kalo ada gas cloud, tembak gas cloud
                    GameObject gas = this.watcher.radar.closestGasCloud();
                    if (gas != null) {
                        System.out.println("Firing gas clouds");
                        return Armory.fireTorpedo(gas);
                    } else {
                        var closestEnemy = SearchEnemy.closestEnemy();
                        return Armory.fireTorpedo(closestEnemy);
//                        return Armory.interceptTargetFood(closestEnemy, this.watcher.player);
                    }
                }
            }

        }

        return act;
    }

    @Override
    public int getPriorityLevel() {
        if (this.watcher.foods.size() == 0) {
            return Priority.NONE;
        }

        var averageEnemySize = this.watcher.enemies.stream().mapToDouble(enemy -> enemy.size).average();
        double idealShipSize;

        if (averageEnemySize.isEmpty()) {
            idealShipSize = SHIP_SIZE_IDEAL;
        } else {
            idealShipSize = java.lang.Math.max(averageEnemySize.getAsDouble(), SHIP_SIZE_IDEAL);
        }

        if (watcher.player.size <= SHIP_SIZE_CRITICAL) {
            return Priority.HIGH;
        } else if (watcher.player.size <= idealShipSize) {
            return Priority.NORMAL;
        } else {
            return Priority.LOW;
        }
    }

}
