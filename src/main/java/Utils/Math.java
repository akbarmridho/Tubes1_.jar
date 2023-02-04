package Utils;

import Models.GameObject;

public class Math {
    public static double getDistanceBetween(GameObject object1, GameObject object2) {
        var triangleX = java.lang.Math.abs(object1.getPosition().x - object2.getPosition().x);
        var triangleY = java.lang.Math.abs(object1.getPosition().y - object2.getPosition().y);
        return java.lang.Math.sqrt(triangleX * triangleX + triangleY * triangleY);
    }

    public static int getHeadingBetween(GameObject thisObject, GameObject otherObject) {
        var direction = toDegrees(java.lang.Math.atan2(otherObject.getPosition().y - thisObject.getPosition().y,
                otherObject.getPosition().x - thisObject.getPosition().x));
        return (direction + 360) % 360;
    }

    public static int toDegrees(double v) {
        return (int) (v * (180 / java.lang.Math.PI));
    }
}
