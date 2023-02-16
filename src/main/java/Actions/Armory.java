package Actions;

import Enums.PlayerActions;
import Models.GameObject;
import Models.PlayerAction;
import Services.GameWatcherManager;
import Utils.Math;


public class Armory {
    public static int TORPEDO_SPEED = 60;

    public static int calculateTorpedoHeading(GameObject player, GameObject target) {
        if (Math.getDistanceBetween(player, target) > 300) {
            var angularVelocity = Math.calculateAngularVelocity(player, target);
            var initialHeading = Math.getHeadingBetween(player, target);
            var distance = Math.getDistanceBetween(player, target);

            int adjustment = 0;

            if (angularVelocity >= 2) {
                adjustment += 5;
            } else if (angularVelocity <= -2) {
                adjustment -= 5;
            }

            if (distance > 500) {
                adjustment *= 2;
            }

            if (adjustment != 0) {
                System.out.println("Torpedo firing adjustment");
            }

            return initialHeading + adjustment;
        } else {
            return Math.getInterceptHeading(TORPEDO_SPEED, target.currentHeading, target.getSpeed(),
                    target.position.x - player.position.x, target.position.y - player.position.y);
        }
    }

    public static PlayerAction fireTorpedo(GameObject target) {
        var heading = calculateTorpedoHeading(GameWatcherManager.getWatcher().player, target);
        return fireTorpedoWithHeading(heading);
    }

    public static PlayerAction fireTorpedoWithHeading(int heading) {
        var action = new PlayerAction();
        action.setHeading(heading);
        action.setAction(PlayerActions.FIRETORPEDOES);
        return action;
    }
}
