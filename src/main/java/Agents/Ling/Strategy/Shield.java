package Agents.Ling.Strategy;

import Actions.SearchTorpedoes;
import Agents.Ling.Priority;
import Agents.Ling.StrategyInterface;
import Enums.PlayerActions;
import Models.PlayerAction;
import Services.GameWatcher;
import Services.GameWatcherManager;

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
        if (this.watcher.player.shieldCount > 0 &&
                SearchTorpedoes.filterDangerousTorpedoes(watcher.player, watcher.torpedoes).size() >= 2 &&
                this.watcher.player.getSize() >= 80) {
            return Priority.EMERGENCY;
        }

        return Priority.NONE;
    }
}
