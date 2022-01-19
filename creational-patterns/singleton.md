# Creational Patterns(생성 관련 패턴)

## Singleton(싱글톤 패턴)
> 애플리케이션에서 여러 개의 인스턴스를 생성할 필요가 없는 경우 1개의 인스턴스만을 생성하도록 설계하고 생성한 객체를 재사용합니다.

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/singleton.jpg?raw=true"/>
</p>

### 구현 방법
1. a private constructor with static method
```java
public class Singleton {

    private static Singleton instance;

    private Singleton() { }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }

        return instance;
    }

}
```

싱글 쓰레드 환경에서 사용할 수 있는 가장 기본적인 싱글 패턴의 구현입니다. 멀티 쓰레드 환경에서는
객체 생성 여부를 평가하는 분기문을 여러 개의 쓰레드가 통과할 수 있기 때문에 thread-safe하지 않으므로
하나의 객체만을 사용하는 것을 보장할 수 없습니다.

2. a private constructor with static synchronized method 
```java
public class Singleton {

    private static Singleton instance;

    private Singleton() {
    }

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }

        return instance;
    }

}
```

멀티 쓰레드 환경에서 사용할 수 있는 가장 기본적인 싱글톤 패턴의 구현입니다. 메소드에 synchronized 키워드를
사용하여 동시에 여러 개의 쓰레드가 접근할 수 없도록 동기화 합니다. static 메소드에서
락을 획득했기 때문에 해당 메소드가 속한 클래스의 Class 객체를 대상으로 모니터링 락을 획득합니다.
메소드가 호출될 때마다 동기화 처리에 필요한 비용이 들기 때문에 좋은 방법이라고 볼 수 없습니다.

3. eager initialization
```java
public class Singleton {

    private static final Singleton INSTANCE = new Singleton();

    private Singleton() {
    }

    public static Singleton getInstance() {
        return INSTANCE;
    }

}
```

클래스 로더에 의해 로드되고 초기화 되는 시점에 객체를 생성하여 이후 객체가 생성되는 것을 방지합니다.
synchronized를 사용하지 않아 동기화에 필요한 비용이 들지 않지만, 객체를 생성하는데 비용이 많이 필요한 경우에는
lazy하게 객체 생성을 사용할 수 없기 때문에 불필요한 리소스를 낭비하게 될 수 있습니다.

4. double checked locking
```java
public class Singleton {

    private static volatile Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }

}
```

객체가 생성되지 않았을 때, 객체 생성 코드 라인에 대해서는 동기화 처리를 하지만 이미 생성한 경우에는 
더 이상 동기화 블럭을 실행하지 않기 때문에 객체를 lazy하게 생성하면서도 메소드를 매번 호출할 때마다 
동기화 처리에 사용되는 불필요한 비용을 낭비하지 않는 방법입니다. 하지만 volatile 변수를 사용해야
thread-safe를 보장할 수 있으며 JDK 1.5 이상을 사용하는 경우에만 이 방법을 사용할 수 있습니다.

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/multi-thread-cpu.png?raw=true"/>
</p>

애플리케이션이 두 개 이상의 CPU를 가진 컴퓨팅 환경에서 실행되고 있다면 멀티 쓰레드 환경에서 접근하는 
CPU cache가 서로 다를 수 있기 때문에 동일한 메모리에서 read/write를 수행하지 않을 수 있고,
CPU cache에 write한 값이 언제 메인 메모리에 write될지 알 수 없기 때문에 가시성 문제가 발생합니다.
volatile 키워드가 지정된 변수는 메인 메모리에 read/write하여 멀티 프로세싱 환경에서도
서로 다른 쓰레드들이 동일한 메모리를 참조할 수 있도록 하여 가시성 문제를 해결합니다.

5. static inner class
```java
public class Singleton {

    private Singleton() {
    }

    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
```

inner class가 접근되는 시점에서 클래스 로더에 의해 lazy하게 로드되는 점을 사용하여 동기화 처리를 하지 않으면서도 
필요한 경우에만 단 하나의 객체를 lazy하게 생성할 수 있는 방법입니다. 이 방법은 이전 까지의 모든 방법들에 비해서
가장 효율적으로 멀티 쓰레드 환경에서도 싱글톤을 보장할 수 있는 방법이지만 몇 가지 정상적이지 않은 사용에 의해서
싱글톤의 보장이 깨질 수 있습니다.

```java
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
```

첫 번째는 리플렉션으로 객체를 생성하는 경우입니다. 생성자의 접근 제한자가 private일지라도 리플렉션을 사용하면 접근이 가능하며
새로운 객체를 생성할 수 있습니다. 리플렉션을 사용한 객체 생성은 막을 수 있는 방법이 없습니다.

```java
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
```

두 번째는 클래스가 serializable 인터페이스를 구현한 경우에는 객체를 바이트 형태의 데이터로 변환하는 직렬화를 수행할 수 있게 되는데 직렬화된 데이터를
다시 객체로 변환하는 역직렬화를 수행하는 과정에서 해당 클래스의 readResolve 메소드를 호출하게되고 해당 메소드에서 기본 생성자를 호출하여
새로운 객체를 생성하게 됩니다. 

