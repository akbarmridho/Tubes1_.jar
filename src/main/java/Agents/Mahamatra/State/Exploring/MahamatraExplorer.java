package Agents.Mahamatra.State.Exploring;

import Agents.Mahamatra.Actions.SearchFood;
import Agents.Mahamatra.State.State;
import Enums.PlayerActions;
import Models.PlayerAction;
import Services.GameWatcherManager;
import Utils.Math;

import static Agents.Mahamatra.Mahamatra.*;

public class MahamatraExplorer implements State {
    private boolean manualGiveUp = false;

    @Override
    public PlayerAction computeNextAction() {
        var watcher = GameWatcherManager.getWatcher();
        PlayerAction action = new PlayerAction();
        action.setPlayerId(watcher.player.id);
        var food = SearchFood.closestFood();

        if (food == null) {
            action.setAction(PlayerActions.STOP);
            manualGiveUp = true;
            return action;
        }

        action.setAction(PlayerActions.FORWARD);
        action.setHeading(Math.getHeadingBetween(watcher.player, food));

        return action;
    }

    @Override
    public boolean giveUpControl() {
        if (manualGiveUp) {
            manualGiveUp = false;
            return true;
        }

        return GameWatcherManager.getWatcher().player.size > SHIP_SIZE_IDEAL;
    }

    @Override
    public int measureTakeoverPriority() {
        if (GameWatcherManager.getWatcher().player.size <= SHIP_SIZE_CRITICAL) {
            return PRIORITY_HIGH;
        } else if (GameWatcherManager.getWatcher().player.size <= SHIP_SIZE_CRITICAL + (SHIP_SIZE_IDEAL - SHIP_SIZE_CRITICAL) / 2) {
            return PRIORITY_NORMAL;
        }

        return PRIORITY_NONE;
    }

    @Override
    public int measureEmergencyTakeoverPriority() {
        return PRIORITY_NONE;
    }

    @Override
    public void receiveControl() {
        System.out.println("Mahamatra explorer was given control");
    }
}
