# Creational Patterns(생성 관련 패턴)

## Factory Method(팩토리 메소드 패턴)
> 객체 생성의 책임을 팩토리에게 위임하고 생성된 객체를 구체적인 클래스가 아닌 추상적인 인터페이스 타입으로
> 반환하여 클래스간의 결합도를 느슨하게 가져갈 수 있고 객체 생성 로직이 변경되어도 팩토리 내부의 로직에만 
> 영향을 미치기 때문에 변경 사항에 대응하기 유리합니다. 구현체(Product)와 구현체를 생성하는 팩토리(Creator)로 구성됩니다.

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/factory-method.jpg?raw=true"/>
</p>

### 구현 방법

```java
public interface ShipFactory {

    Ship of(String name, String color);

}
```

팩토리 메소드의 네이밍에 사용되는 컨벤션은 다음과 같습니다.
- from : 하나의 매개 변수를 받아서 객체 생성
- of : 여러 개의 매개 변수를 받아서 객체를 생성
- getInstance OR instance : 인스턴스를 생성하거나 기존에 생성된 인스턴스를 반환
- newInstance OR create : 새로운 인스턴스를 생성
- get[type] : 다른 타입의 인스턴스를 생성하거나 기존에 생성된 인스턴스를 반환
- new[type] : 다른 타입의 새로운 인스턴스를 생성

```java
public class ShipStaticFactory {

    public static Ship of(String name, String color, String type) {
        switch (type) {
            case "cargo":
                return new CargoShip(name, color);
            case "passenger":
                return new PassengerShip(name, color);
            default:
                throw new IllegalArgumentException("제작할 수 없는 배의 유형입니다.");
        }
    }

}
```

정적 팩토리 메소드를 사용해 구현체를 생성하는 방식은 새로운 구현체가 생길때마다 새로운 팩토리 클래스를 정의하고 관리할 필요가 없지만
변경사항이 생길때 마다 소스 코드를 수정해서 대응해야 하는 단점이 있으므로 OCP(open closed principal)를
위반하게 됩니다. 각 방법에 따라 장단점이 다르기 때문에 상황에 맞는 적합한 방법을 선택해서 사용하는 것이 좋습니다.

```java
public class CargoShipFactory implements ShipFactory {

    @Override
    public Ship of(String name, String color) {
        return new CargoShip(name, color);
    }

}
```
```java
public class PassengerShipFactory implements ShipFactory{

    @Override
    public Ship of(String name, String color) {
        return new PassengerShip(name, color);
    }

}
```
```java
public class RecommendShipFactory implements ShipFactory {

    @Override
    public Ship of(String name, String color) {
        if ("black".equals(color) || "white".equals(color)) {
            return new PassengerShip(name, color);
        }

        return new CargoShip(name, color);
    }

}
```

팩토리 인터페이스를 구현하는 클래스를 정의하고 각 팩토리 클래스에 맞는 구현체를 생성하도록 오버라이딩합니다. 

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

구현체들이 상속받을 추상 클래스 또는 클래스를 정의합니다.

```java
public class CargoShip extends Ship {

    public CargoShip(String name, String color) {
        super(name, color);
    }

}
```
```java
public class PassengerShip extends Ship {

    public PassengerShip(String name, String color) {
        super(name, color);
    }

}
```

추상 클래스를 상속받는 구현체 클래스를 정의합니다. 

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/factory-method-diagram.png?raw=true"/>
</p>

인터페이스 및 추상 클래스와 구현체들을 정의한 다이어그램은 다음과 같습니다.

```java
CargoShip cargoShip = new CargoShip("화물선1호", "black");
PassengerShip passengerShip = new PassengerShip("여객선1호", "white");
```

직접 구체적인 클래스를 사용하여 객체를 생성하는 코드를 사용하게되면 새로운 구현체가 추가될 때마다 소스 코드의 수정이 필요해지고 클래스간의 결합도가 증가합니다.

```java
@ParameterizedTest
@ArgumentsSource(TestArgumentProvider.class)
public void 주입되는_팩토리에따라_생성되는_구현체가_다르다(ShipFactory shipFactory, Class<?> expected) {
    // given & when
    Ship ship = shipFactory.of("영진호", "black");
        
    // then
    assertSame(expected, ship.getClass());
}

public static class TestArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(new CargoShipFactory(), CargoShip.class),
                Arguments.of(new PassengerShipFactory(), PassengerShip.class)
        );
    }

}
```

반면 팩토리 메소드를 사용하게되면 주입되는 팩토리의 구현체에 따라 생성되는 구현체가 다르지만 추상화된 클래스와 인터페이스를
사용하고 있기 때문에 소스 코드에 영향을 주지 않으며 클래스간의 결합도가 증가하지 않습니다.

### 사용 사례

1. java.util.Calendar

2. java.time.LocalDate, LocalTime, LocalDateTime

3. org.springframework.beans.factory.BeanFactory