```java
// class version 52.0 (52)
// access flags 0x21
public class creational/singleton/inner/Singleton implements java/io/Serializable {

  // compiled from: Singleton.java
  // access flags 0x1008
  static synthetic INNERCLASS creational/singleton/inner/Singleton$1 null null
  // access flags 0xA
  private static INNERCLASS creational/singleton/inner/Singleton$SingletonHolder creational/singleton/inner/Singleton SingletonHolder

  // access flags 0x2
  private <init>()V
   L0
    LINENUMBER 10 L0
    ALOAD 0
    INVOKESPECIAL java/lang/Object.<init> ()V
   L1
    LINENUMBER 11 L1
    RETURN
   L2
    LOCALVARIABLE this Lcreational/singleton/inner/Singleton; L0 L2 0
    MAXSTACK = 1
    MAXLOCALS = 1

  // access flags 0x9
  public static getInstance()Lcreational/singleton/inner/Singleton;
   L0
    LINENUMBER 18 L0
    INVOKESTATIC creational/singleton/inner/Singleton$SingletonHolder.access$100 ()Lcreational/singleton/inner/Singleton;
    ARETURN
    MAXSTACK = 1
    MAXLOCALS = 0

  // access flags 0x4
  protected readResolve()Ljava/lang/Object;
   L0
    LINENUMBER 22 L0
    INVOKESTATIC creational/singleton/inner/Singleton$SingletonHolder.access$100 ()Lcreational/singleton/inner/Singleton;
    ARETURN
   L1
    LOCALVARIABLE this Lcreational/singleton/inner/Singleton; L0 L1 0
    MAXSTACK = 1
    MAXLOCALS = 1

  // access flags 0x1000
  synthetic <init>(Lcreational/singleton/inner/Singleton$1;)V
   L0
    LINENUMBER 8 L0
    ALOAD 0
    INVOKESPECIAL creational/singleton/inner/Singleton.<init> ()V
    RETURN
   L1
    LOCALVARIABLE this Lcreational/singleton/inner/Singleton; L0 L1 0
    LOCALVARIABLE x0 Lcreational/singleton/inner/Singleton$1; L0 L1 1
    MAXSTACK = 1
    MAXLOCALS = 2
}

```

Serializable 인터페이스에 readResolve 메소드가 정의되어 있지 않지만 구현체의 바이트 코드를 살펴보면 readResolve 메소드가 존재 합니다.

```java
public class Singleton implements Serializable {

    private Singleton() {
    }

    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return SingletonHolder.INSTANCE;
    }

}
```

기본 생성자를 호출하는 readResolve를 사용하지 않고 싱글톤 객체를 반환하는 메소드를 직접 정의하게 되면 역직렬화를 
수행하더라도 싱글톤을 보장할 수 있습니다.



6. Enum
```java
public enum Singleton {

    INSTANCE

}
```

Enum을 사용하면 하나의 객체를 생성하기 때문에 가장 간단한 방법으로 싱글톤 객체를 사용할 수 있습니다. 또한 리플렉션을 통한 객체 생성이 불가능하고
직렬화 및 역직렬화에 대해서도 기본적으로 싱글톤을 보장합니다.

```java
@CallerSensitive
public T newInstance(Object ... initargs)
    throws InstantiationException, IllegalAccessException,
    IllegalArgumentException, InvocationTargetException
{
    if (!override) {
        if (!Reflection.quickCheckMemberAccess(clazz, modifiers)) {
            Class<?> caller = Reflection.getCallerClass();
            checkAccess(caller, clazz, null, modifiers);
        }
    }
    if ((clazz.getModifiers() & Modifier.ENUM) != 0)
        throw new IllegalArgumentException("Cannot reflectively create enum objects");
    ConstructorAccessor ca = constructorAccessor;   // read volatile
    if (ca == null) {
        ca = acquireConstructorAccessor();
    }
    @SuppressWarnings("unchecked")
    T inst = (T) ca.newInstance(initargs);
    return inst;
}
```

Constructor 클래스의 newInstance 메소드의 소스 코드를 살펴보면 Enum인 경우 예외를 발생하도록 구현되어 있기 때문에 리플렉션을 사용해도
새로운 객체를 생성할 수 없으므로 가장 안전한 방법으로 싱글톤 패턴을 구현할 수 있는 방법입니다.

### 사용 사례

1. java.lang.Runtime

자바 애플리케이션 실행 환경을 객체화하여 싱글톤으로 제공합니다. OS간의 상호 작용이 가능하고 JVM이 작동하는 시스템과의
인터페이스를 제공하며 자바 클래스가 아닌 운영체제 기반의 프로그램을 실행시키거나 운영체제에 대한 정보를 제공합니다.

2. Spring Framework의 Bean

스프링 프레임워크에서 @Bean 또는 @Component 애노테이션이 적용된 객체는 ApplicationContext에 의해 빈으로 관리되고 기본 스코프 설정에 의해 싱글톤으로 관리됩니다. 
멀티 쓰레드 환경에서 필드를 가지지 않거나 상수 필드를 가진 상태가 변경되지 않는 객체를 매번 생성하지 않고 하나의 객체만 생성하고 재사용하여 효율적으로 사용합니다.

