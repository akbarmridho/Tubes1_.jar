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
    private static int ANGULAR_VELOCITY_OFFSET = 30;
    private final GameWatcher watcher = GameWatcherManager.getWatcher();
    private GameObject target = null;

    @Override
    public PlayerAction computeNextAction() {
        // cek jika memerlukan heading adjustment
        var adjustment = headingAdjustment();

        if (adjustment != null) {
            return adjustment;
        }

        if (this.target == null) {
            return null;
        }

        if (this.watcher.player.torpedoSalvoCount > 0) {
            var heading = Armory.calculateTorpedoHeading(this.watcher.player, this.target);
            var action = new PlayerAction();
            action.setHeading(heading);
            action.setAction(PlayerActions.FIRETORPEDOES);
            return action;
        }

        return null;
    }

    private PlayerAction headingAdjustment() {
        var targetSection = watcher.radar.determineSection(this.target);
        var currentHeading = watcher.radar.heading;
        var radarSectionCount = watcher.radar.sectionCount;
        // cek jika ada kapal yang akan nabrak
        if (this.target != null && Math.potentialIntercept(this.watcher.player, this.target)) {
            // cek jika sudah pada course yang tepat (manuver menghindar)
            Integer[] safeSection = new Integer[]{(targetSection - 1) % radarSectionCount,
                    (targetSection - 2) % radarSectionCount,
                    (targetSection + 1) % radarSectionCount,
                    (targetSection + 2 % radarSectionCount)};
            var potentialAreas = this.watcher.radar.getMostAdvantageousAreaIn(safeSection);

            if (currentHeading == null) {
                return foodTargetFromAreaList(potentialAreas);
            }

            var currentHeadingSection = this.watcher.radar.determineSection(currentHeading);

            if (List.of(safeSection).contains(currentHeadingSection)) {
                return null;
            }

            return foodTargetFromAreaList(potentialAreas);
        }

        // fokus mengejar tetapi tetap jaga jarak
        var angularVelocity = Math.toDegrees(Math.calculateAngularVelocity(this.watcher.player, this.target));
        Integer[] safeSection;

        if (angularVelocity < ANGULAR_VELOCITY_OFFSET * (-1)) {
            safeSection = new Integer[]{(targetSection - 1) % radarSectionCount, (targetSection - 2) % radarSectionCount};
        } else if (angularVelocity > ANGULAR_VELOCITY_OFFSET) {
            safeSection = new Integer[]{(targetSection + 1) % radarSectionCount, (targetSection + 2) % radarSectionCount};
        } else {
            safeSection = new Integer[]{targetSection,
                    (targetSection - 1) % radarSectionCount,
                    (targetSection + 1) % radarSectionCount};
        }

        var currentHeadingSection = this.watcher.radar.determineSection(currentHeading);

        if (List.of(safeSection).contains(currentHeadingSection)) {
            return null;
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
        } else if (target.getSize() < watcher.player.getSize()) {
            this.target = target;
            return Priority.NORMAL;
        }

        this.target = null;
        return Priority.NONE;
    }
}
