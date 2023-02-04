package Agents.Mahamatra;

import Agents.Agent;
import Agents.Mahamatra.State.Exploring.MahamatraExplorer;
import Agents.Mahamatra.State.State;
import Models.PlayerAction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Mahamatra implements Agent {
    public static final int PRIORITY_NONE = 0;
    public static final int PRIORITY_NORMAL = 1;
    public static final int PRIORITY_HIGH = 2;
    public static final int PRIORITY_EMERGENCY = 3;
    public static final int SHIP_SIZE_IDEAL = 100;
    public static final int SHIP_SIZE_CRITICAL = 20;
    private final ArrayList<State> states;
    private State activeState;

    public Mahamatra() {
        this.states = new ArrayList<>();
        this.states.add(new MahamatraExplorer());
        this.setActiveState();
    }

    private void setActiveState() {
        this.activeState = this.states.stream().sorted(Comparator.comparing(state -> state.takeControl() * (-1))).collect(Collectors.toList()).get(0);
    }

    @Override
    public PlayerAction computeNextAction() {
        if (this.activeState.giveUpControl()) {
            this.setActiveState();
        }

        return this.activeState.computeNextAction();
    }
}
