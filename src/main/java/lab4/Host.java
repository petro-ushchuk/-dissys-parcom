package lab4;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Host {

    public Host(String name, int port) throws RemoteException {
        Registry reg = LocateRegistry.createRegistry(port);
        reg.rebind(name, new BrainImpl());
        System.out.println("Host " + name + " started successfully");
        System.out.println("Press [Enter] to stop the Host");
        new Scanner(System.in).nextLine();
        System.out.println("Server stopped...");
        System.exit(0);
    }

    public static void main(String[] args){
        if(args.length > 2){
            System.out.println("Enter [host name] [port]");
            return;
        }
        String name = args[0];
        try {
            int port = Integer.parseInt(args[1]);
            System.out.println("Host is starting ...");
            final Host host = new Host(name, port);
        }catch (NumberFormatException e){
            System.out.println("Bad host");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
