package structural.facade;

import org.junit.jupiter.api.Test;

public class FacadeTest {

    @Test
    void 여러_서브시스템_의존성들을_단일_객체로_통합한다() {
        CPU cpu = new CPU();
        Memory memory = new Memory();
        HardDrive hardDrive = new HardDrive();
        Computer computer = new Computer(cpu, memory, hardDrive);

        computer.boot();
    }

}
