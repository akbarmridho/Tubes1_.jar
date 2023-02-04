package Agents.Mahamatra.State;

import Agents.Agent;

public interface State extends Agent {
    public boolean giveUpControl();

    public int measureTakeoverPriority();

    public int measureEmergencyTakeoverPriority();

    public void receiveControl();
}
