package Agents.Mahamatra;

import Agents.Agent;
import Agents.Mahamatra.State.EngageAttack.MahamatraChaser;
import Agents.Mahamatra.State.Exploring.MahamatraExplorer;
import Agents.Mahamatra.State.State;
import Models.PlayerAction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Mahamatra implements Agent {
    public static final int PRIORITY_NONE = 0;
    public static final int PRIORITY_IDLE = 1;
    public static final int PRIORITY_NORMAL = 2;
    public static final int PRIORITY_HIGH = 3;
    public static final int PRIORITY_EMERGENCY = 4;
    public static final int SHIP_SIZE_IDEAL = 50;
    public static final int SHIP_SIZE_CRITICAL = 20;
    private final ArrayList<State> states;
    private State activeState;

    public Mahamatra() {
        this.states = new ArrayList<>();
        this.states.add(new MahamatraExplorer());
        this.states.add(new MahamatraChaser());
        this.setActiveState(getStateCandidate());
    }

    private void setActiveState(State state) {
        this.activeState = state;
        this.activeState.receiveControl();
    }

    private State getStateCandidate() {
        return this.states.stream().sorted(Comparator.comparing(state -> state.measureTakeoverPriority() * (-1))).collect(Collectors.toList()).get(0);
    }

    private State getEmergencyStateCandidate() {
        return this.states.stream().sorted(Comparator.comparing(state -> state.measureInterruptionPriority() * (-1))).collect(Collectors.toList()).get(0);
    }

    @Override
    public PlayerAction computeNextAction() {
        if (this.activeState.giveUpControl()) {
            this.setActiveState(getStateCandidate());
        } else {
            var candidate = getEmergencyStateCandidate();

            if (candidate != this.activeState && candidate.measureInterruptionPriority() > this.activeState.measureTakeoverPriority()) {
                this.setActiveState(candidate);
            }
        }

        return this.activeState.computeNextAction();
    }
}
