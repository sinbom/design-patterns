package creational.abstract_factory;

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
