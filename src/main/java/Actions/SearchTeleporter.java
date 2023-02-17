package Actions;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import Enums.ObjectTypes;
import Models.GameObject;
import Services.GameWatcher;
import Services.GameWatcherManager;

public class SearchTeleporter {
    public static final int TELEPORTER_DANGER_DIST = 400;
    public static final int TELEPORTER_ADDED_BEARINGS = 10;
    public static int largestEnemySize;

    public static List<GameObject> filterDangerousTeleporters(GameObject player, List<GameObject> gameObjects,
            List<GameObject> enemies) {
        enemies.stream().max(Comparator.comparing(enemy -> enemy.size)).ifPresentOrElse(
                (enemy) -> {
                    largestEnemySize = enemy.size;
                },
                () -> {
                    largestEnemySize = 0;
                });

        return gameObjects.stream()
                .filter(teleporter -> teleporter.gameObjectType == ObjectTypes.TELEPORTER
                        && (Utils.Math.projectileWillHit(teleporter, player, TELEPORTER_ADDED_BEARINGS,
                                TELEPORTER_DANGER_DIST)
                                || Utils.Math.getDistanceBetween(teleporter, player) <= largestEnemySize))
                .collect(Collectors.toList());
    }

    public static int safestHeading() {
        GameWatcher watcher = GameWatcherManager.getWatcher();
        GameObject player = watcher.player;
        List<GameObject> teleporters = filterDangerousTeleporters(player, watcher.others, watcher.enemies);

        GameObject closestTeleporter = teleporters.stream()
                .min(Comparator.comparing(teleporter -> teleporter.currentHeading))
                .orElse(null);

        if (closestTeleporter == null) {
            return player.currentHeading;
        }

        if (Utils.Math.getDistanceBetween(player, closestTeleporter) <= largestEnemySize
                && Utils.Math.headingDiff(Utils.Math.getHeadingBetween(closestTeleporter, player),
                        closestTeleporter.currentHeading) > 60) {
            return Utils.Math.getHeadingBetween(closestTeleporter, player);
        }
        return (closestTeleporter.currentHeading + 90) % 360;
    }
}
