package Agents.Ling.Strategy;

import Actions.SearchEnemy;
import Agents.Ling.Priority;
import Agents.Ling.StrategyInterface;
import Models.PlayerAction;
import Services.GameWatcher;
import Services.GameWatcherManager;

public class Supernova implements StrategyInterface {
    private final GameWatcher watcher = GameWatcherManager.getWatcher();

    @Override
    public PlayerAction computeNextAction() {
        return null;
    }

    @Override
    public int getPriorityLevel() {
        var potentialTarget = SearchEnemy.enemyAboveRange(500);
        if (this.watcher.player.supernovaAvailable == 1 && potentialTarget != null && potentialTarget.size() != 0) {
            return Priority.EMERGENCY;
        }
//        } else if (false /* cek jika proyektil yang ditembakkan sudah dalam radius tembakan */) {
//
//        }

        return Priority.NONE;
    }
}
