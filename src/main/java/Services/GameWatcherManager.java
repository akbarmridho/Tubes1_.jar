package Services;

public class GameWatcherManager {
    public static GameWatcher watcher = null;

    public static GameWatcher getWatcher() {
        if (watcher == null) {
            watcher = new GameWatcher();
        }

        return watcher;
    }
}
