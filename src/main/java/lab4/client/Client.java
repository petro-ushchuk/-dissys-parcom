package lab4.client;

import lab2.model.Compute;
import lab4.Brain;
import lab4.exception.BadConnectionException;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Client {
    public Brain remotingBrain;

    public Client(String address, String name, int port) throws BadConnectionException {
        try {
            remotingBrain = (Brain) Naming.lookup("//" + address + ":" + port + "/" + name);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            throw new BadConnectionException("Can't connect to this adress");
        }
        System.out.println("Connection to " + address + ":" + port + "/" + name + " is succeed");
    }

    public static float calculate(Client myClient, Compute compute) throws ExecutionException, InterruptedException {
        CompletableFuture<float[][]> c2 = CompletableFuture.supplyAsync(() -> {
            try {
                return myClient.remotingBrain.calculateC2(compute.getN());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return null;
        });

        CompletableFuture<float[]> b = CompletableFuture.supplyAsync(() -> {
            try {
                return myClient.remotingBrain.calculateB(compute.getN());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return null;
        });

        CompletableFuture<float[][]> b2 = CompletableFuture.supplyAsync(compute::calculateB2);
        CompletableFuture<float[][]> a2 = CompletableFuture.supplyAsync(compute::calculateA2);
        CompletableFuture<float[]> b1 = CompletableFuture.supplyAsync(compute::calculateB1);
        CompletableFuture<float[]> c1 = CompletableFuture.supplyAsync(compute::calculateC1);
        CompletableFuture<float[][]> a1 = CompletableFuture.supplyAsync(compute::calculateA1);
        CompletableFuture<float[][]> a = CompletableFuture.supplyAsync(compute::calculateA);

        CompletableFuture<float[]> y1 = b.thenCombine(a, (bk, ak) -> {
            try {
                return myClient.remotingBrain.calculateY1(ak, bk);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return null;
        });
        CompletableFuture<float[][]> y3 = b2.thenCombine(c2, Compute::staticB224c2).thenCombine(a2, Compute::staticMatrxMultMatrx);
        CompletableFuture<float[]> y2 = b1.thenCombine(c1, Compute::staticB124C1).thenCombine(a1, (bk, ak) -> Compute.calculateMatrixMultVector(ak, bk));
        CompletableFuture<float[]> right = CompletableFuture.supplyAsync(()->{
            try {
                return myClient.remotingBrain.calculateRight(y1.get(), y2.get(), y3.get());
            } catch (RemoteException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        });
        CompletableFuture<float[]>  left = y2.thenCombine(y3, Compute::calculateVectorMultMatrix)
                .thenCombine(y3, Compute::calculateVectorMultMatrix)
                .thenCombine(y1, Compute::multTwoVectors)
                .thenCombine(y1, Compute::vectorAddNumber);
        CompletableFuture<Float> x = left.thenCombine(right, Compute::multTwoVectors);
        return x.get();
    }
}
