package Actions;

import java.util.List;

import Enums.ObjectTypes;
import Models.GameObject;
import Services.GameWatcher;
import Services.GameWatcherManager;

public class SearchSupernova {
    public static final int SUPERNOVA_DANGER_DIST = 500;
    public static final int SUPERNOVA_ADDED_BEARINGS = 8;

    public static GameObject getDangerousSupernova(GameObject player, List<GameObject> gameObjects) {
        return gameObjects.stream()
                .filter(supernova -> supernova.gameObjectType == ObjectTypes.SUPERNOVA_BOMB
                        && Utils.Math.projectileWillHit(supernova, player, SUPERNOVA_ADDED_BEARINGS,
                                SUPERNOVA_DANGER_DIST))
                .findFirst()
                .orElse(null);
    }

    public static int safestHeading() {
        GameWatcher watcher = GameWatcherManager.getWatcher();
        GameObject player = watcher.player;
        GameObject supernova = getDangerousSupernova(player, watcher.others);

        if (supernova == null) {
            return player.currentHeading;
        } else if (Utils.Math.getDistanceBetween(player, supernova) <= watcher.supernovaBlastRadius
                && Utils.Math.headingDiff(Utils.Math.getHeadingBetween(supernova, player),
                        supernova.currentHeading) > 60) {
            return Utils.Math.getHeadingBetween(supernova, player);
        } else {
            return (supernova.currentHeading + 90) % 360;
        }
    }
}
