package Agents.Ling.Strategy;

import Actions.SearchTorpedoes;
import Agents.Ling.Priority;
import Agents.Ling.StrategyInterface;
import Enums.PlayerActions;
import Models.PlayerAction;
import Services.GameWatcher;
import Services.GameWatcherManager;

public class Defender implements StrategyInterface {
    private final GameWatcher watcher = GameWatcherManager.getWatcher();

    @Override
    public PlayerAction computeNextAction() {
        PlayerAction act = new PlayerAction();
        act.setAction(PlayerActions.FORWARD);

        if (!SearchTorpedoes.filterDangerousTorpedoes(watcher.player, watcher.torpedoes).isEmpty()) {
            act.setHeading(SearchTorpedoes.safestHeading());
        }

        return act;
    }

    @Override
    public int getPriorityLevel() {
        // Detect if torpeoes are close using radar, check if heading is towards us and
        // multiple are in the same section
        // Detect for enemies and go the opposite, safe direction(no gas cloud) from
        // closest enemy

        if (!SearchTorpedoes.filterDangerousTorpedoes(watcher.player, watcher.torpedoes).isEmpty()) {
            return Priority.HIGH;
        }
        return Priority.NONE;
    }
}
