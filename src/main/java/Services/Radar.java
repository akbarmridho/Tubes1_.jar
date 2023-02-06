package Services;

import Models.GameObject;
import Utils.Math;

import java.util.ArrayList;
import java.util.List;

public class Radar {
    public final int SHORT_RANGE_OFFSET = 250;
    public final int MEDIUM_RANGE_OFFSET = 500;
    public final int sectionCount;
    private final List<RadarSection> sections;
    private GameObject player;

    public Radar(int sectionCount) {
        this.sectionCount = sectionCount;
        this.player = null;

        this.sections = new ArrayList<>();

        for (int i = 0; i < this.sectionCount; i++) {
            sections.add(i, new RadarSection());
        }
    }

    public Radar() {
        this.sectionCount = 6;
        this.sections = new ArrayList<>();

        for (int i = 0; i < this.sectionCount; i++) {
            sections.add(i, new RadarSection());
        }
    }

    public void clear() {
        this.sections.forEach(RadarSection::clear);
    }

    public void updatePlayer(GameObject player) {
        this.player = player;
    }

    public void updateOther(GameObject object) {
        this.sections.get(determineSection(object)).updateOther(object, this.determineRange(object));
    }

    public void updateTorpedoes(GameObject object) {
        this.sections.get(determineSection(object)).updateTorpedoes(object, this.determineRange(object));
    }

    public void updateAsteroidField(GameObject object) {
        this.sections.get(determineSection(object)).updateAsteroidField(object, this.determineRange(object));
    }

    public void updateGasCloud(GameObject object) {
        this.sections.get(determineSection(object)).updateGasCloud(object, this.determineRange(object));
    }

    public void updateFood(GameObject object) {
        this.sections.get(determineSection(object)).updateFood(object, this.determineRange(object));
    }

    public void updateEnemy(GameObject object) {
        this.sections.get(determineSection(object)).updateEnemy(object, this.determineRange(object));
    }

    private int determineSection(GameObject object) {
        int heading = Math.getHeadingBetween(this.player, object);
        return (heading * this.sectionCount / 360) % this.sectionCount;
    }

    private int determineRange(GameObject object) {
        var distance = Math.getDistanceBetween(this.player, object);

        if (distance < SHORT_RANGE_OFFSET) {
            return 0;
        } else if (distance < MEDIUM_RANGE_OFFSET) {
            return 1;
        }

        return 2;
    }
}
