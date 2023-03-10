package Services;

import Models.GameObject;
import Utils.Math;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Radar {
    public final int SHORT_RANGE_OFFSET = 250;
    public final int MEDIUM_RANGE_OFFSET = 500;
    public final int sectionCount;
    private final List<RadarSection> sections;
    public GameObject heading;
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
        this.sections.forEach(section -> section.updatePlayer(player));
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

    public int determineSection(GameObject object) {
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

    public List<RadarUnitArea> getMostAdvantageousArea() {
        return this.sections.stream().
                sorted(Comparator.comparing(RadarSection::measureShortRangeAdvantage).reversed()).
                map(each -> each.short_range).
                collect(Collectors.toList());
    }

    public List<RadarUnitArea> getMostAdvantageousAreaIn(Integer[] targetArea) {
        List<RadarSection> filteredSection = new ArrayList<>();

        for (int j : targetArea) {
            filteredSection.add(this.sections.get(Math.getModulus(j, this.sectionCount)));
        }

        return filteredSection.stream().
                sorted(Comparator.comparing(RadarSection::measureShortRangeAdvantage).reversed()).
                map(each -> each.short_range).
                collect(Collectors.toList());
    }

    public void predictShipHeading() {
//        var section = this.sections.get(this.determineSection(this.player));
        List<GameObject> potential = new ArrayList<>();
        this.sections.forEach(section -> {
            section.short_range.foods.
                    forEach(food -> {
                        if (Math.potentialInterceptFood(this.player, food)) {
                            potential.add(food);
                        }
                    });

            section.medium_range.foods.
                    forEach(food -> {
                        if (Math.potentialInterceptFood(this.player, food)) {
                            potential.add(food);
                        }
                    });
        });

        if (potential.size() == 0) {
            this.heading = null;
        } else {
            this.heading = potential.stream().
                    sorted(Comparator.comparing(food -> Math.getDistanceBetween(player, food))).
                    collect(Collectors.toList()).get(0);
        }
    }

    public GameObject closestGasCloud() {
        List<GameObject> pot_gas = new ArrayList<>();
        this.sections.forEach(section -> section.short_range.gasClouds.
                forEach(gasCloud -> {
                    if (gasCloud.size < 20) {
                        pot_gas.add(gasCloud);
                    }
                }));
        if (pot_gas.size() == 0) {
            return null;
        } else {
            return pot_gas.stream().
                    sorted(Comparator.comparing(gasCloud -> Math.getDistanceBetween(player, gasCloud))).
                    collect(Collectors.toList()).get(0);
        }
    }

    public boolean clearToShoot(GameObject target) {
        var section = this.determineSection(target);
        var range = this.determineRange(target);
        var targetHeading = Math.getHeadingBetween(this.player, target);
        List<GameObject> obstacles = new ArrayList<>();

        if (range == 0) {
            return true;
        } else {
            this.sections.get(section).short_range.gasClouds.forEach(gas -> {
                if (java.lang.Math.abs(targetHeading - Math.getHeadingBetween(this.player, gas)) < 5) {
                    obstacles.add(gas);
                }
            });

            this.sections.get(section).short_range.asteroidFields.forEach(asteroid -> {
                if (java.lang.Math.abs(targetHeading - Math.getHeadingBetween(this.player, asteroid)) < 5) {
                    obstacles.add(asteroid);
                }
            });

            if (range == 2) {
                this.sections.get(section).medium_range.gasClouds.forEach(gas -> {
                    if (java.lang.Math.abs(targetHeading - Math.getHeadingBetween(this.player, gas)) < 5) {
                        obstacles.add(gas);
                    }
                });

                this.sections.get(section).medium_range.asteroidFields.forEach(asteroid -> {
                    if (java.lang.Math.abs(targetHeading - Math.getHeadingBetween(this.player, asteroid)) < 5) {
                        obstacles.add(asteroid);
                    }
                });
            }
        }

        return obstacles.size() == 0;
    }
}
