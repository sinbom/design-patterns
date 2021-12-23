package creational.factory_method;

public class PassengerShipFactory implements ShipFactory{

    @Override
    public Ship of(String name, String color) {
        return new PassengerShip(name, color);
    }

}
