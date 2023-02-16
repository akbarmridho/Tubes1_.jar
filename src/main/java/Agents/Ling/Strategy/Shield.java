package Agents.Ling.Strategy;

import Actions.SearchEnemy;
import Actions.SearchTorpedoes;
import Agents.Ling.Priority;
import Agents.Ling.StrategyInterface;
import Enums.PlayerActions;
import Models.PlayerAction;
import Services.GameWatcher;
import Services.GameWatcherManager;
import Utils.Math;

public class Shield implements StrategyInterface {
    private final GameWatcher watcher = GameWatcherManager.getWatcher();

    @Override
    public PlayerAction computeNextAction() {
        PlayerAction act = new PlayerAction();
        act.setAction(PlayerActions.ACTIVATESHIELD);

        return act;
    }

    @Override
    public int getPriorityLevel() {
        var closestEnemy = SearchEnemy.closestEnemy();
        boolean dangerousEnemy = false;

        if (closestEnemy != null) {
            dangerousEnemy = Math.getTrueDistanceBetween(this.watcher.player, closestEnemy) < 60;
        }


        if (this.watcher.player.shieldCount > 0 &&
                SearchTorpedoes.filterDangerousTorpedoes(watcher.player, watcher.torpedoes).size() >= 2 &&
                (this.watcher.player.getSize() >= 80 || (dangerousEnemy && watcher.player.size > 35))) {
            return Priority.EMERGENCY;
        }

        return Priority.NONE;
    }
}
