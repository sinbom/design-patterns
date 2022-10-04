package structural.flyweight;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class FlyweightTest {

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

}
