package Utils;

import Models.GameObject;

import static java.lang.Math.abs;

public class Math {
    public static int getModulus(int n, int m) {
        return (n < 0) ? (m - (abs(n) % m)) % m : (n % m);
    }

    public static double getDistanceBetween(GameObject object1, GameObject object2) {
        var triangleX = abs(object1.getPosition().x - object2.getPosition().x);
        var triangleY = abs(object1.getPosition().y - object2.getPosition().y);
        return java.lang.Math.sqrt(triangleX * triangleX + triangleY * triangleY);
    }

    public static double getTrueDistanceBetween(GameObject object1, GameObject object2) {
        return getDistanceBetween(object1, object2) - object1.size - object2.size;
    }

    public static int getHeadingBetween(GameObject thisObject, GameObject otherObject) {
        var direction = toDegrees(java.lang.Math.atan2(otherObject.getPosition().y - thisObject.getPosition().y,
                otherObject.getPosition().x - thisObject.getPosition().x));
        return (direction + 360) % 360;
    }

    public static int getCenterHeading(GameObject player) {
        var direction = toDegrees(java.lang.Math.atan2(-player.getPosition().y,
                -player.getPosition().x));
        return (direction + 360) % 360;
    }

    public static int headingDiff(int theta1, int theta2) {
        int theta = abs(theta1 - theta2) % 360;
        return theta > 180 ? 360 - theta : theta;
    }

    public static int getInterceptHeading(float projectileSpeed, int targetHeading, float targetSpeed, float xDis,
            float yDis) {
        // todo: sudut optimal untuk intercept
        var tgtDeg = toRadians(targetHeading);
        var adjAngle = java.lang.Math.atan(yDis / xDis);
        var tgtAspect = targetSpeed * (java.lang.Math.sin(tgtDeg) - (yDis / xDis * java.lang.Math.cos(tgtDeg)));
        var gamma = java.lang.Math.asin(-1 * java.lang.Math.cos(adjAngle) * tgtAspect / projectileSpeed);
        var res = toDegrees(adjAngle - gamma);
        // Check if it is possible to intercept with the current angle
        var verificator = yDis / (projectileSpeed * java.lang.Math.sin(toRadians(res)) -
                targetSpeed * java.lang.Math.sin(toRadians(targetHeading)));

        System.out.println("Intercepting Torpedos Deployed");
        if (verificator < 0) {
            return toDegrees(adjAngle + gamma - java.lang.Math.PI);
        } else {
            return res;
        }

    }

    public static int toDegrees(double v) {
        return (int) (v * (180 / java.lang.Math.PI));
    }

    public static double toRadians(int d) {
        return d * (java.lang.Math.PI / 180);
    }

    public static boolean potentialIntercept(GameObject target, GameObject projectile) {
        int targetHeading = getHeadingBetween(projectile, target);
        int tolerance = toDegrees(java.lang.Math.atan(target.size / getDistanceBetween(target, projectile)));

        return abs(targetHeading - projectile.currentHeading) <= tolerance;
    }

    public static boolean potentialInterceptFood(GameObject player, GameObject food) {
        var distance = getDistanceBetween(player, food);
        double angleTolerance = toDegrees(java.lang.Math.asin(player.size / distance));

        return java.lang.Math.abs(player.currentHeading - getHeadingBetween(player, food)) < angleTolerance;
    }

    public static int calculateAngularVelocity(GameObject pivot, GameObject target) {
        // todo: benerin rumusnya
        var relativeHeading = getHeadingBetween(pivot, target) - target.currentHeading;
        var speedPerpendicular = java.lang.Math.cos(toRadians(relativeHeading)) * target.speed;
        var distance = getDistanceBetween(pivot, target);

        return toDegrees(speedPerpendicular / distance);
    }

    public static boolean isOutOfBound(GameObject player, Integer worldRadius) {
        var position = player.getPosition();
        return java.lang.Math.sqrt(position.x * position.x + position.y * position.y) + player.size > worldRadius;
    }

    public static boolean projectileWillHit(GameObject projectile, GameObject object, int addedDegrees,
            int minDistance) {
        int dangerHeading = toDegrees(
                java.lang.Math.abs(java.lang.Math.asin(object.size / getDistanceBetween(projectile, object))))
                + addedDegrees;
        return (getDistanceBetween(projectile, object) < minDistance)
                && headingDiff(getHeadingBetween(projectile, object), projectile.currentHeading) < dangerHeading;
    }
}
