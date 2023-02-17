package Agents.Ling.Strategy;

import Actions.SearchTorpedoes;

import java.util.Comparator;
import java.util.List;

import Actions.SearchGasCloud;
import Actions.SearchSupernova;
import Actions.SearchTeleporter;
import Agents.Ling.Priority;
import Agents.Ling.StrategyInterface;
import Enums.PlayerActions;
import Models.GameObject;
import Models.PlayerAction;
import Services.GameWatcher;
import Services.GameWatcherManager;
import Utils.Math;

public class Defender implements StrategyInterface {
    private final GameWatcher watcher = GameWatcherManager.getWatcher();
    private List<GameObject> dangerousTorpedoes = null;
    private List<GameObject> dangerousTeleporters = null;
    private GameObject supernovaBomb = null;
    private GameObject dangerousGasCloud = null;

    @Override
    public PlayerAction computeNextAction() {
        PlayerAction act = new PlayerAction();
        act.setAction(PlayerActions.FORWARD);

        if (supernovaBomb != null) {
            act.setHeading(SearchSupernova.safestHeading());
        } else if (dangerousGasCloud != null) {
            act.setHeading(SearchGasCloud.safestHeading());
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
        this.dangerousGasCloud = SearchGasCloud.getImmediatelyDangerousGasCloud(watcher.player, watcher.gasClouds);

        if (supernovaBomb != null || dangerousGasCloud != null) {
            return Priority.EMERGENCY;
        }
        if (!dangerousTorpedoes.isEmpty() || !dangerousTeleporters.isEmpty()) {
            return Priority.HIGH;
        }
        return Priority.NONE;
    }
}
