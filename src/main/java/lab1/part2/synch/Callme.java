package lab1.part2.synch;

class Callme {
    public void call(String msg1, String msg2) {
        System.out.print(msg1 + ": ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException е) {
            System.out.println("Перервано");
        }
        System.out.println(' ' + msg2);
    }
}
