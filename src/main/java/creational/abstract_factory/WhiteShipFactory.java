package creational.abstract_factory;

import creational.factory_method.Ship;
import creational.factory_method.ShipFactory;

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
