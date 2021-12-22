package creational.singleton.inner;

import java.io.Serializable;

/**
 * eager initialization
 */
public class Singleton implements Serializable {

    private Singleton() {
    }

    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
