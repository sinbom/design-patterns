package structural.proxy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProxyTest {

    private ByteArrayOutputStream byteArrayOutputStream;

    private PrintStream printStream;

    @BeforeEach
    void beforeEach() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);
    }

    @AfterEach
    void afterEach() throws IOException {
        printStream.close();
        byteArrayOutputStream.close();
        System.setOut(System.out);
    }

    @Test
    void 원본객체의_코드를_수정하지않고_접근을_제어하거나__기능을_추가한다() {
        //given
        GameService gameService = new GameServiceProxy(new DefaultGameService());

        // when
        gameService.startGame();

        // then
        assertTrue(byteArrayOutputStream.toString().startsWith("프록시님 등장!"));
    }

}
