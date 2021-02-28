package lab1.part1;

/**
 * Варіант 24. Програма моделює обслуговування одного потоку процесів двома центральними процесорами
 * з однією чергою. Коли процес віддаляється з черги, він спочатку поступає на обробку в перший процесор.
 * Як тільки процес обробки закінчується, процес обробляється на другому процесорі, а на перший процесор
 * поступає черговий процес з черги (якщо черга не порожня). Процес на першому процесорі чекає закінчення
 * обробки попереднього процесу на другому процесорі (якщо обробка попереднього  процесу ще не закінчилася).
 * Визначте максимальну довжину черги.
 */

public class Demo {
    public static void main(String[] args) {
        CPUQueue queue = new CPUQueue(20, 1);
        Thread cpu1 = new Thread(new CPU(CPUQueue.PROCESSES_QUEUE, 5, 1));
        Thread cpu2 = new Thread(new CPU(CPUQueue.PROCESSES_QUEUE, 5, 1));

        cpu1.setPriority(Thread.MAX_PRIORITY);
        queue.start();
        cpu1.start();
        cpu2.start();
        try {
            queue.join();
            cpu1.join();
            cpu2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("MAX QUEUE  -  %d " , queue.getMaxQueue());
    }
}
