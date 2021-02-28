package lab1.part2.asynch;

class Caller implements Runnable {
    String msg;
    String msg1;
    Callme target;
    Thread t;

    public Caller(Callme targ, String s, String s1) {
        target = targ;
        msg = s;
        msg1 = s1;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        target.call(msg, msg1);
    }
}