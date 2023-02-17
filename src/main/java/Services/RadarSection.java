package Services;

import Models.GameObject;

public class RadarSection {
    public RadarUnitArea short_range;
    public RadarUnitArea medium_range;
    public RadarUnitArea long_range;

    public GameObject player;

    public RadarSection() {
        this.short_range = new RadarUnitArea();
        this.medium_range = new RadarUnitArea();
        this.long_range = new RadarUnitArea();
        this.player = null;
    }

    public void clear() {
        this.short_range.clear();
        this.medium_range.clear();
        this.long_range.clear();
    }

    public void updateOther(GameObject object, int range) {
        this.getAreaByRange(range).updateOther(object);
    }

    public void updateTorpedoes(GameObject object, int range) {
        this.getAreaByRange(range).updateTorpedoes(object);
    }

    public void updateAsteroidField(GameObject object, int range) {
        this.getAreaByRange(range).updateAsteroidField(object);

        if (range >= 250) {
            this.short_range.updateOther(object);
        }
    }

    public void updateGasCloud(GameObject object, int range) {
        this.getAreaByRange(range).updateGasCloud(object);
    }

    public void updateFood(GameObject object, int range) {
        this.getAreaByRange(range).updateFood(object);
    }

    public void updateEnemy(GameObject object, int range) {
        this.getAreaByRange(range).updateEnemy(object);
    }

    public void updatePlayer(GameObject object) {
        this.player = object;
        this.short_range.updatePlayer(object);
        this.medium_range.updatePlayer(object);
        this.long_range.updatePlayer(object);
    }

    private RadarUnitArea getAreaByRange(int range) {
        if (range == 0) {
            return this.short_range;
        } else if (range == 1) {
            return this.medium_range;
        }

        return this.long_range;
    }

    public double measureShortRangeAdvantage() {
        return this.short_range.measureOverallAdvantage();
    }
}
