package lab1.part2.mysynch;

class Synch {
    public static void main(String args[]) throws InterruptedException {
        String [] phrases = {"Ім'я","Петро","Прізвище","Ущук",
                "По-батькові","Олексійович","Рік народження","2000","Ріст","183","Вага","70"};
        Callme target = new Callme();
        for(int i = 0; i < phrases.length; i+=2) {
            Caller ob = new Caller(target, phrases[i], phrases[i+1]);
            ob.join();
        }
    }
}
