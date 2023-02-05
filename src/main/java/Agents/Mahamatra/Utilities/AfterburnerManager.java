package Agents.Mahamatra.Utilities;

import Agents.Agent;
import Enums.PlayerActions;
import Models.PlayerAction;
import Services.GameWatcherManager;

public class AfterburnerManager implements Agent {
    public static int SHIP_SIZE_AFTERBURNER_CUTOFF = 60;
    public static int SHIP_SIZE_AFTERBURNER_IDEAL = 75;

    public int activeDuration;
    public int maxDuration;

    public AfterburnerManager() {
        this.activeDuration = 0;
        this.maxDuration = 1 << 30;
    }

    public AfterburnerManager(int maxDuration) {
        this.maxDuration = maxDuration;
        this.activeDuration = 0;
    }


    @Override
    public PlayerAction computeNextAction() {
        var watcher = GameWatcherManager.getWatcher();
        PlayerAction action = new PlayerAction();

        if (watcher.player.isAfterburnerActive()) {
            if (watcher.player.size <= SHIP_SIZE_AFTERBURNER_CUTOFF || this.activeDuration >= this.maxDuration) {
                action.setAction(PlayerActions.STOPAFTERBURNER);
                this.activeDuration = 0;
                return action;
            }

            this.activeDuration++;
            return null;
        }

        if (watcher.player.size > SHIP_SIZE_AFTERBURNER_IDEAL) {
            action.setAction(PlayerActions.STARTAFTERBURNER);
            return action;
        }

        return null;
    }
}
