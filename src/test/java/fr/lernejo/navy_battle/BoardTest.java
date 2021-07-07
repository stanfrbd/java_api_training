package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void correct_location() {
        Board test = new Board();
        List<List<int[]>> ships = new ArrayList<>();
        List<int[]> temp = new ArrayList<>();
        temp.add(new int[]{0, 0});
        ships.add(temp);
        assertTrue(test.CorrectLocation(ships, 5, 5, 0, 2), "ship is on the board");
        assertTrue(test.CorrectLocation(ships, 5, 5, 1, 2), "ship is on the board");
        assertTrue(test.CorrectLocation(ships, 0, 3, 1, 2), "ship is on the board");
        assertTrue(test.CorrectLocation(ships, 3, 0, 0, 2), "ship is on the board");
        assertFalse(test.CorrectLocation(ships, 2, 0, 1, 2), "ship is not on the board");
        assertFalse(test.CorrectLocation(ships, 0, 2, 0, 2), "ship is not on the board");
        assertFalse(test.CorrectLocation(ships, 0, 1, 1, 2), "ship is not on the board");
        assertFalse(test.CorrectLocation(ships, 1, 0, 0, 2), "ship is not on the board");
        assertFalse(test.CorrectLocation(ships, 0, 0, 1, 1), "ship is not on the board");
        assertFalse(test.CorrectLocation(ships, 0, 0, 0, 1), "ship is not on the board");
    }

    @Test
    void location() {
        Board test = new Board();
        List<List<int[]>> ships = new ArrayList<>();
        test.PlaceShip(ships, 2, 0, 1, 2);
        test.PlaceShip(ships, 0, 2, 0, 2);
    }


    @Test
    void generate_game_board() {
        Board test = new Board();
        for (int i = 0; i < 25; i++) {
            int k = 0;
            List<List<int[]>> ships = test.GenerateBoard();
            for (List<int[]> ship_loc : ships) {
                for (int[] ignored : ship_loc) {
                    k++;
                }
            }
            Assertions.assertEquals(17, k, "Number of filled cell");
        }
    }
}
