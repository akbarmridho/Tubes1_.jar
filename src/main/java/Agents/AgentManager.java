package Agents;

import Agents.Paimon.Paimon;

import java.util.HashMap;

enum AgentNames {
    PAIMON;
}

public class AgentManager {
    private static final HashMap<AgentNames, Agent> agents = new HashMap<AgentNames, Agent>();

    public static Agent getAgent(AgentNames name) {
        if (agents.containsKey(name)) {
            return agents.get(name);
        }

        if (name == AgentNames.PAIMON) {
            agents.put(name, new Paimon());
        }

        return agents.get(name);
    }

    public static Agent getDefaultAgent() {
        return getAgent(AgentNames.PAIMON);
    }
}
