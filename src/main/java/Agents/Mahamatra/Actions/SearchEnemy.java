package Agents.Mahamatra.Actions;

import Models.GameObject;
import Services.GameWatcher;
import Services.GameWatcherManager;
import Utils.Math;

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

    public static GameObject biggerEnemy() {
        GameWatcher watcher = GameWatcherManager.getWatcher();
        GameObject player = watcher.player;

        if (watcher.enemies.size() == 0) {
            return null;
        }

        var sortedEnemies = watcher.enemies.
                stream().
                sorted(Comparator.comparing(enemy -> enemy.size - player.size)).
                collect(Collectors.toList());

        return sortedEnemies.get(0);
    }

    public static GameObject smallerEnemy() {
        GameWatcher watcher = GameWatcherManager.getWatcher();
        GameObject player = watcher.player;

        if (watcher.enemies.size() == 0) {
            return null;
        }

        var sortedEnemies = watcher.enemies.
                stream().
                sorted(Comparator.comparing(enemy -> player.size - enemy.size)).
                collect(Collectors.toList());

        return sortedEnemies.get(0);
    }
}
