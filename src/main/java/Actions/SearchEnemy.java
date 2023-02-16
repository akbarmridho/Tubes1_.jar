package Actions;

import Models.GameObject;
import Services.GameWatcher;
import Services.GameWatcherManager;
import Utils.Math;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SearchEnemy {
    public static GameObject closestEnemy() {
        GameWatcher watcher = GameWatcherManager.getWatcher();
        GameObject player = watcher.player;

        if (watcher.enemies.size() == 0) {
            return null;
        }

        var sortedEnemies = watcher.enemies.
                stream().
                sorted(Comparator.comparing(enemy -> Math.getDistanceBetween(player, enemy))).
                collect(Collectors.toList());

        return sortedEnemies.get(0);
    }

    public static GameObject largestEnemy() {
        GameWatcher watcher = GameWatcherManager.getWatcher();

        if (watcher.enemies.size() == 0) {
            return null;
        }

        var sortedEnemies = watcher.enemies.
                stream().
                sorted(Comparator.comparing(enemy -> enemy.size * -1)).
                collect(Collectors.toList());

        return sortedEnemies.get(0);
    }

    public static List<GameObject> enemyAboveRange(int radius) {
        GameWatcher watcher = GameWatcherManager.getWatcher();
        GameObject player = watcher.player;

        if (watcher.enemies.size() == 0) {
            return null;
        }

        return watcher.enemies.
                stream().
                filter(enemy -> Math.getDistanceBetween(player, enemy) >= radius).
                collect(Collectors.toList());
    }
}
