package Agents.Ling.Strategy;

import Actions.SearchTorpedoes;

import java.util.List;

import Actions.SearchSupernova;
import Actions.SearchTeleporter;
import Agents.Ling.Priority;
import Agents.Ling.StrategyInterface;
import Enums.PlayerActions;
import Models.GameObject;
import Models.PlayerAction;
import Services.GameWatcher;
import Services.GameWatcherManager;

public class Defender implements StrategyInterface {
    private final GameWatcher watcher = GameWatcherManager.getWatcher();
    private List<GameObject> dangerousTorpedoes = null;
    private List<GameObject> dangerousTeleporters = null;
    private GameObject supernovaBomb = null;

    @Override
    public PlayerAction computeNextAction() {
        PlayerAction act = new PlayerAction();
        act.setAction(PlayerActions.FORWARD);

        if (supernovaBomb != null) {
            act.setHeading(SearchSupernova.safestHeading());
        } else if (!dangerousTeleporters.isEmpty()) {
            act.setHeading(SearchTeleporter.safestHeading());
        } else {
            act.setHeading(SearchTorpedoes.safestHeading());
        }

        return act;
    }

    @Override
    public int getPriorityLevel() {
        this.dangerousTorpedoes = SearchTorpedoes.filterDangerousTorpedoes(watcher.player, watcher.torpedoes);
        this.supernovaBomb = SearchSupernova.getDangerousSupernova(watcher.player, watcher.others);
        this.dangerousTeleporters = SearchTeleporter.filterDangerousTeleporters(watcher.player, watcher.others,
                watcher.enemies);

        if (supernovaBomb != null) {
            return Priority.EMERGENCY;
        }
        if (!dangerousTorpedoes.isEmpty() || !dangerousTeleporters.isEmpty()) {
            return Priority.HIGH;
        }
        return Priority.NONE;
    }
}
