package Actions;

import Models.GameObject;
import Services.DebugWriter;
import Services.GameWatcher;
import Services.GameWatcherManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.Math;

import Enums.ObjectTypes;

public class SearchTorpedoes {
    public static final int TORPEDO_DANGER_DIST = 300;
    public static final int TORPEDO_ADDED_BEARINGS = 8;
    public static final int TORPEDO_EXTREMELY_CLOSE = 50;

    public static List<GameObject> filterDangerousTorpedoes(GameObject player, List<GameObject> gameObjects) {
        return gameObjects.stream()
                .filter(gameObject -> gameObject.gameObjectType == ObjectTypes.TORPEDO_SLAVO)
                .filter(torpedo -> Utils.Math.projectileWillHit(torpedo, player, TORPEDO_ADDED_BEARINGS,
                        TORPEDO_DANGER_DIST))
                .collect(Collectors.toList());
    }

    public static int safestHeading() {
        GameWatcher watcher = GameWatcherManager.getWatcher();
        GameObject player = watcher.player;
        List<GameObject> torpedoes = filterDangerousTorpedoes(player, watcher.torpedoes);

        GameObject closestTorpedo = torpedoes.stream().min(Comparator.comparing(torpedo -> torpedo.currentHeading))
                .orElse(null);

        // BufferedWriter writer = DebugWriter.getWriter();
        // try {
        // writer.write("Tick: " + watcher.world.currentTick);
        // writer.newLine();
        // writer.write("Safe Heading: " + (closestTorpedo.currentHeading + 90) % 360);
        // writer.newLine();
        // writer.newLine();
        // writer.flush();
        // } catch (IOException e) {
        // System.err.println("Problem writing");
        // }

        if (Utils.Math.getDistanceBetween(player, closestTorpedo) <= TORPEDO_EXTREMELY_CLOSE
                && Utils.Math.headingDiff(Utils.Math.getHeadingBetween(closestTorpedo, player),
                        closestTorpedo.currentHeading) > 45) {
            return Utils.Math.getHeadingBetween(closestTorpedo, player);
        }
        return (closestTorpedo.currentHeading + 90) % 360;
    }
}
