package Services;

import Enums.ObjectTypes;
import Models.GameObject;
import Utils.Math;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RadarUnitArea {
    public List<GameObject> enemies;
    public List<GameObject> foods;
    public List<GameObject> gasClouds;
    public List<GameObject> asteroidFields;
    public List<GameObject> torpedoes;
    public List<GameObject> others;

    public GameObject player;

    public RadarUnitArea() {
        this.enemies = new ArrayList<>();
        this.foods = new ArrayList<>();
        this.gasClouds = new ArrayList<>();
        this.asteroidFields = new ArrayList<>();
        this.torpedoes = new ArrayList<>();
        this.others = new ArrayList<>();
        this.player = null;
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

    public void updatePlayer(GameObject object) {
        this.player = object;
    }

    public double measureFoodAdvantage() {
        return this.foods.stream().mapToInt(element -> {
            int val;
            if (element.gameObjectType == ObjectTypes.FOOD) {
                val = 1;
            } else if (element.gameObjectType == ObjectTypes.SUPER_FOOD) {
                val = 2;
            } else if (element.gameObjectType == ObjectTypes.SUPERNOVA_PICKUP) {
                val = 6;
            } else {
                val = 0;
            }

            return val;
        }).sum();
    }

    public double measureThreatLevel() {
        return this.enemies.stream().mapToDouble(enemy -> java.lang.Math.max(enemy.getSize() - 0.05 * this.player.getSize(), 5)).sum();
    }

    public double measureTorpedoThreatLevel() {
        return this.torpedoes.stream().mapToDouble(torpedo -> Math.potentialIntercept(this.player, torpedo) ? 10 : 0).sum();
    }

    public double measureTerrainDisadvantage() {
        return this.asteroidFields.size() * 2 + this.gasClouds.size() * 5;
    }

    public double measureOverallAdvantage() {
        return this.measureFoodAdvantage() -
                this.measureThreatLevel() -
                this.measureTorpedoThreatLevel() -
                this.measureTerrainDisadvantage();
    }

    public GameObject getClosestFood() {
        if (this.foods.size() == 0) {
            return null;
        }

        var sortedFood = this.foods.
                stream().
                sorted(Comparator.comparing(food -> Math.getDistanceBetween(player, food))).
                collect(Collectors.toList());

        return sortedFood.get(0);
    }
}
