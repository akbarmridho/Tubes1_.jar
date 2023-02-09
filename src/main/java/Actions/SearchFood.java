package Actions;

import Models.GameObject;
import Services.GameWatcher;
import Services.GameWatcherManager;
import Utils.Math;

import java.util.Comparator;
import java.util.stream.Collectors;

public class SearchFood {
    public static GameObject closestFood() {
        GameWatcher watcher = GameWatcherManager.getWatcher();
        GameObject player = watcher.player;

        if (watcher.foods.size() == 0) {
            return null;
        }

        var sortedFood = watcher.foods.
                stream().
                sorted(Comparator.comparing(food -> Math.getDistanceBetween(player, food))).
                collect(Collectors.toList());

        return sortedFood.get(0);
    }

    public static GameObject closestSafestFood() {
        GameWatcher watcher = GameWatcherManager.getWatcher();

        var potentialArea = watcher.radar.getMostAdvantageousArea();

        GameObject result = null;
        int i = 0;

        while (result == null && i < potentialArea.size()) {
            result = potentialArea.get(i++).getClosestFood();
        }

        return result;
    }
}
