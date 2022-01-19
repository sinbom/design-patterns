package creational.abstract_factory;

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
