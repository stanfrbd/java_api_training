package fr.lernejo.navy_battle;

import org.apache.commons.validator.routines.UrlValidator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MyHttpClient {

    final ProjectServer server;
    final String url;

    MyHttpClient(ProjectServer s, String url) throws URISyntaxException {
        server = s;
        UrlValidator urlValidator = new UrlValidator();
        urlValidator.isValid(url);
        this.url = url;
    }


    public boolean connect() throws URISyntaxException, IOException, InterruptedException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest post_request = HttpRequest.newBuilder().uri(new URI(url + "/api/game/start")).setHeader("Accept", "application/json")
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(String.format("{\"id\": \"%s\",\"url\": \"%s\",\"message\": \"%s\"}", server.serverID, server.url, "Trying to connect")))
                .build();
            client.send(post_request, HttpResponse.BodyHandlers.ofString());
            server.target[0] = url;
            server.game.ingame[0] = true;
        } catch (URISyntaxException | InterruptedException | IOException error) {
            throw error;
        }
        return true;
    }

}
