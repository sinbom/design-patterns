package creational.factory_method;

import creational.factory_method.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertSame;

public class FactoryMethodTest {

    @Test
    public void 일반생성() {
        CargoShip cargoShip = new CargoShip("화물선1호", "black");
        PassengerShip passengerShip = new PassengerShip("여객선1호", "white");
    }

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

}
