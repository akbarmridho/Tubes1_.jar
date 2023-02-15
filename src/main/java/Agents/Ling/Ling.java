package Agents.Ling;

import Agents.Agent;
import Agents.Ling.Strategy.*;
import Enums.PlayerActions;
import Models.PlayerAction;
import Services.GameWatcher;
import Services.GameWatcherManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Ling implements Agent {
    private final List<StrategyInterface> strategies;

    // for debug purposes
    private String currentStrategy = null;
    private int counter = 0;

    public Ling() {
        this.strategies = new ArrayList<>();
        this.strategies.add(new Attacker());
        this.strategies.add(new Explorer());
        this.strategies.add(new Defender());
        this.strategies.add(new Shield());
        this.strategies.add(new ToCenter());
    }

    @Override
    public PlayerAction computeNextAction() {
        GameWatcher watcher = GameWatcherManager.getWatcher();

        try {
            var action = this.selectStrategy().computeNextAction();

            if (action.getAction() == PlayerActions.FORWARD) {
                var headingDiff = Math.abs(watcher.player.currentHeading - action.getHeading());
                if (headingDiff > 150 && headingDiff < 210) {
                    System.out.println("Sudden turn was detected");
                }
            }

            return action;
        } catch (Error e) {
            System.out.println("Exception detected");
        }

        return null;
    }

    private Agent selectStrategy() {
        var selectedStrategy = this.strategies.stream()
                .sorted(Comparator.comparing(StrategyInterface::getPriorityLevel).reversed())
                .collect(Collectors.toList()).get(0);
        var name = selectedStrategy.getClass().getSimpleName();

        if (this.currentStrategy == null) {
            System.out.format("Start using strategy %s\n", name);
            this.currentStrategy = name;
            this.counter = 1;
        } else if (this.currentStrategy.equals(name)) {
            this.counter++;
        } else {
            GameWatcher watcher = GameWatcherManager.getWatcher();
            System.out.format("Strategy %s was called %d times from tick %d - %d. Begin using %s\n", this.currentStrategy, this.counter,
                    watcher.world.currentTick - this.counter - 1, watcher.world.currentTick - 1, name);
            this.currentStrategy = name;
            this.counter = 1;
        }

        return selectedStrategy;
    }
}
