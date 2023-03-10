import Agents.AgentManager;
import Models.GameStateDto;
import Services.GameWatcherManager;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;

import java.util.UUID;

import static Services.GameWatcher.shouldAct;

public class Main {
    public static void main(String[] args) throws Exception {
        String token = System.getenv("Token");
        token = (token != null) ? token : UUID.randomUUID().toString();

        String environmentIp = System.getenv("RUNNER_IPV4");

        String ip = (environmentIp != null && !environmentIp.isBlank()) ? environmentIp : "localhost";
        ip = ip.startsWith("http://") ? ip : "http://" + ip;

        String url = ip + ":" + "5000" + "/runnerhub";

        String botName;
        if (args.length != 0) {
            botName = String.format("%s - Dotjar", args[0]);
        } else {
            botName = "Dotjar";
        }

        HubConnection hubConnection = HubConnectionBuilder.create(url)
                .build();

        hubConnection.on("Disconnect", (id) -> {
            System.out.println("Disconnected:");
            hubConnection.stop();
        }, UUID.class);

        hubConnection.on("Registered", (id) -> {
            System.out.println("Registered with the runner " + id);
            GameWatcherManager.getWatcher().initializePlayer(id);
        }, UUID.class);

        hubConnection.on("ReceiveGameState", GameWatcherManager.getWatcher()::reloadData, GameStateDto.class);
        hubConnection.on("ReceivePlayerConsumed", () -> System.out.println("Player was consumed"));
        hubConnection.on("ReceiveGameComplete", (data) -> System.out.println("Game was finished"), Object.class);

        hubConnection.on("Error", (Throwable::printStackTrace), Throwable.class);
        hubConnection.start().blockingAwait();

        Thread.sleep(1000);
        System.out.println("Registering with the runner...");
        System.out.format("Running as %s...\n", botName);
        hubConnection.send("Register", token, botName);
        // new DebugWriter(botName);

        // This is a blocking call
        hubConnection.start().subscribe(() -> {
            while (hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
                Thread.sleep(20);

                if (GameWatcherManager.getWatcher().player == null || !shouldAct) {
                    continue;
                }
                var agent = AgentManager.getDefaultAgent();

                if (hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
                    try {
                        hubConnection.send("SendPlayerAction", agent.computeNextAction());
                    } catch (Error e) {
                        
                    }

                }

                shouldAct = false;
            }
        });

        hubConnection.stop().blockingAwait();
    }
}
