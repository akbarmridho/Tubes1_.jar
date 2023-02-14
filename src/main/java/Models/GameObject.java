package Models;

import Enums.ObjectTypes;

import java.util.List;
import java.util.UUID;

public class GameObject {
    public UUID id;
    public Integer size;
    public Integer speed;
    public Integer currentHeading;
    public Position position;
    public ObjectTypes gameObjectType;
    public Integer torpedoSalvoCount;

    public Integer effects;

    public Integer supernovaAvailable;

    public Integer shieldCount;

    public Integer teleporterCount;

    public GameObject(
            UUID id,
            Integer size,
            Integer speed,
            Integer currentHeading,
            Position position,
            ObjectTypes gameObjectType,
            Integer torpedoSalvoCount,
            Integer effects,
            Integer supernovaAvailable,
            Integer shieldCount,
            Integer teleporterCount) {
        this.id = id;
        this.size = size;
        this.speed = speed;
        this.currentHeading = currentHeading;
        this.position = position;
        this.gameObjectType = gameObjectType;
        this.torpedoSalvoCount = torpedoSalvoCount;
        this.effects = effects;
        this.supernovaAvailable = supernovaAvailable;
        this.shieldCount = shieldCount;
        this.teleporterCount = teleporterCount;
    }

    public static GameObject MockSizePositionHeading(int x, int y, int size, int heading, ObjectTypes objectType) {
        Position position = new Position(x, y);
        UUID mockID = new UUID(0, 0);

        return new GameObject(
                mockID,
                size,
                0,
                heading,
                position,
                objectType,
                0,
                0,
                0,
                0,
                0);
    }

    public static GameObject FromStateList(UUID id, List<Integer> stateList) {
        Position position = new Position(stateList.get(4), stateList.get(5));

        if (stateList.size() == 7) {
            return new GameObject(
                    id,
                    stateList.get(0),
                    stateList.get(1),
                    stateList.get(2),
                    position,
                    ObjectTypes.valueOf(stateList.get(3)),
                    0,
                    stateList.get(6),
                    0,
                    0,
                    0);
        }

        return new GameObject(
                id,
                stateList.get(0),
                stateList.get(1),
                stateList.get(2),
                position,
                ObjectTypes.valueOf(stateList.get(3)),
                stateList.get(7),
                stateList.get(6),
                stateList.get(8),
                stateList.get(10),
                stateList.get(9));
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public ObjectTypes getGameObjectType() {
        return gameObjectType;
    }

    public void setGameObjectType(ObjectTypes gameObjectType) {
        this.gameObjectType = gameObjectType;
    }

    public boolean isAfterburnerActive() {
        return (this.effects & 1) == 1;
    }
}
