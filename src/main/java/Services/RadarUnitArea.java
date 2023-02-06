package Services;

import Models.GameObject;

import java.util.ArrayList;
import java.util.List;

public class RadarUnitArea {
    public List<GameObject> enemies;
    public List<GameObject> foods;
    public List<GameObject> gasClouds;
    public List<GameObject> asteroidFields;
    public List<GameObject> torpedoes;
    public List<GameObject> others;

    public RadarUnitArea() {
        this.enemies = new ArrayList<>();
        this.foods = new ArrayList<>();
        this.gasClouds = new ArrayList<>();
        this.asteroidFields = new ArrayList<>();
        this.torpedoes = new ArrayList<>();
        this.others = new ArrayList<>();
    }

    public void clear() {
        this.enemies.clear();
        this.foods.clear();
        this.gasClouds.clear();
        this.asteroidFields.clear();
        this.torpedoes.clear();
        this.others.clear();
    }

    public void updateOther(GameObject object) {
        this.others.add(object);
    }

    public void updateTorpedoes(GameObject object) {
        this.torpedoes.add(object);
    }

    public void updateAsteroidField(GameObject object) {
        this.asteroidFields.add(object);
    }

    public void updateGasCloud(GameObject object) {
        this.gasClouds.add(object);
    }

    public void updateFood(GameObject object) {
        this.foods.add(object);
    }

    public void updateEnemy(GameObject object) {
        this.enemies.add(object);
    }

}
