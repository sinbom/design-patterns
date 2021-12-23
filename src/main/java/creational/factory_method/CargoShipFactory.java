package creational.factory_method;

public class CargoShipFactory implements ShipFactory {

    @Override
    public Ship of(String name, String color) {
        return new CargoShip(name, color);
    }

}
