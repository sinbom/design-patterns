# Structural Patterns(구조 관련 패턴)

## Flyweight (플라이웨이트 패턴)
> 자주 변하는 속성(extrinsit)과 변하지 않는 속성(intrinsit)을 분리하고 재사용하여 메모리 사용을 줄일 수 있는 패턴이다.

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/structural/flyweight.png?raw=true"/>
</p>

### 구현 방법

```java
public class Character {

    private char value;

    private String color;

    private Font font;

    public Character(char value, String color, Font font) {
        this.value = value;
        this.color = color;
        this.font = font;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

}
```

자주 변하는 속성(extrinsit)들을 가진 클래스를 정의합니다.

```java
public final class Font {

    private final String family;

    private final int size;

    public Font(String family, int size) {
        this.family = family;
        this.size = size;
    }

    public String getFamily() {
        return family;
    }

    public int getSize() {
        return size;
    }

}
```

자주 변하지 않는 속성(intrinsit)들을 가진 flyweight 클래스 객체는 공유 및 재사용될 수 있기 때문에 thread-safe한 immutable 클래스로 정의합니다.

```java
public class FontFactory {

    private final Map<String, Font> cache = new HashMap<>();

    public Font getFont(String key) {
        Font font = cache.get(key);

        if (font != null) {
            return font;
        }

        String[] split = key.split(":");
        font = new Font(split[0], Integer.parseInt(split[1]));

        cache.put(split[0], font);

        return font;
    }

}
```

동일한 속성을 가지는 flyweigth 객체를 캐시하는 FlyweightFactory 클래스를 정의합니다. 캐시된 객체가 없다면 생성하여 리런하고 생성한 객체를 캐시합니다.

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/structural/flyweight-diagram.png?raw=true"/>
</p>

클래스들을 정의한 다이어그램은 다음과 같습니다.

```java
@Test
void 동일한_속성을_가지는_객체를_캐시하여_재사용한다() {
    FontFactory fontFactory = new FontFactory();

    Font f1 = fontFactory.getFont("nanum:12");
    Character c1 = new Character('h', "white", f1);

    Font f2 = fontFactory.getFont("nanum:12");
    Character c2 = new Character('h', "white", f2);

    Font f3 = fontFactory.getFont("nanum:12");
    Character c3 = new Character('h', "white", f3);

    assertSame(f1, f2);
    assertSame(f2, f3);
    assertSame(f1, f3);
}
```

동일한 속성을 가지는 flyweight 객체는 Flyweight Factory를 통해 캐시된 객체를 재사용합니다. singleton 패턴과 비교하면 애플리케이션에서 유일한 하나의 객체가 아닌 동일한 속성을 가진 객체만을 재사용하는 점에서 차이가 있습니다.
