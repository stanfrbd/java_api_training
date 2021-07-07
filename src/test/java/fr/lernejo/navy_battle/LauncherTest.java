package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

class LauncherTest {
    @Test
    void correct_argument() {
        new Launcher();
        try {
            String[] arg = new String[]{"9999"};
            Launcher.main(arg);

            arg = new String[]{"9998", "http://localhost:9999"};

            Launcher.main(arg);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
            Assertions.assertEquals(0, 1, "Exception");
        }
        Assertions.assertEquals(0, 0, "Exception");
    }

    @Test
    void wrong_argument() {
        try {
            String[] arg = new String[]{"##"};
            Launcher.main(arg);
            Assertions.assertEquals(1, 0, "Exception");
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals(1, 1, "IllegalArgumentException");
        } catch (IOException | InterruptedException | URISyntaxException e) {
            Assertions.assertEquals(1, 0, "IllegalArgumentException");
        }
        ;

        try {
            String[] arg = new String[]{"2222", "Never gonna give you up"};
            Launcher.main(arg);
            Assertions.assertEquals(1, 0, "Exception");
        } catch (URISyntaxException e) {
            Assertions.assertEquals(1, 1, "URISyntaxException");
        } catch (IOException | InterruptedException e) {
            Assertions.assertEquals(1, 0, "URISyntaxException");
        }
    }
}
