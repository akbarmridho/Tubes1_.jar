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

    public GameObject(UUID id, Integer size, Integer speed, Integer currentHeading, Position position, ObjectTypes gameObjectType, Integer torpedoSalvoCount) {
        this.id = id;
        this.size = size;
        this.speed = speed;
        this.currentHeading = currentHeading;
        this.position = position;
        this.gameObjectType = gameObjectType;
        this.torpedoSalvoCount = torpedoSalvoCount;
    }

    public static GameObject FromStateList(UUID id, List<Integer> stateList) {
        Position position = new Position(stateList.get(4), stateList.get(5));

        if (stateList.size() == 8) {
            return new GameObject(id, stateList.get(0), stateList.get(1), stateList.get(2), position, ObjectTypes.valueOf(stateList.get(3)), stateList.get(7));
        } else {
            return new GameObject(id, stateList.get(0), stateList.get(1), stateList.get(2), position, ObjectTypes.valueOf(stateList.get(3)), 0);
        }

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
}
