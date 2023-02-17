package Agents.Ling.Strategy;

import Actions.Armory;
import Actions.SearchEnemy;
import Agents.Ling.Priority;
import Agents.Ling.StrategyInterface;
import Models.GameObject;
import Models.PlayerAction;
import Services.GameWatcher;
import Services.GameWatcherManager;

public class Sniper implements StrategyInterface {
    private final GameWatcher watcher = GameWatcherManager.getWatcher();

    private GameObject target = null;

    @Override
    public PlayerAction computeNextAction() {

        if (this.target == null) {
            return null;
        }

        return Armory.fireTorpedo(this.target);
    }

    @Override
    public int getPriorityLevel() {
        var largestEnemy = SearchEnemy.largestEnemy();

        if (largestEnemy == null) {
            this.target = null;
            return Priority.NONE;
        }

        if (watcher.player.torpedoSalvoCount > 0 && watcher.player.size > 30 && (largestEnemy.size - watcher.player.size) > 30) {
            this.target = largestEnemy;
            return Priority.HIGH;
        }

        this.target = null;
        return Priority.NONE;
    }
}
