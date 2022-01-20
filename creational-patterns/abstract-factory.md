# Creational Patterns(생성 관련 패턴)

## Abstract Factory (추상 팩토리 패턴)
> 객체 생성의 책임을 위임한 팩토리 메소드 패턴에서 구현체와 관련된 객체들을 생성하는 책임을
> 추상 팩토리에게 위임하고 구체적인 클래스가 아닌 추상적인 인터페이스 타입으로 반환하고 사용하여
> 클래스간의 결합도를 느슨하게 가져갈 수 있습니다.

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/abstract-factory.jpg?raw=true"/>
</p>

### 구현 방법

```java
public interface ShipFactory {

    Ship of(String name, String color);

}
```

팩토리 인터페이스를 정의합니다. 메소드의 네이밍에 사용되는 컨벤션은 다음과 같습니다.
- from : 하나의 매개 변수를 받아서 객체 생성
- of : 여러 개의 매개 변수를 받아서 객체를 생성
- getInstance OR instance : 인스턴스를 생성하거나 기존에 생성된 인스턴스를 반환
- newInstance OR create : 새로운 인스턴스를 생성
- get[type] : 다른 타입의 인스턴스를 생성하거나 기존에 생성된 인스턴스를 반환
- new[type] : 다른 타입의 새로운 인스턴스를 생성

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

팩토리 인터페이스를 구현하는 클래스를 정의하고 각 팩토리 클래스가 생성하는 구현체에 맞는 관련 객체를 
추상 팩토리 클래스로 생성하고 사용하여 구현체를 생성하도록 오버라이딩합니다.

```java
public interface ShipPartFactory {

    Anchor createAnchor();

    Wheel createWheel();

}
```

팩토리 인터페이스가 생성하는 구현체와 관련된 객체를 생성하는 추상 팩토리 인터페이스를 정의합니다.

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

추상 팩토리 인터페이스를 구현하는 클래스를 정의하고 팩토리 인터페이스가 생성하는 구현체와 관련된 객체를 생성하도록 오버라이딩합니다.

```java
public abstract class Ship {

    private final String name;

    private final String color;

    public Ship(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

}
```

팩토리 메소드가 생성하는 구현체들이 상속받을 추상 클래스 또는 인터페이스를 정의합니다.

```java
public class WhiteShip extends Ship {

    private final Anchor anchor;

    private final Wheel wheel;

    public WhiteShip(String name, String color, Anchor anchor, Wheel wheel) {
        super(name, color);
        this.anchor = anchor;
        this.wheel = wheel;
    }

    public Anchor getAnchor() {
        return anchor;
    }

    public Wheel getWheel() {
        return wheel;
    }

}
```

팩토리 메소드가 생성하는 구현체 클래스를 정의합니다.  


```java
public interface Anchor {
}
```

```java
public interface Wheel {
}
```

팩토리 메소드가 생성하는 구현체와 관련된 추상 팩토리가 생성하는 객체들이 상속받을 인터페이스를 정의합니다.

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

추상 팩토리가 생성하는 구현체 클래스를 정의합니다.

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

추상 팩토리를 사용하지 않고 팩토리 메소드가 생성하는 구현체를 생성하는 과정에서 구체적인 클래스를 사용하여 관련 객체를 생성하게 되면 
새로운 구현체와 관련된 객체가 추가될 때마다 소스 코드의 수정이 필요해지고 클래스와의 결합도가 증가합니다.

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

반면 추상 팩토리를 사용하게되면 주입되는 추상 팩토리의 구현체에 따라 생성되는 구현체와 관련된 객체가 다르지만 추상화된 클래스와 인터페이스를
사용하고 있기 때문에 소스 코드에 영향을 주지 않으며 클래스간의 결합도가 증가하지 않습니다.

### 팩토리 메소드와의 차이점

두 패턴 모두 구체적인 객체 생성 과정을 추상화한다는 점에서 동일하지만 팩토리 메소드 패턴은 팩토리를 구현하는 방법에 초점을 두고 구체적인 객체 생성 과정을 구현체에게 맡기는 것이 목적이지만, 
추상 팩토리 패턴은 팩토리 메소드를 사용하는 방법에 초점을 두고 구체적인 객체를 생성하는 과정에서 관련된 여러 객체를 구체적인 클래스에 의존하지 않고 생성할 수 있게 해주는 것이 목적입니다.



