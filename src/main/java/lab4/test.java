//package lab4;
//
//import java.io.*;
//
//public class test
//{
//
//    public test() throws Exception	//Це реалізація інтерфейсу користувача
//    {
//        System.out.println("Type [1] to start Host");
//        System.out.println("Type [2] to start Client");
//        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
//        int c = keyboard.readLine().charAt(0); //Вибір запуску клієнта або сервера
//        if (c == '1')	//Якщо обрано запуск сервера
//        {
//            String address = null;
//            String name = null;
//            int port;
//            System.out.print("\t-host name:\t");
//            name = keyboard.readLine();	//Зчитую ім'я сервера
//            System.out.print("\t-port:\t\t");
//            port = Integer.parseInt(keyboard.readLine());	//І номер порта де він буде розміщений
//            System.out.println("\tHost is starting ...");
//            Host myHost = new Host(name,port);	//Запускаю сервер
//        }
//        else if (c == '2')	//Якщо обрано запуск клієнта
//        {
//            String address = null;
//            String name = null;
//            int port;
//            System.out.print("\t-adress:\t");
//            address = keyboard.readLine();	//Зчитую адрес де має бути розташований сервер
//            System.out.print("\t-host name:\t");
//            name = keyboard.readLine();		//Зчитую його ім'я
//            System.out.print("\t-port:\t\t");
//            port = Integer.parseInt(keyboard.readLine());	//І його порт
//            System.out.println("\tClient is starting...");
//            Client myClient = new Client(address,name,port);	//Запускаю клієнт
//            System.out.print("\tPing! - ");
//            myClient.remotingBrain.Ping();	//Викликаю просту віддалену процедуру
//            System.out.println("Pong!");
//            System.out.println("Type <something data> to send that");
//            System.out.println("Type [%exit] to exit");
//            String str = null;
//            while(!(str = keyboard.readLine()).equalsIgnoreCase("%exit"))
//                myClient.remotingBrain.message(str);	//Зациклюю програму для можливості передачі
////повідомлень на серверний комп'ютер
//        }
//    }
//    public static void main(String[] args) throws Exception
//    {
//        new test();
//    }
//}
