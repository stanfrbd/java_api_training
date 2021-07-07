package fr.lernejo.navy_battle;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class Game {
    final List<List<int[]>> game_board;
    final boolean[] ingame;
    final Board init_board;
    final ProjectServer server;
    final End shoot;

    final Pattern p = Pattern.compile("^[A-J](10|[1-9])$");

    enum FireResult {miss, hit, sunk, out}

    Game(ProjectServer server) {
        ingame = new boolean[]{false};
        init_board = new Board();
        game_board = init_board.GenerateBoard();
        this.server = server;
        shoot = new End(this);
    }

    public FireResult ShotAt(String cell) throws IOException {
        if (!p.matcher(cell).find()) {
            return FireResult.out;
        }
        int y = (cell.charAt(0) - 'A');
        int x = Integer.parseInt(cell.split("[A-J]")[1]) - 1;
        for (List<int[]> ship_loc : game_board) {
            for (int[] loc : ship_loc) {
                if (loc[0] == x && loc[1] == y) {
                    ship_loc.remove(loc);
                    if (ship_loc.size() == 0) {
                        game_board.remove(ship_loc);
                        return FireResult.sunk;
                    }
                    return FireResult.hit;
                }
            }
        }
        return FireResult.miss;
    }

    public void FireBack() {
        int[] loc = shoot.GetCellToShoot();
        loc[0]++;
        shoot.Shoot(loc);
    }
}
