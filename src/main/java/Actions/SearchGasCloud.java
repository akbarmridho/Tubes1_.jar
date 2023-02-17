package Actions;

import java.util.List;

import com.fasterxml.jackson.databind.type.PlaceholderForType;

import Models.GameObject;
import Services.GameWatcher;
import Services.GameWatcherManager;
import Services.Radar;
import Utils.Math;

public class SearchGasCloud {
    public static GameObject immediatelyDangerousGasCloud;

    // Returns gas cloud if player is in it, or else return null
    public static GameObject getImmediatelyDangerousGasCloud(GameObject player, List<GameObject> gasClouds) {
        Radar radar = GameWatcherManager.getWatcher().radar;
        GameObject closestGasCloud = radar.closestGasCloud();

        if (closestGasCloud != null
                && Math.getDistanceBetween(player, closestGasCloud) + player.size <= closestGasCloud.size) {
            immediatelyDangerousGasCloud = closestGasCloud;
        } else {
            immediatelyDangerousGasCloud = null;
        }
        return immediatelyDangerousGasCloud;
    }

    public static int safestHeading() {
        GameWatcher watcher = GameWatcherManager.getWatcher();
        if (immediatelyDangerousGasCloud != null)
            return Math.getHeadingBetween(immediatelyDangerousGasCloud, watcher.player);
        return watcher.player.currentHeading;
    }
}
