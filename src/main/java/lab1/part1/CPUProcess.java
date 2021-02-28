package lab1.part1;

public class CPUProcess {

    private int number;

    public CPUProcess(int number) {
        this.number = number;
    }

    public void processing() {
        System.out.println(this + " is being processed by " + Thread.currentThread().getName() + "...");
    }

    @Override
    public String toString() {
        return "Process-" + number;
    }

}
