package Services;

import Models.GameObject;
import Models.GameStateDto;
import Models.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GameWatcher {
    public static boolean shouldAct = false;
    public GameObject player = null;
    public List<GameObject> enemies;
    public List<GameObject> foods;
    public List<GameObject> gasClouds;
    public List<GameObject> asteroidFields;
    public List<GameObject> torpedoes;
    public List<GameObject> others;
    public World world;
    private UUID id;

    public GameWatcher() {
        this.enemies = new ArrayList<>();
        this.foods = new ArrayList<>();
        this.gasClouds = new ArrayList<>();
        this.asteroidFields = new ArrayList<>();
        this.torpedoes = new ArrayList<>();
        this.others = new ArrayList<>();
    }

    public void initializePlayer(UUID id) {
        this.id = id;
//        Position position = new Position();
//        this.player = new GameObject(id, 10, 20, 0, position, ObjectTypes.PLAYER, 0);
    }

    private void clearObjects() {
        this.enemies.clear();
        this.foods.clear();
        this.gasClouds.clear();
        this.asteroidFields.clear();
        this.torpedoes.clear();
        this.others.clear();
    }

    public void reloadData(GameStateDto data) {
        this.world = data.getWorld();
        this.clearObjects();

        for (Map.Entry<String, List<Integer>> objectEntry : data.getPlayerObjects().entrySet()) {
            this.handleObject(objectEntry);
        }

        for (Map.Entry<String, List<Integer>> objectEntry : data.getGameObjects().entrySet()) {
            this.handleObject(objectEntry);
        }

        shouldAct = true;
    }

    public GameObject getEnemyById(UUID id) {
        for (var enemy : this.enemies) {
            if (enemy.getId().equals(id)) {
                return enemy;
            }
        }

        return null;
    }

    private void handleObject(Map.Entry<String, List<Integer>> objectEntry) {
        GameObject object = GameObject.FromStateList(UUID.fromString(objectEntry.getKey()), objectEntry.getValue());

        switch (object.getGameObjectType()) {
            case PLAYER:
                if (object.getId().equals(this.id)) {
                    this.player = object;
                } else {
                    this.enemies.add(object);
                }
                break;
            case FOOD:
                this.foods.add(object);
                break;
            case GAS_CLOUD:
                this.gasClouds.add(object);
                break;
            case ASTEROID_FIELD:
                this.asteroidFields.add(object);
                break;
            case TORPEDO_SLAVO:
                this.torpedoes.add(object);
                break;
            default:
                this.others.add(object);
                break;
        }
    }
}

