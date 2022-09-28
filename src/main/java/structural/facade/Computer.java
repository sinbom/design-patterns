package structural.facade;

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
