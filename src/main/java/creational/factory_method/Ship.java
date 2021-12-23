package creational.factory_method;

public abstract class Ship {

    private final String name;

    private final String color;

    public Ship(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

}
