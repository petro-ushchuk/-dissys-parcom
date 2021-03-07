package lab4;

import lab2.model.Compute;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class BrainImpl extends UnicastRemoteObject implements Brain {
    protected BrainImpl() throws RemoteException {
    }


    @Override
    public float[] calculateLeft(Compute compute) throws RemoteException {

        CompletableFuture<float[]> b = CompletableFuture.supplyAsync(compute::calculateB);
        CompletableFuture<float[][]> a = CompletableFuture.supplyAsync(compute::calculateA);

        CompletableFuture<float[]> y1 = b.thenCombine(a, (b2s, c2s) -> compute.calculateY1());

        CompletableFuture<float[]> b1minusC1 = CompletableFuture.supplyAsync(compute::b1Minus24C1);
        CompletableFuture<float[]> y2 = CompletableFuture.supplyAsync(() -> {
            b1minusC1.join();
            return compute.calculateY2();
        });

        CompletableFuture<float[][]> b2plusC2 = CompletableFuture.supplyAsync(compute::B2plus24C2T);
        CompletableFuture<float[][]> y3 = CompletableFuture.supplyAsync(() -> {
            b2plusC2.join();
            return compute.calculateY3();
        });

        CompletableFuture<float[]> y2y3plusY1 = y2.thenCombine(y3, (b2s, c2s) -> compute.calculateY2Y3Y3y1PlusY1());
        float [] left = new float[0];
        try {
            left = y2y3plusY1.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return left;
    }

}
