package Agents.Ling.Strategy;

import Actions.SearchTorpedoes;

import java.util.Comparator;
import java.util.List;

import Actions.SearchSupernova;
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

    @Override
    public PlayerAction computeNextAction() {
        PlayerAction act = new PlayerAction();
        act.setAction(PlayerActions.FORWARD);

        List<GameObject> dangerousTorpedoes = SearchTorpedoes.filterDangerousTorpedoes(watcher.player,
                watcher.torpedoes);
        GameObject closestTorpedo = dangerousTorpedoes.stream()
                .min(Comparator.comparing(torpedo -> Math.getDistanceBetween(torpedo, watcher.player))).orElse(null);
        GameObject supernovaBomb = SearchSupernova.getDangerousSupernova(watcher.player, watcher.others);

        if (dangerousTorpedoes.isEmpty()) {
            act.setHeading(SearchSupernova.safestHeading());
        } else if (supernovaBomb == null) {
            act.setHeading(SearchTorpedoes.safestHeading());
        } else if (Math.getDistanceBetween(watcher.player, supernovaBomb) > watcher.supernovaBlastRadius) {
            act.setHeading(
                    Math.getDistanceBetween(watcher.player, supernovaBomb) < Math.getDistanceBetween(watcher.player,
                            closestTorpedo) ? SearchSupernova.safestHeading() : SearchTorpedoes.safestHeading());
        } else {
            act.setHeading(SearchSupernova.safestHeading());
        }

        return act;
    }

    @Override
    public int getPriorityLevel() {
        if (!SearchTorpedoes.filterDangerousTorpedoes(watcher.player, watcher.torpedoes).isEmpty()
                || SearchSupernova.getDangerousSupernova(watcher.player, watcher.others) != null) {
            return Priority.HIGH;
        }
        return Priority.NONE;
    }
}
