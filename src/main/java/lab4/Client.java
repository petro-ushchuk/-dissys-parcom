package lab4;

import lab2.model.Compute;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Client {
    public Brain remotingBrain;

    public Client(String address, String name, int port) {
        try {
            remotingBrain = (Brain) Naming.lookup("//" + address + ":" + port + "/" + name);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.err.println("Can't connect to " + address + ":" + port + "/" + name);
            return;
        }
        System.out.println("Connection to " + address + ":" + port + "/" + name + " is succeed");
    }

    public static float calculate(Client myClient, Compute compute) throws ExecutionException, InterruptedException {

    }
}
