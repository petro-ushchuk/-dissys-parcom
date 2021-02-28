package lab1.part2.mysynch;


class Caller extends Thread {
    String msg;
    String msg1;
    static Callme target;

    public Caller(Callme targ, String s, String s1) {
        target = targ;
        msg = s;
        msg1 = s1;
        start();
    }

    @Override
    public void run() {
       target.call(msg, msg1);
    }
}
