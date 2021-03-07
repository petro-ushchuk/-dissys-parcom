package lab4;

import javafx.scene.control.TextArea;
import lab2.controller.ComputerController;
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

    public static float calculate(ComputerController computerController, int n, String address, String name, int port) throws ExecutionException, InterruptedException {
        System.out.println("\tClient is starting...");
        Client myClient = new Client(address, name, port);    //Запускаю клієнт
        Compute compute = new Compute(computerController, n);

        CompletableFuture<Void> y2y3plusY1 = CompletableFuture.runAsync(() ->
            myClient.remotingBrain.calculateLeft(compute)
        );

        CompletableFuture<float[]> b1 = CompletableFuture.supplyAsync(compute::calculateB1);
        CompletableFuture<float[]> c1 = CompletableFuture.supplyAsync(compute::calculateC1);
        CompletableFuture<float[][]> a1 = CompletableFuture.supplyAsync(compute::calculateA1);

        CompletableFuture<float[]> b1minusC1 = b1.thenCombine(c1, (b2s, c2s) -> compute.b1Minus24C1());
        CompletableFuture<float[]> y2 = a1.thenCombine(b1minusC1, (b2s, c2s) -> compute.calculateY2());

        CompletableFuture<float[]> y3y1plusY2 = y2.thenCombine(y2, (b2s, c2s) -> compute.calculateY3Y1plusY2());
        CompletableFuture<Float> x = y2y3plusY1.thenCombine(y3y1plusY2, (b2s, c2s) -> compute.getX());
        return x.get();


    }
}
