package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class RequestHandler {

    final ProjectServer server;
    final Checker check;

    RequestHandler(ProjectServer s) {
        server = s;
        check = new Checker();
    }

    String GetBodyRequest(HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        int b;
        StringBuilder buf = new StringBuilder(512);
        while ((b = br.read()) != -1) {
            buf.append((char) b);
        }
        br.close();
        isr.close();
        return buf.toString();
    }

    public void FireHandler(HttpExchange exchange, boolean test) throws IOException {
        String cell = "";
        try {
            cell = (String) exchange.getRequestURI().getQuery().split("cell=")[1];
            Game.FireResult f = server.game.ShotAt(cell);
            String bodyresponse = String.format("{\"consequence\": \"%s\",\"shipLeft\": %s}", f.toString(), (server.game.game_board.size() > 0) && (server.game.ingame[0]));
            if (!test)
                exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(202, bodyresponse.length());
            try (
                OutputStream os = exchange.getResponseBody()) {
                os.write(bodyresponse.getBytes());
            }
            server.game.FireBack();
        } catch (Exception e) {
            server.generateHtml(exchange, 400);
        }
    }

    public void StartHandler(HttpExchange exchange, boolean test) throws IOException, InterruptedException {
        try {
            String body = GetBodyRequest(exchange);
            String server_url = check.ValidateStartRequest(body);
            server.target[0] = server_url;
        } catch (Exception e) {
            server.generateHtml(exchange, 400);
        }
        String bodyresponse = String.format("{\"id\": \"%s\",\"url\": \"%s\",\"message\": \"%s\"}", server.serverID, server.url, "OK");
        exchange.sendResponseHeaders(202, bodyresponse.length());
        if (!test)
            exchange.getResponseHeaders().add("Content-Type", "application/json");
        try (
            OutputStream os = exchange.getResponseBody()) {
            os.write(bodyresponse.getBytes());
        }
        server.game.ingame[0] = true;
        server.game.FireBack();
    }
}
