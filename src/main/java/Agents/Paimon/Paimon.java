package Agents.Paimon;

import Agents.Agent;
import Enums.PlayerActions;
import Models.GameObject;
import Models.PlayerAction;
import Services.GameWatcher;
import Services.GameWatcherManager;
import Utils.Math;

import java.util.Comparator;
import java.util.stream.Collectors;

public class Paimon implements Agent {
    public final String name = "Paimon";

    /*
     * Paimon just being paimon that loves food
     */
    @Override
    public PlayerAction computeNextAction() {
        GameWatcher watcher = GameWatcherManager.getWatcher();
        GameObject player = watcher.player;

        var sortedFood = watcher.foods.
                stream().
                sorted(Comparator.comparing(food -> Math.getDistanceBetween(player, food))).
                collect(Collectors.toList());

        PlayerAction action = new PlayerAction();
        action.setPlayerId(player.id);
        action.setAction(PlayerActions.FORWARD);
        action.setHeading(Math.getHeadingBetween(player, sortedFood.get(0)));

        System.out.println("Done calculating action\n");

        return action;
    }
}
