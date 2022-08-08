package creational.singleton;

import creational.singleton.inner.Singleton;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertNotSame;

public class SingletonTest {

    @Test
    public void 싱글톤이_리플랙션에의해_싱글톤이_보장되지않을수있다() throws Exception {
        // given
        Singleton instance = Singleton.getInstance();

        // when
        Constructor<Singleton> constructor = Singleton.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Singleton reflection = constructor.newInstance();

        // then
        assertNotSame(instance, reflection);
    }

    @Test
    public void 직렬화_및_역직렬화에의해_싱글톤이_보장되지않을수있다() throws Exception {
        // given
        Singleton instance = Singleton.getInstance();
        Singleton deserialization;

        // when
        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream("singleton.obj"))) {
            out.writeObject(instance);
        }
        try (ObjectInput in = new ObjectInputStream(new FileInputStream("singleton.obj"))) {
            deserialization = (Singleton) in.readObject();
        }

        // then
        assertNotSame(instance, deserialization);
    }

}
