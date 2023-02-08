package Agents.Ling;

import Agents.Agent;
import Services.GameWatcher;
import Services.GameWatcherManager;

public interface StrategyInterface extends Agent {
    GameWatcher watcher = GameWatcherManager.getWatcher();

    public int getPriorityLevel();
}
