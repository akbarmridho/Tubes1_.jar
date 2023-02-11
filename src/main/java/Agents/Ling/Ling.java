package Agents.Ling;

import Agents.Agent;
import Agents.Ling.Strategy.Attacker;
import Agents.Ling.Strategy.Explorer;
import Models.PlayerAction;

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
    }

    @Override
    public PlayerAction computeNextAction() {
        return this.selectStrategy().computeNextAction();
    }

    private Agent selectStrategy() {
        var selectedStrategy = this.strategies.stream().sorted(Comparator.comparing(StrategyInterface::getPriorityLevel).reversed()).collect(Collectors.toList()).get(0);
        var name = selectedStrategy.getClass().getSimpleName();

        if (this.currentStrategy == null) {
            this.currentStrategy = name;
            this.counter = 1;
        } else if (this.currentStrategy.equals(name)) {
            this.counter++;
        } else {
            System.out.format("Strategy %s was called %d times\n", this.currentStrategy, this.counter);
            this.currentStrategy = name;
            this.counter = 1;
        }

        return selectedStrategy;
    }
}
