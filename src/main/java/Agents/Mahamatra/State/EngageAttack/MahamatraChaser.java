package Agents.Mahamatra.State.EngageAttack;

import Agents.Mahamatra.Actions.SearchEnemy;
import Agents.Mahamatra.State.State;
import Enums.PlayerActions;
import Models.PlayerAction;
import Services.GameWatcherManager;
import Utils.Math;

import java.util.UUID;

import static Agents.Mahamatra.Mahamatra.*;

public class MahamatraChaser implements State {
    private final int ENGAGE_ENEMY_RADIUS = 500;
    private UUID target = null;
    private UUID potentialTarget = null;
    private int tickSinceHeadingAdjustment = 1000;

    @Override
    public PlayerAction computeNextAction() {
        var watcher = GameWatcherManager.getWatcher();

        return adjustHeading();

//        if (tickSinceHeadingAdjustment > 10) {
//            return this.adjustHeading();
//        }
//
//        return null;
    }

    private PlayerAction adjustHeading() {
        var watcher = GameWatcherManager.getWatcher();
        var target = watcher.getEnemyById(this.target);
        PlayerAction action = new PlayerAction();
        action.setPlayerId(watcher.player.id);
        action.setAction(PlayerActions.FORWARD);
        action.setHeading(Math.getHeadingBetween(watcher.player, target));
        this.tickSinceHeadingAdjustment = 0;

        return action;
    }

    @Override
    public boolean giveUpControl() {
        var watcher = GameWatcherManager.getWatcher();

        if (target == null) {
            return true;
        }

        var targetObject = watcher.getEnemyById(this.target);

        if (targetObject == null) {
            this.target = null; // target eliminated
            return true;
        } else if (targetObject.size > watcher.player.size) {
            return true;
        }

        return watcher.player.size < SHIP_SIZE_CRITICAL;
    }

    @Override
    public int takeControl() {
        var watcher = GameWatcherManager.getWatcher();
        var potentialEnemy = SearchEnemy.smallerEnemyWithin(ENGAGE_ENEMY_RADIUS);

        if (potentialEnemy == null) {
            return PRIORITY_NONE;
        }

        this.potentialTarget = potentialEnemy.getId();
        return PRIORITY_NORMAL;
    }

    @Override
    public void giveControl() {
        this.tickSinceHeadingAdjustment = 1000;
        this.target = this.potentialTarget;
        System.out.println("Mahamatra chaser was given control");
    }
}
