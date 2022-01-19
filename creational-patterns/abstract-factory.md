# Creational Patterns(생성 관련 패턴)

## Abstract Factory (추상 팩토리 패턴)
> TODO

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/abstract-factory.jpg?raw=true"/>
</p>

### 구현 방법

```java
public interface ShipFactory {

    Ship of(String name, String color);

}
```

TODO

```java
public class WhiteShipFactory implements ShipFactory {

    private final ShipPartFactory shipPartFactory;

    public WhiteShipFactory(ShipPartFactory shipPartFactory) {
        this.shipPartFactory = shipPartFactory;
    }

    @Override
    public Ship of(String name, String color) {
        Anchor anchor = shipPartFactory.createAnchor();
        Wheel wheel = shipPartFactory.createWheel();

        return new WhiteShip(name, color, anchor, wheel);
    }

}
```

TODO

```java
public interface ShipPartFactory {

    Anchor createAnchor();

    Wheel createWheel();

}
```

TODO

```java
public class WhiteShipPartFactory implements ShipPartFactory {

    @Override
    public Anchor createAnchor() {
        return new WhiteAnchor();
    }

    @Override
    public Wheel createWheel() {
        return new WhiteWheel();
    }

}
```

```java
public class WhiteShipProPartFactory implements ShipPartFactory {

    @Override
    public Anchor createAnchor() {
        return new WhiteProAnchor();
    }

    @Override
    public Wheel createWheel() {
        return new WhiteProWheel();
    }

}
```

TODO

```java
public interface Anchor {
}
```

```java
public interface Wheel {
}
```

TODO

```java
public class WhiteAnchor implements Anchor {
}
```

```java
public class WhiteProAnchor implements Anchor {
}
```

```java
public class WhiteWheel implements Wheel {
}
```

```java
public class WhiteProWheel implements Wheel {
}
```

TODO 

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/abstract-factory-diagram.png?raw=true"/>
</p>

인터페이스 및 추상 클래스와 구현체들을 정의한 다이어그램은 다음과 같습니다.

```java
public class WhiteShipFactory implements ShipFactory {

    @Override
    public Ship of(String name, String color) {
        Anchor anchor = new WhiteAnchor();
        Wheel wheel = new WhiteWheel();

        return new WhiteShip(name, color, anchor, wheel);
    }

}
```

TODO

```java
@ParameterizedTest
@ArgumentsSource(TestArgumentProvider.class)
public void 주입되는_추상_팩토리에따라_생성되는_구현체와_관련되는_객체가_다르다(ShipFactory shipFactory, Class<?> expectedAnchor, Class<?> expectedWheel) {
    // given & when
    WhiteShip whiteShip = (WhiteShip) shipFactory.of("돛단배", "white");

    // then
    assertSame(expectedAnchor, whiteShip.getAnchor().getClass());
    assertSame(expectedWheel, whiteShip.getWheel().getClass());
}

public static class TestArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(new WhiteShipFactory(new WhiteShipPartFactory()), WhiteAnchor.class, WhiteWheel.class),
                Arguments.of(new WhiteShipFactory(new WhiteShipProPartFactory()), WhiteProAnchor.class, WhiteProWheel.class)
        );
    }

}
```

TODO