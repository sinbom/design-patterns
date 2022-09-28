package structural.facade;

public class HardDrive {

    public byte[] read(int size) {
        System.out.println("read!");

        return "read".getBytes();
    }

}
