package lab4;

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

    public static float calculate(ComputerController computerController, int n, String address, String name, int port) throws ExecutionException, InterruptedException, RemoteException {
        System.out.println("\tClient is starting...");
        Client myClient = new Client(address, name, port);    //Запускаю клієнт
        Compute compute = new Compute(computerController, n);

        CompletableFuture<float[][]> c2 = CompletableFuture.supplyAsync(compute::calculateC2);
        CompletableFuture<float[][]> b2 = CompletableFuture.supplyAsync(compute::calculateB2);
        CompletableFuture<float[][]> a2 = CompletableFuture.supplyAsync(compute::calculateA2);

        CompletableFuture<float[]> b1 = CompletableFuture.supplyAsync(compute::calculateB1);
        CompletableFuture<float[]> c1 = CompletableFuture.supplyAsync(compute::calculateC1);
        CompletableFuture<float[][]> a1 = CompletableFuture.supplyAsync(compute::calculateA1);

        CompletableFuture<float[]> left = CompletableFuture.allOf(c2,b2,a2,b1,c1,a1).thenCombine(a1, (b2s, c2s) -> {
            try {
                return myClient.remotingBrain.calculateLeft(compute);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return new float[0];
        });


        CompletableFuture<float[]> b = CompletableFuture.supplyAsync(compute::calculateB);
        CompletableFuture<float[][]> a = CompletableFuture.supplyAsync(compute::calculateA);

        CompletableFuture<float[]> y1 = b.thenCombine(a, (b2s, c2s) -> compute.calculateY1());

        CompletableFuture<float[]> b1minusC1 = b1.thenCombine(c1, (b2s, c2s) -> compute.b1Minus24C1());
        CompletableFuture<float[]> y2 = a1.thenCombine(b1minusC1, (b2s, c2s) -> compute.calculateY2());

        CompletableFuture<float[][]> b2plusC2 = b2.thenCombine(c2, (b2s, c2s) -> compute.B2plus24C2T());
        CompletableFuture<float[][]> y3 = b2plusC2.thenCombine(a2, (b2s, c2s) -> compute.calculateY3());

        CompletableFuture<float[]> y3y1plusY2 = y2.thenCombine(y3, (b2s, c2s) -> compute.calculateY3Y1plusY2());

        CompletableFuture<Float> x = y3y1plusY2.thenCombine(left, compute::multTwoVectors);
        return x.get();

    }


    {



    }
}
