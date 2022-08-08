# Structural Patterns(구조 관련 패턴)

## Composite (컴포짓 패턴)
> 객체들의 관계를 트리 구조로 구성하여 부분-전체 계층을 표현하여 단일 객체와 복합 객체 모두 동일하게 다루는 패턴입니다.

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/structural/composite.png?raw=true"/>
</p>

### 구현 방법

```java
public interface Component {

    int getPrice();

}
```

단일 및 복합 구현체가 구현할 공통 메소드를 가진 인터페이스를 정의합니다.

```java
public class Item implements Component {

    private final String name;

    private final int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

}
```

```java
public class Bag implements Component {

    private final List<Component> components = new ArrayList<>();

    public void add(Component component) {
        components.add(component);
    }

    public List<Component> getComponents() {
        return components;
    }

    @Override
    public int getPrice() {
        return components
            .stream()
            .mapToInt(Component::getPrice)
            .sum();
    }

}
```

인터페이스를 구현하는 단일 및 복합 구현체를 정의합니다. 복합 구현체는 인터페이스 타입의 객체를 리스트 형태로 위임하고 있습니다. 

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/structural/composite-diagram.png?raw=true"/>
</p>

인터페이스와 구현체들을 정의한 다이어그램은 다음과 같습니다.

```java
@Test
void 단일객체와_복합객체를_동일하게_다룰수있다() {
    Item doranBlade = new Item("도란검", 450);
    Item healPotion = new Item("체력 물약", 50);
    int totalPrice = doranBlade.getPrice() + healPotion.getPrice();

    Bag bag = new Bag();

    bag.add(doranBlade);
    bag.add(healPotion);

    assertEquals(totalPrice, bag.getPrice());
}
```

클라이언트는 다형성과 재귀를 통해 구현체가 단일, 복합 객체인지 구분할 필요없이 동일하게 사용할 수 있고, 새로운 구현체가 추가되어도 코드를 변경하지 않을 수 있습니다.

