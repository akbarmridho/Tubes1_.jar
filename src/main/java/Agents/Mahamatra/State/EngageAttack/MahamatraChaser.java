package Agents.Mahamatra.State.EngageAttack;

import Actions.Armory;
import Actions.SearchEnemy;
import Agents.Mahamatra.State.State;
import Agents.Mahamatra.Utilities.AfterburnerManager;
import Enums.PlayerActions;
import Models.PlayerAction;
import Services.GameWatcherManager;
import Utils.Math;

import java.util.UUID;

import static Agents.Mahamatra.Mahamatra.*;

public class MahamatraChaser implements State {
    private final int ENGAGE_ENEMY_RADIUS = 500;

    private final int HEADING_ADJUSTMENT_MAX_TURN = 8;
    private UUID target = null;
    private UUID potentialTarget = null;
    private int tickSinceHeadingAdjustment = 1000;

    private AfterburnerManager afterburner;

    public MahamatraChaser() {
        this.afterburner = new AfterburnerManager(10);
    }

    @Override
    public PlayerAction computeNextAction() {
        var watcher = GameWatcherManager.getWatcher();

        var afterburner = this.afterburner.computeNextAction();

        if (afterburner != null) {
            return afterburner;
        }

        var anotherTarget = this.getPotentialTarget();

        if (anotherTarget != null && this.target != anotherTarget) {
            this.target = anotherTarget;
        }

        if (tickSinceHeadingAdjustment >= HEADING_ADJUSTMENT_MAX_TURN) {
            return adjustHeading();
        } else if (watcher.player.torpedoSalvoCount > 0) {
            return fireTorpedo();
        }

        return adjustHeading();
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

    private PlayerAction fireTorpedo() {
        var watcher = GameWatcherManager.getWatcher();
        var target = watcher.getEnemyById(this.target);
        PlayerAction action = new PlayerAction();
        action.setPlayerId(watcher.player.id);
        action.setAction(PlayerActions.FIRETORPEDOES);
        action.setHeading(Armory.calculateTorpedoHeading(watcher.player, target));

        return action;
    }

    private UUID getPotentialTarget() {
        var enemy = SearchEnemy.smallerEnemyWithin(ENGAGE_ENEMY_RADIUS);

        if (enemy == null) {
            return null;
        }

        return enemy.getId();
    }

    @Override
    public boolean isFinished() {
        var watcher = GameWatcherManager.getWatcher();

        if (this.target == null) {
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
    public int measureTakeoverPriority() {
        var watcher = GameWatcherManager.getWatcher();
        var potentialEnemy = this.getPotentialTarget();

        if (potentialEnemy == null) {
            return PRIORITY_NONE;
        }

        this.potentialTarget = potentialEnemy;

        return PRIORITY_NORMAL;
    }

    @Override
    public int measureInterruptionPriority() {
        return PRIORITY_NONE;
    }

    @Override
    public void receiveControl() {
        this.tickSinceHeadingAdjustment = 1000;
        this.target = this.potentialTarget;
        System.out.format("Mahamatra chaser was given control. Attacking %s\n", this.target.toString());
    }
}
