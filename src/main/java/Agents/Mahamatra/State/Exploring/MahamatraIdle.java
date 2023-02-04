package Agents.Mahamatra.State.Exploring;

import Agents.Mahamatra.State.State;
import Models.PlayerAction;

import static Agents.Mahamatra.Mahamatra.PRIORITY_IDLE;
import static Agents.Mahamatra.Mahamatra.PRIORITY_NONE;

public class MahamatraIdle implements State {
    @Override
    public PlayerAction computeNextAction() {
        // todo: gerakkan ke arah yang menjauh dari bahaya
        // menghindar dari kabut racun, bergerak ke pusat, dst
        return null;
    }

    @Override
    public boolean giveUpControl() {
        return true;
    }

    @Override
    public int measureTakeoverPriority() {
        return PRIORITY_IDLE;
    }

    @Override
    public int measureEmergencyTakeoverPriority() {
        return PRIORITY_NONE;
    }

    @Override
    public void receiveControl() {
        System.out.println("Mahamatra idle was given control");
    }
}
