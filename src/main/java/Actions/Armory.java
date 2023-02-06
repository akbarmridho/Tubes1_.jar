package Actions;

import Models.GameObject;
import Utils.Math;

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
}
