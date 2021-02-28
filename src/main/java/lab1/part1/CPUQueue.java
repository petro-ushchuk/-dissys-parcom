package lab1.part1;

import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Integer.max;

public class CPUQueue extends Thread {

    protected static Queue<CPUProcess> PROCESSES_QUEUE = new LinkedList<>();

    static {
        PROCESSES_QUEUE.add(new CPUProcess(0));
        System.out.println("Added process number " + 0 + "...");
    }

    private int proccessCounter = 0;
    private int maxQueue = 0;

    private final int MAX_TIME;
    private final int MIN_TIME;

    public CPUQueue(int MAX_TIME, int MIN_TIME) {
        this.MAX_TIME = MAX_TIME - MIN_TIME;
        this.MIN_TIME = MIN_TIME;
    }

    @Override
    public synchronized void run() {
        while (Thread.activeCount() > 3) {
            PROCESSES_QUEUE.add(new CPUProcess(++proccessCounter));
            System.out.println("Added process number " + proccessCounter + "...");
            maxQueue = max(maxQueue, PROCESSES_QUEUE.size());
            try {
                sleep((int)(Math.random() * MAX_TIME) + MIN_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getMaxQueue() {
        return maxQueue;
    }
}
