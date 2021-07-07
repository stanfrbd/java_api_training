package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckerTest {

    @Test
    void start_request() {
        Checker check = new Checker();
    }

    @Test
    void bad_startRequest() {
        Checker check = new Checker();
        try {
            Assertions.assertEquals(false, check.ValidateStartRequest("{\"id\": \"2aca7611-0ae4-49f3-bf63-75bef4769028\",\"url\": 90,\"message\": \"May the best code win\"}"), "Got a bad request");
            Assertions.assertEquals(1, 0, "org.everit.json.schema.ValidationException");
        } catch (org.everit.json.schema.ValidationException e) {
            Assertions.assertEquals(1, 1, "org.everit.json.schema.ValidationException");
        }
        try {
            Assertions.assertEquals(false, check.ValidateStartRequest("{\"id\": \"2aca7611-0ae4-49f3-bf63-75bef4769028\",\"message\": \"May the best code win\"}"), "Got a bad request");
            Assertions.assertEquals(1, 0, "org.everit.json.schema.ValidationException");
        } catch (org.everit.json.schema.ValidationException e) {
            Assertions.assertEquals(1, 1, "org.everit.json.schema.ValidationException");
        }
    }

    @Test
    void bad_request() {
        Checker check = new Checker();
        Game g = new Game(null);
        try {
            Assertions.assertEquals(Game.FireResult.out, check.ValidateFireRequest("{\"consequence\": \"wtf\",\"shipLeft\": true}", g), "Got a bad request");
            Assertions.assertEquals(1, 0, "org.everit.json.schema.ValidationException");
        } catch (Exception e) {
        }
        try {
            Assertions.assertEquals(Game.FireResult.out, check.ValidateFireRequest("{\"shipLeft\": true}", g), "Got a bad request");
            Assertions.assertEquals(1, 0, "org.everit.json.schema.ValidationException");
        } catch (Exception e) {

        }
    }

    @Test
    void correct_request() {
        Checker check = new Checker();
        Game g = new Game(null);
        Assertions.assertEquals(Game.FireResult.sunk, check.ValidateFireRequest("{\"consequence\": \"sunk\",\"shipLeft\": true}", g), "Got a correct request");
        Assertions.assertEquals(Game.FireResult.hit, check.ValidateFireRequest("{\"consequence\": \"hit\",\"shipLeft\": true}", g), "Got a correct request");
        Assertions.assertEquals(Game.FireResult.sunk, check.ValidateFireRequest("{\"consequence\": \"sunk\",\"shipLeft\": false}", g), "Got a correct request");
        Assertions.assertEquals(g.ingame[0], false, "Game Should have ended");

    }

}
