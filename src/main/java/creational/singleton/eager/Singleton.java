package creational.singleton.eager;

public class Singleton {

    private static final Singleton INSTANCE = new Singleton();

    private Singleton() {
    }

    public static synchronized Singleton getInstance() {
        return INSTANCE;
    }

}
