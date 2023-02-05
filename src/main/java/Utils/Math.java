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

    public static int getInterceptHeading(float projectileSpeed, int targetHeading, float targetSpeed, float xDis, float yDis) {
        // todo: sudut optimal untuk intercept
        int targetDeg = changeDegreesAnchor(targetHeading);
        var adjAngle = java.lang.Math.atan(xDis/yDis);
        var targetAspect = targetSpeed * java.lang.Math.sin(toRadians(targetDeg)) - 
            (yDis/xDis * targetSpeed * java.lang.Math.cos(toRadians(targetDeg)));
        var resDeg = java.lang.Math.acos( java.lang.Math.sin(adjAngle) * targetAspect / projectileSpeed) - adjAngle;
        return  changeDegreesAnchor((int) resDeg);
    }

    public static int toDegrees(double v) {
        return (int) (v * (180 / java.lang.Math.PI));
    }

    public static double toRadians(int d){
        return d * (java.lang.Math.PI / 180);
    }

    public static int changeDegreesAnchor(int deg){
        // Used to convert degrees used in North Anchor Basis to East Anchor Basis, vice versa.
        return (450 - deg) % 360;
    }

}
