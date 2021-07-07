package fr.lernejo.navy_battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

    final int[] all_ships = new int[]{5, 4, 3, 3, 2};
    final Random rand;

    Board() {
        rand = new Random();
    }

    public boolean CorrectLocation(List<List<int[]>> ships, int x, int y, int orientation, int shipsize) {
        if (orientation == 0) {
            if (x - shipsize < 0) {
                return false;
            }
            for (List<int[]> ship_loc : ships) {
                for (int[] loc : ship_loc) {
                    for (int i = 0; i < shipsize; i++) {
                        if (loc[0] == (x - i) && loc[1] == y)
                            return false;
                    }
                }
            }
        } else {
            if (y - shipsize < 0) {
                return false;
            }
            for (List<int[]> ship_loc : ships) {
                for (int[] loc : ship_loc) {
                    for (int i = 0; i < shipsize; i++) {
                        if (loc[0] == x && loc[1] == (y - i))
                            return false;
                    }
                }
            }
        }
        return true;
    }

    public void PlaceShip(List<List<int[]>> ships, int x, int y, int orientation, int shipsize) {
        List<int[]> ship_loc = new ArrayList<>();
        if (orientation == 0) {
            for (int i = 0; i < shipsize; i++)
                ship_loc.add(new int[]{x - i, y});
        } else {
            for (int i = 0; i < shipsize; i++)
                ship_loc.add(new int[]{x, y - i});
        }
        ships.add(ship_loc);
    }

    public List<List<int[]>> GenerateBoard() {
        List<List<int[]>> ret = new ArrayList<>();
        for (int sizeship : all_ships) {
            boolean correctplacement = false;
            while (!correctplacement) {
                int x = rand.nextInt(10);
                int y = rand.nextInt(10);
                int orientation = rand.nextInt(2);
                correctplacement = CorrectLocation(ret, x, y, orientation, sizeship);
                if (correctplacement) {
                    PlaceShip(ret, x, y, orientation, sizeship);
                }
            }
        }
        return ret;
    }
}
