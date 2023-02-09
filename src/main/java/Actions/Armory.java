package Actions;

import Models.GameObject;
import Services.GameWatcherManager;
import Utils.Math;

import java.util.ArrayList;
import java.util.List;

public class Armory {
    public static int TORPEDO_SPEED = 60;
    public static int TORPEDO_RADIUS_DETECTION = 500;

    public static int calculateTorpedoHeading(GameObject player, GameObject target) {
        // todo: gunakan fungsi getInterceptHeading
        return Math.getHeadingBetween(player, target);
    }

    public static List<GameObject> getIncomingTorpedoes() {
        var result = new ArrayList<GameObject>();
        var watcher = GameWatcherManager.getWatcher();

        watcher.torpedoes.forEach(torpedo -> {
            if (Math.getDistanceBetween(watcher.player, torpedo) < TORPEDO_RADIUS_DETECTION) {
                if (Math.potentialIntercept(watcher.player, torpedo)) {
                    result.add(torpedo);
                }
            }
        });

        return result;
    }

}
