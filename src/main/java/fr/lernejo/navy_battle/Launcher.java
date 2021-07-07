package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.URISyntaxException;

public class Launcher {

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        ProjectServer server = null;
        try {
            server = new ProjectServer(args[0]);
            if (args.length > 1) {
                MyHttpClient client = new MyHttpClient(server, args[1]);
                client.connect();
            }
        } catch (IOException | URISyntaxException error) {
            throw error;
        }
    }
}
