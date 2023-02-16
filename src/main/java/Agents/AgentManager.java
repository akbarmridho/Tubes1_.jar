package Agents;

import Agents.Ling.Ling;

import java.util.HashMap;

enum AgentNames {
    LING
}

public class AgentManager {
    private static final HashMap<AgentNames, Agent> agents = new HashMap<>();

    public static Agent getAgent(AgentNames name) {
        if (agents.containsKey(name)) {
            return agents.get(name);
        }

        if (name == AgentNames.LING) {
            agents.put(name, new Ling());
        }

        return agents.get(name);
    }

    public static Agent getDefaultAgent() {
        return getAgent(AgentNames.LING);
    }
}
