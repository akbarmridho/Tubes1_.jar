package Agents.Ling.Strategy;

import Agents.Ling.StrategyInterface;
import Models.PlayerAction;

public class Attacker implements StrategyInterface {
    @Override
    public PlayerAction computeNextAction() {
        return null;
    }

    @Override
    public int getPriorityLevel() {
        return 0;
    }
}
