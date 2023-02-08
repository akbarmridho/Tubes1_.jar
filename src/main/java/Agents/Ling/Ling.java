package Agents.Ling;

import Agents.Agent;
import Models.PlayerAction;
import Services.GameWatcher;
import Services.GameWatcherManager;

public class Ling implements Agent {
    private GameWatcher watcher;

    public Ling() {
        this.watcher = GameWatcherManager.getWatcher();
    }

    @Override
    public PlayerAction computeNextAction() {
        return null;
    }

    private Agent selectStrategy() {
        return null;
    }
}
