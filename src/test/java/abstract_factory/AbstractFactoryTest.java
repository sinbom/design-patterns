package abstract_factory;

import creational.abstract_factory.*;
import creational.factory_method.ShipFactory;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertSame;

public class AbstractFactoryTest {

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

}
