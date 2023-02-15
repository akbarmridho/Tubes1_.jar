package Actions;

import Enums.PlayerActions;
import Models.GameObject;
import Models.PlayerAction;
import Services.GameWatcherManager;
import Utils.Math;

import java.util.ArrayList;
import java.util.List;

public class Armory {
    public static int TORPEDO_SPEED = 60;
    public static int TORPEDO_RADIUS_DETECTION = 500;

    public static int calculateTorpedoHeading(GameObject player, GameObject target) {
        if (Math.getDistanceBetween(player, target) > 300){
            var angularVelocity = Math.calculateAngularVelocity(player, target);
            var initialHeading = Math.getHeadingBetween(player, target);
            var distance = Math.getDistanceBetween(player, target);

            int adjustment = 0;

            if (angularVelocity >= 2) {
                adjustment += 2;
            } else if (angularVelocity <= -2) {
                adjustment -= 2;
            }

            if (distance > 500) {
                adjustment *= 2;
            }

            if (adjustment != 0) {
                System.out.println("Torpedo firing adjustment");
            }
            return initialHeading+adjustment;
        } else {
            return Math.getInterceptHeading(60, target.currentHeading, target.getSpeed(), 
                            target.position.x - player.position.x, target.position.y - player.position.y);
        }
    }

    public static PlayerAction fireTorpedo(GameObject target) {
        var heading = calculateTorpedoHeading(GameWatcherManager.getWatcher().player, target);
        var action = new PlayerAction();
        action.setHeading(heading);
        action.setAction(PlayerActions.FIRETORPEDOES);
        return action;
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
