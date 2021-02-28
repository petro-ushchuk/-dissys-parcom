package lab1.part2.asynch;

class Synch {
    public static void main(String args[]) {
        Callme target = new Callme();
        Caller ob1 = new Caller(target, "Ім'я", "Петро");
        Caller ob2 = new Caller(target, "Прізвище", "Ущук");
        Caller ob3 = new Caller(target, "По-батькові", "Олексійович");
        Caller ob4 = new Caller(target, "Рік народження", "2000");
        Caller ob5 = new Caller(target, "Ріст", "183");
        Caller ob6 = new Caller(target, "Вага", "70");
        try {
            ob1.t.join();
            ob2.t.join();
            ob3.t.join();
            ob4.t.join();
            ob5.t.join();
            ob6.t.join();
        } catch (InterruptedException е) {
            System.out.println("Перервано");
        }
    }
}
