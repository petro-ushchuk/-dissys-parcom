package lab1.part1;

import java.util.Queue;

import static java.lang.Thread.sleep;

public class CPU implements Runnable {

    protected static final Object monitor = new Object();
    protected static boolean locked;

    private final Queue<CPUProcess> processQueue;
    private final int maxTime;
    private final int minTime;

    public CPU(Queue<CPUProcess> processQueue, int maxTime, int minTime) {
        this.processQueue = processQueue;
        this.maxTime = maxTime;
        this.minTime = minTime;
    }

    @Override
    public void run() {
        while (!processQueue.isEmpty()) {
            synchronized (monitor) {
                try {
                    processQueue.element().processing();
                    sleep((int) (Math.random() * maxTime) + minTime);
                    if (locked) {
                        processQueue.remove();
                        locked = false;
                        monitor.notify();
                        monitor.wait();
                    } else {
                        locked = true;
                        monitor.wait();
                        monitor.notify();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
