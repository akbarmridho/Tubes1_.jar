package Agents.Ling;

import Agents.Agent;
import Agents.Ling.Strategy.Attacker;
import Agents.Ling.Strategy.Explorer;
import Models.PlayerAction;
import Services.GameWatcher;
import Services.GameWatcherManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Ling implements Agent {
    private GameWatcher watcher;
    private List<StrategyInterface> strategies;

    public Ling() {
        this.watcher = GameWatcherManager.getWatcher();
        this.strategies = new ArrayList<>();
        this.strategies.add(new Attacker());
        this.strategies.add(new Explorer());
    }

    @Override
    public PlayerAction computeNextAction() {
        return this.selectStrategy().computeNextAction();
    }

    private Agent selectStrategy() {
        return this.strategies.stream().sorted(Comparator.comparing(StrategyInterface::getPriorityLevel).reversed()).collect(Collectors.toList()).get(0);
    }
}
