package Enums;

public enum PlayerActions {
    FORWARD(1),
    STOP(2),
    START_AFTERBURNER(3),
    STOP_AFTERBURNER(4),

    FIRETORPEDOES(5),

    FIRE_SUPERNOVA(6),

    DETONATE_SUPERNOVA(7),

    FILE_TELEPORT(8),

    TELEPORT(9),

    ACTIVATE_SHIELD(10);

    public final Integer value;

    PlayerActions(Integer value) {
        this.value = value;
    }
}
