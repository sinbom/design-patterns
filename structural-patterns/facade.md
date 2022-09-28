# Structural Patterns(구조 관련 패턴)

## Facade (퍼사드 패턴)
> 복잡한 서브 시스템 의존성들을 간단한 인터페이스로 통합하여 추상화하는 패턴입니다.

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/structural/facade.jpg?raw=true"/>
</p>

### 구현 방법

```java
public class CPU {

    public void freeze() {
        System.out.println("freeze!");
    }

    public void jump(long position) {
        System.out.println("jump!");
    }

    public void execute() {
        System.out.println("execute!");
    }

}
```

```java
public class Memory {

    public void load(byte[] data) {
        System.out.println("load!");
    }

}
```

```java
public class HardDrive {

    public byte[] read(int size) {
        System.out.println("read!");

        return "read".getBytes();
    }

}
```

여러 개의 서브 시스템을 구성하는 클래스를 정의합니다.

```java
public class Computer {

    private final CPU cpu;

    private final Memory memory;

    private final HardDrive hardDrive;

    public Computer(CPU cpu, Memory memory, HardDrive hardDrive) {
        this.cpu = cpu;
        this.memory = memory;
        this.hardDrive = hardDrive;
    }

    public void boot() {
        cpu.freeze();
        memory.load(hardDrive.read(10));
        cpu.jump(10);
        cpu.execute();
    }

}
```

서브 클래스 의존성들을 통합하는 클래스를 정의합니다.

<p align="center">
    <img src="https://github.com/sinbom/design-patterns/blob/master/resources/structural/facade-diagram.png?raw=true"/>
</p>

클래스들을 정의한 다이어그램은 다음과 같습니다.

```java
@Test
void 여러_서브시스템_의존성들을_단일_객체로_통합한다() {
    CPU cpu = new CPU();
    Memory memory = new Memory();
    HardDrive hardDrive = new HardDrive();
    Computer computer = new Computer(cpu, memory, hardDrive);

    computer.boot();
}
```

여러 개의 서브 시스템을 구성하는 클래스들을 직접 사용하는 것과 통합하는 클래스를 통해 사용하는 것이 차이가 없다고 느낄 수 있지만 서브 클래스들의 직접 의존하지 않는 것에 차이가 있습니다.
