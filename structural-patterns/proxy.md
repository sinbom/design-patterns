# Structural Patterns(구조 관련 패턴)

## Proxy (프록시 패턴)
> 객체를 직접적으로 참조하는 것이 아닌 프록시 객체를 통해 접근하여 객체에 대한 접근을 제어하거나 기능을 추가할 수 있고, 생성 비용이 큰 객체를 실제로 사용하는 시점에 생성 및 초기화할 수 있는 패턴이다.

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/structural/proxy.png?raw=true"/>
</p>

### 구현 방법

```java
public interface GameService {

    void startGame();

}
```

객체와 프록시 객체가 구현할 인터페이스를 정의합니다.

```java
public class DefaultGameService implements GameService {

    @Override
    public void startGame() {
        System.out.println("이 자리에 오신 여러분을 진심으로 환영합니다.");
    }

}
```

인터페이스의 구현체를 정의합니다.

```java
public class GameServiceProxy implements GameService {

    private final GameService gameService;

    public GameServiceProxy(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void startGame() {
        System.out.println("프록시님 등장!");
        long start = System.currentTimeMillis();

        this.gameService.startGame();

        System.out.println(System.currentTimeMillis() - start);
    }

}
```

동일한 인터페이스를 구현하는 프록시 구현체를 정의합니다. 프록시에서 객체의 접근을 제어하거나 기능을 추가합니다.

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/structural/proxy-diagram.png?raw=true"/>
</p>

클래스들을 정의한 다이어그램은 다음과 같습니다.

```java
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
```

코드 수정 없이 프록시 객체를 통해 접근을 제어하거나 기능을 추가할 수 있고 인터페이스를 통해 접근함으로써 불필요한 의존성이 증가하지 않습니다.

```java
public class GameServiceProxy implements GameService {

    private GameService gameService;

    @Override
    public void startGame() {
        System.out.println("프록시님 등장!");
        long start = System.currentTimeMillis();

        if (this.gameService == null) {
            this.gameService = new DefaultGameService();
        }

        this.gameService.startGame();

        System.out.println(System.currentTimeMillis() - start);
    }

}
```

객체 생성 비용이 큰 경우에는 lazy하게 객체를 생성하여 불필요한 리소스를 감소시킬 수 있습니다.

