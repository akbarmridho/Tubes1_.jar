package Agents.Ling.Strategy;

import Actions.Armory;
import Actions.SearchEnemy;
import Agents.Ling.Priority;
import Agents.Ling.StrategyInterface;
import Enums.PlayerActions;
import Models.GameObject;
import Models.PlayerAction;
import Services.GameWatcher;
import Services.GameWatcherManager;
import Services.RadarUnitArea;
import Utils.Math;

import java.util.List;

public class Attacker implements StrategyInterface {
    private final GameWatcher watcher = GameWatcherManager.getWatcher();
    private GameObject target = null;

    @Override
    public PlayerAction computeNextAction() {
        if (this.target == null) {
            return null;
        }

        // cek jika memerlukan heading adjustment
        var adjustment = headingAdjustment(false);

        int SHIP_SAFE_SIZE = 50;
        if (this.watcher.player.torpedoSalvoCount >= 5 &&
                (this.watcher.player.size > SHIP_SAFE_SIZE ||
                        this.watcher.enemies.size() == 1 && this.watcher.player.size >= 15)
        ) {
            return Armory.fireTorpedo(this.target);
        }

        if (adjustment != null) {
            return adjustment;
        }

        if (this.watcher.player.torpedoSalvoCount > 0) {
            if ((this.watcher.radar.clearToShoot(this.target) && Math.getTrueDistanceBetween(this.watcher.player, this.target) < 700) ||
                    (this.watcher.enemies.size() == 1 && this.watcher.player.size >= 15)) {
                return Armory.fireTorpedo(this.target);
            }
        }

        var newAdjustment = headingAdjustment(true);

        if (this.watcher.foods.size() <= 3 || newAdjustment == null) {
            return mirrorTargetHeading();
        }

        return newAdjustment;
    }

    private PlayerAction mirrorTargetHeading() {
        PlayerAction act = new PlayerAction();
        act.setAction(PlayerActions.FORWARD);
        act.setHeading(this.target.currentHeading);
        return act;
    }

    private PlayerAction headingAdjustment(boolean forceNew) {
        if (this.target == null) {
            return null;
        }

        var targetSection = watcher.radar.determineSection(this.target);
        GameObject currentHeading;

        if (forceNew) {
            currentHeading = null;
        } else {
            currentHeading = watcher.radar.heading;
        }
        var radarSectionCount = watcher.radar.sectionCount;

        // fokus mengejar tetapi tetap jaga jarak
        var angularVelocity = Math.calculateAngularVelocity(this.watcher.player, this.target);
        Integer[] safeSection;
        var headingDiff = java.lang.Math.abs(target.currentHeading - this.watcher.player.currentHeading);

        int ANGULAR_VELOCITY_OFFSET = 2;
        if (angularVelocity < ANGULAR_VELOCITY_OFFSET * (-1)) {
            safeSection = new Integer[]{
                    targetSection,
                    (targetSection - 1) % radarSectionCount,
                    (targetSection - 2) % radarSectionCount};
        } else if (angularVelocity > ANGULAR_VELOCITY_OFFSET) {
            safeSection = new Integer[]{
                    targetSection,
                    (targetSection + 1) % radarSectionCount,
                    (targetSection + 2) % radarSectionCount};
        } else {
            if (headingDiff > 150 && headingDiff < 210) {
                safeSection = new Integer[]{targetSection + radarSectionCount / 2,
                        (targetSection - 1) % radarSectionCount,
                        (targetSection + 1) % radarSectionCount,
                };
            } else if (headingDiff < 30) {
                safeSection = new Integer[]{targetSection,
                        (targetSection - 1) % radarSectionCount,
                        (targetSection + 1) % radarSectionCount,
                };
            } else {
                safeSection = new Integer[]{
                        (targetSection - 1) % radarSectionCount,
                        (targetSection + 1) % radarSectionCount,
                };
            }
        }


        if (currentHeading != null) {
            var currentHeadingSection = this.watcher.radar.determineSection(currentHeading);

            if (List.of(safeSection).contains(currentHeadingSection)) {
                return null;
            }
        }

        var potentialAreas = this.watcher.radar.getMostAdvantageousAreaIn(safeSection);
        return foodTargetFromAreaList(potentialAreas);
    }

    private PlayerAction foodTargetFromAreaList(List<RadarUnitArea> scannedArea) {
        GameObject foodTarget = null;
        int i = 0;

        while (foodTarget == null && i < scannedArea.size()) {
            foodTarget = scannedArea.get(i++).getClosestFood();
        }

        if (foodTarget == null) {
            return null;
        }

        PlayerAction action = new PlayerAction();
        action.setAction(PlayerActions.FORWARD);
        action.setHeading(Math.getHeadingBetween(this.watcher.player, foodTarget));

        return action;
    }

    @Override
    public int getPriorityLevel() {
        var target = SearchEnemy.closestEnemy();
        // target direset setiap tick, sehingga tidak akan berisi target pada state sebelumya

        if (target == null) {
            this.target = null;
            return Priority.NONE;
        } else if (Math.getTrueDistanceBetween(this.watcher.player, target) < 700 || this.watcher.enemies.size() == 1) {
            this.target = target;
            return Priority.NORMAL;
        }

        this.target = null;
        return Priority.NONE;
    }
}
