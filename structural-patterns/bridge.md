# Structural Patterns(구조 관련 패턴)

## Bridge (브릿지 패턴)
> 구현부에서 추상층을 분리하여 각자 독립적으로 변형이 가능하고 확장이 가능하도록 합니다. 
> 즉 기능과 구현에 대해서 별도의 클래스로 분리하여 구현하는 패턴입니다.

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/structural/bridge.jpg?raw=true"/>
</p>

### 구현 방법

```java
public interface Champion {

    void move();

    void q();

    void w();

    void e();

    void r();

}
```

```java
public class DefaultChampion implements Champion {

    private final Skin skin;

    private final String name;

    public DefaultChampion(Skin skin, String name) {
        this.skin = skin;
        this.name = name;
    }

    @Override
    public void move() {
        System.out.printf("%s %s move\n", skin.getName(), this.name);
    }

    @Override
    public void q() {
        System.out.printf("%s %s Q\n", skin.getName(), this.name);
    }

    @Override
    public void w() {
        System.out.printf("%s %s W\n", skin.getName(), this.name);
    }

    @Override
    public void e() {
        System.out.printf("%s %s E\n", skin.getName(), this.name);
    }

    @Override
    public void r() {
        System.out.printf("%s %s R\n", skin.getName(), this.name);
    }

}
```

기능 계층의 인터페이스 및 최상위 클래스(Abstraction)를 정의합니다.

```java
public class Ahri extends DefaultChampion {

    public Ahri(Skin skin) {
        super(skin, "아리");
    }

}
```

```java
public class Akali extends DefaultChampion {

    public Akali(Skin skin) {
        super(skin, "아칼리");
    }

}
```

기능 계층의 새로운 부분을 확장하는 구현체(Refined Abstraction)를 정의합니다.

```java
public interface Skin {

    String getName();

}
```

기능 계층의 구체적인 기능을 정의하는 구현 계층의 인터페이스(Implementation)을 정의합니다.

```java
public class KDA implements Skin{

    @Override
    public String getName() {
        return "KDA";
    }

}
```

```java
public class PoolParty implements Skin{

    @Override
    public String getName() {
        return "PoolParty";
    }

}
```

구현 계층 인터페이스의 구현체(Concrete Implementation)를 정의합니다.

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/structural/bridge-diagram.png?raw=true"/>
</p>

인터페이스와 구현체들을 정의한 다이어그램은 다음과 같습니다.

```java
@Test
public void 추상적인것과_구체적인것을_분리하여_연결한다() {
    Champion kdaAhri = new Ahri(new KDA());
    Champion kdaAkail = new Akali(new KDA());
    Champion poolPartyAhri = new Ahri(new PoolParty());
    Champion poolPartyAkali = new Akali(new PoolParty());

    kdaAhri.move();
    kdaAhri.q();

    poolPartyAhri.move();
    poolPartyAhri.q();

    kdaAkail.move();
    kdaAhri.w();

    poolPartyAkali.move();
    poolPartyAkali.w();
}
```

기능 계층(Abstraction)의 코드를 구체적인 기능(Implementation)의 변경 없이 독립적으로 확장할 수 있지만 계층 구조가 늘어나 복잡도가 증가하게 됩니다.

```java
public class KDAKaisa implements Champion {

    @Override
    public void move() {
        System.out.println("KDA 카이사 move");
    }

    @Override
    public void q() {
        System.out.println("KDA 카이사 Q");
    }

    @Override
    public void w() {
        System.out.println("KDA 카이사 W");
    }

    @Override
    public void e() {
        System.out.println("KDA 카이사 E");
    }

    @Override
    public void r() {
        System.out.println("KDA 카이사 R");
    }

}
```

브릿지 패턴을 사용하지 않으면 새로운 구체적인 기능의 구현체가 추가될 때마다 중복되는 코드를 작성하게 될 수 있고 
구체적인 기능이 수정될 때 수정이 동반될 수 있습니다.


