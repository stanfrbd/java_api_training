package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

class MyHttpClientTest {

    @Test
    void accept() throws IOException, URISyntaxException, InterruptedException {
        MyHttpClient client = new MyHttpClient(new ProjectServer("1234"), "http://localhost:1234");
        client.connect();
    }
}
