package fr.lernejo.navy_battle;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class End {
    final Game game;

    final List<int[]> all_shots = new ArrayList<>();

    public End(Game g) {
        game = g;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                all_shots.add(new int[]{i, j});
            }
        }
    }


    public int[] GetCellToShoot() {
        return all_shots.remove(game.init_board.rand.nextInt(all_shots.size()));
    }

    public Game.FireResult Shoot(int[] loc) {
        String cell = String.format("%s%s", (char) (loc[1] + 'A'), loc[0] + 1);
        if (!game.ingame[0])
            return EndGame();
        try {
            String url = String.format("%s/api/game/fire?cell=%s", game.server.target[0], cell);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest fire_request = HttpRequest.newBuilder()
                .uri(new URI(url)).setHeader("Accept", "application/json").setHeader("Content-Type", "application/json").GET()
                .build();
            HttpResponse<String> response = client.send(fire_request, HttpResponse.BodyHandlers.ofString());
            return game.server.handler.check.ValidateFireRequest(response.body(), game);
        } catch (Exception e) {
            return Game.FireResult.out;
        }
    }

    public Game.FireResult EndGame() {
        game.ingame[0] = false;
        return Game.FireResult.out;
    }
}
