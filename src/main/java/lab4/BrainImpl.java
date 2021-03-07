package lab4;

import lab2.model.Compute;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class BrainImpl extends UnicastRemoteObject implements Brain
{
    protected BrainImpl() throws RemoteException {
    }

    public float[] calculateLeft(Compute compute){
        CompletableFuture<float[][]> c2 = CompletableFuture.supplyAsync(compute::calculateC2);
        CompletableFuture<float[][]> b2 = CompletableFuture.supplyAsync(compute::calculateB2);
        CompletableFuture<float[][]> a2 = CompletableFuture.supplyAsync(compute::calculateA2);

        CompletableFuture<float[]> b = CompletableFuture.supplyAsync(compute::calculateB);
        CompletableFuture<float[][]> a = CompletableFuture.supplyAsync(compute::calculateA);

        CompletableFuture<float[]> y1 = b.thenCombine(a, (b2s, c2s) -> compute.calculateY1());

        CompletableFuture<float[][]> b2plusC2 = b2.thenCombine(c2, (b2s, c2s) -> compute.B2plus24C2T());
        CompletableFuture<float[][]> y3 = b2plusC2.thenCombine(a2, (b2s, c2s) -> compute.calculateY3());

        CompletableFuture<float[]> y3y1plusY2 = y1.thenCombine(y3, (b2s, c2s) -> compute.calculateY2Y3Y3y1PlusY1());
        try {
            return y3y1plusY2.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
