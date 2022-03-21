package structural.bridge;

public class DefaultChampion implements Champion {

    private final Skin skin;

    private final String name;

    public DefaultChampion(Skin skin, String name) {
        this.skin = skin;
        this.name = name;
    }

    @Override
    public void move() {
        System.out.printf("%s %s move\n", skin.getName(), this.name);
    }

    @Override
    public void q() {
        System.out.printf("%s %s Q\n", skin.getName(), this.name);
    }

    @Override
    public void w() {
        System.out.printf("%s %s W\n", skin.getName(), this.name);
    }

    @Override
    public void e() {
        System.out.printf("%s %s E\n", skin.getName(), this.name);
    }

    @Override
    public void r() {
        System.out.printf("%s %s R\n", skin.getName(), this.name);
    }

}
