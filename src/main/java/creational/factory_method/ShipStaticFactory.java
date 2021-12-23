package creational.factory_method;

public class ShipStaticFactory {

    public static Ship of(String name, String color, String type) {
        switch (type) {
            case "cargo":
                return new CargoShip(name, color);
            case "passenger":
                return new PassengerShip(name, color);
            default:
                throw new IllegalArgumentException("제작할 수 없는 배의 유형입니다.");
        }
    }

}
