package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProjectServer {

    final int port;
    final String url;
    final HttpServer server;
    final String serverID;
    final RequestHandler handler;
    final Game game;
    final String[] target;

    void generateHtml(HttpExchange exchange, int error) throws IOException {
        String body = String.format("<h1>%s</h1>", error);
        exchange.sendResponseHeaders(error, body.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    }

    final HttpHandler pingResponse = exchange -> {
        String body = "OK";
        exchange.sendResponseHeaders(200, body.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    };

    final HttpHandler shotResponse = new HttpHandler() {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!exchange.getRequestMethod().equals("GET")) {
                generateHtml(exchange, 404);
                return;
            }
            handler.FireHandler(exchange, false);
        }
    };

    final HttpHandler startResponse = new HttpHandler() {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (game.ingame[0] || !exchange.getRequestMethod().equals("POST")) {
                generateHtml(exchange, 404);
                return;
            }
            try {
                handler.StartHandler(exchange, false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    final HttpHandler defaultResponse = exchange -> generateHtml(exchange, 404);

    ProjectServer(String port) throws IOException {
        this.port = Integer.parseInt(port);
        url = String.format("http://localhost:%s", port);
        Executor singlethread = Executors.newFixedThreadPool(1);
        server = HttpServer.create(new InetSocketAddress(this.port), 50);
        server.setExecutor(singlethread);
        server.createContext("/ping", this.pingResponse);
        server.createContext("/api/game/start", this.startResponse);
        server.createContext("/api/game/fire", this.shotResponse);
        server.createContext("/", this.defaultResponse);
        this.serverID = UUID.randomUUID().toString();
        handler = new RequestHandler(this);
        server.start();
        game = new Game(this);
        target = new String[]{""};
    }
}
