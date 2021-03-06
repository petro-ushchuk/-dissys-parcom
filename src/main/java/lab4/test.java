package lab4;

import java.rmi.*;
import java.rmi.registry.*;
import java.io.*;
import java.rmi.server.*;
public class test
{
    public interface Brain extends Remote 	//Це віддалений інтерфейс
    {
        void Ping() throws Exception;
        void message(String str) throws Exception;	//Відправка текстового повідомлення на сервер
    }
    public class BrainImpl extends UnicastRemoteObject implements Brain
//Це реалізація віддаленого інтерфейсу
    {
        public BrainImpl() throws Exception
        {
        }
        public void Ping() throws Exception
        {
            System.out.println("Pong! :)");
        }
        public void message(String str) throws Exception
        {
            System.out.println("Client: "+str);
        }
    }
    public class Host	//Це сервер
    {
        public Registry reg;
        public Host(String HostName,int port) throws Exception
        {
            Registry reg = LocateRegistry.createRegistry(port);	//Порт задеє користувач
            reg.rebind(HostName, new BrainImpl());	//Прив'язую сервер до реєстру RMI
            System.out.println("Host "+HostName+" started successfully");
            System.out.println("Press [Enter] to close the Host");
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            keyboard.readLine();	//Сервер завершить роботу при натиску клавіші Enter
        }
    }
    public class Client	//Це клієнт
    {
        public Brain remotingBrain;	//Це інтерфейс що буде посилатися на віддалений клас
        public Client(String address,String HostName,int port) throws Exception
        {
            remotingBrain = (Brain) Naming.lookup("//"+address+":"+port+"/"+HostName);
//З'єднуюся з реєстром RMI і отримую посилання на віддалений серверний об'єкт
            System.out.println("Connection to "+address+":"+port+"/"+HostName+" is succeed");
        }
    }
    public test() throws Exception	//Це реалізація інтерфейсу користувача
    {
        System.out.println("Type [1] to start Host");
        System.out.println("Type [2] to start Client");
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        int c = keyboard.readLine().charAt(0); //Вибір запуску клієнта або сервера
        if (c == '1')	//Якщо обрано запуск сервера
        {
            String address = null;
            String name = null;
            int port;
            System.out.print("\t-host name:\t");
            name = keyboard.readLine();	//Зчитую ім'я сервера
            System.out.print("\t-port:\t\t");
            port = Integer.parseInt(keyboard.readLine());	//І номер порта де він буде розміщений
            System.out.println("\tHost is starting ...");
            Host myHost = new Host(name,port);	//Запускаю сервер
        }
        else if (c == '2')	//Якщо обрано запуск клієнта
        {
            String address = null;
            String name = null;
            int port;
            System.out.print("\t-adress:\t");
            address = keyboard.readLine();	//Зчитую адрес де має бути розташований сервер
            System.out.print("\t-host name:\t");
            name = keyboard.readLine();		//Зчитую його ім'я
            System.out.print("\t-port:\t\t");
            port = Integer.parseInt(keyboard.readLine());	//І його порт
            System.out.println("\tClient is starting...");
            Client myClient = new Client(address,name,port);	//Запускаю клієнт
            System.out.print("\tPing! - ");
            myClient.remotingBrain.Ping();	//Викликаю просту віддалену процедуру
            System.out.println("Pong!");
            System.out.println("Type <something data> to send that");
            System.out.println("Type [%exit] to exit");
            String str = null;
            while(!(str = keyboard.readLine()).equalsIgnoreCase("%exit"))
                myClient.remotingBrain.message(str);	//Зациклюю програму для можливості передачі
//повідомлень на серверний комп'ютер
        }
    }
    public static void main(String[] args) throws Exception
    {
        new test();
    }
}
