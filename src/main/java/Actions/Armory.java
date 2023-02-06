package Actions;

import Models.GameObject;
import Services.GameWatcherManager;
import Utils.Math;

import java.util.ArrayList;
import java.util.List;

public class Armory {
    public static int TORPEDO_SPEED = 60;

    public static int calculateTorpedoHeading(GameObject player, GameObject target) {
        // todo: gunakan fungsi getInterceptHeading

        var oldIntercept = Math.getHeadingBetween(player, target);
//        var newIntercept = Math.getIntercept(
//                TORPEDO_SPEED,
//                target.currentHeading,
//                target.speed,
//                target.getPosition().x - player.getPosition().x,
//                target.getPosition().y - player.getPosition().y);

//        System.out.format("Degree diff %d\n", newIntercept - oldIntercept);
        return oldIntercept;
    }

    public static List<GameObject> getIncomingTorpedoes() {
        var result = new ArrayList<GameObject>();
        var watcher = GameWatcherManager.getWatcher();

        watcher.torpedoes.forEach(torpedo -> {
            if (Math.incomingTorpedo(watcher.player, torpedo)) {
                result.add(torpedo);
            }
        });

        return result;
    }

}
