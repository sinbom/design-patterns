package creational.abstract_factory;

import creational.factory_method.Ship;

public class WhiteShip extends Ship {

    private final Anchor anchor;

    private final Wheel wheel;

    public WhiteShip(String name, String color, Anchor anchor, Wheel wheel) {
        super(name, color);
        this.anchor = anchor;
        this.wheel = wheel;
    }

    public Anchor getAnchor() {
        return anchor;
    }

    public Wheel getWheel() {
        return wheel;
    }

}
