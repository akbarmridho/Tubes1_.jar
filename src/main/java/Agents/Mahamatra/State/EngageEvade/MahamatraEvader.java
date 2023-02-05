package Agents.Mahamatra.State.EngageEvade;

import Agents.Mahamatra.State.State;
import Models.PlayerAction;

public class MahamatraEvader implements State {
    @Override
    public PlayerAction computeNextAction() {
        // todo:
        // teknik: bergerak dengan afterburner
        // deploy shield
        // bergerak tegak lurus terhadap torpedo
        // menjauhi musuh yang mengejar
        return null;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public int measureTakeoverPriority() {
        return 0;
    }

    @Override
    public int measureInterruptionPriority() {
        return 0;
    }

    @Override
    public void receiveControl() {

    }
}
