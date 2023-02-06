package Actions;

import Models.GameObject;
import Services.GameWatcher;
import Services.GameWatcherManager;
import Utils.Math;

import java.util.ArrayList;
import java.util.Comparator;
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

    public static ArrayList<GameObject> filterSmallerEnemy() {
        GameWatcher watcher = GameWatcherManager.getWatcher();

        ArrayList<GameObject> result = new ArrayList<>();

        for (var enemy : watcher.enemies) {
            if (watcher.player.size > enemy.size) {
                result.add(enemy);
            }
        }

        return result;
    }

    public static GameObject smallerEnemyWithin(double radius) {
        var candidate = filterSmallerEnemy();

        if (candidate.isEmpty()) {
            return null;
        }

        return candidate.stream().
                sorted(Comparator.comparing(enemy -> Math.getDistanceBetween(GameWatcherManager.getWatcher().player, enemy))).
                collect(Collectors.toList()).get(0);
    }
}
