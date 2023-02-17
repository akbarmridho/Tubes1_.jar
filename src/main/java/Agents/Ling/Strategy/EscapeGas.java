package Agents.Ling.Strategy;

import Agents.Ling.Priority;
import Agents.Ling.StrategyInterface;
import Enums.PlayerActions;
import Models.PlayerAction;
import Services.GameWatcher;
import Services.GameWatcherManager;
import Utils.Math;

import java.util.Comparator;
import java.util.stream.Collectors;

public class EscapeGas implements StrategyInterface {
    private final GameWatcher watcher = GameWatcherManager.getWatcher();

    @Override
    public PlayerAction computeNextAction() {
        var gases = this.watcher.gasClouds.stream().sorted(
                Comparator.comparing(gas -> -1 * Math.getDistanceBetween(this.watcher.player, gas))
        ).collect(Collectors.toList());

        if (gases.size() == 0) {
            return null;
        }

        PlayerAction action = new PlayerAction();
        action.setAction(PlayerActions.FORWARD);
        action.setHeading(Math.getHeadingBetween(gases.get(0), this.watcher.player));
        return action;
    }

    @Override
    public int getPriorityLevel() {
        if (this.watcher.player.isInsideGasCloud()) {
            return Priority.EMERGENCY;
        }

        return Priority.NONE;
    }
}
