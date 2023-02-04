package Agents.Mahamatra.Actions;

import Models.GameObject;
import Utils.Math;

public class Armory {
    public static int TORPEDO_SPEED = 60;

    public static int calculateTorpedoHeading(GameObject player, GameObject target) {
        // todo: gunakan fungsi getInterceptHeading
        return Math.getHeadingBetween(player, target);
    }
}
