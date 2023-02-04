package Agents;

import Agents.Mahamatra.Mahamatra;
import Agents.Paimon.Paimon;

import java.util.HashMap;

enum AgentNames {
    PAIMON,
    MAHAMATRA;
}

public class AgentManager {
    private static final HashMap<AgentNames, Agent> agents = new HashMap<AgentNames, Agent>();

    public static Agent getAgent(AgentNames name) {
        if (agents.containsKey(name)) {
            return agents.get(name);
        }

        if (name == AgentNames.PAIMON) {
            agents.put(name, new Paimon());
        } else if (name == AgentNames.MAHAMATRA) {
            agents.put(name, new Mahamatra());
        }

        return agents.get(name);
    }

    public static Agent getDefaultAgent() {
        return getAgent(AgentNames.MAHAMATRA);
    }
}
