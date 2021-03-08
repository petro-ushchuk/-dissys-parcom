package lab2.service;

import lab2.controller.ComputerController;
import lab2.model.Compute;

import java.rmi.RemoteException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ThreadBasedComputing {


    public static float calculate(ComputerController computerController, int n) throws InterruptedException, ExecutionException {
        Compute compute = new Compute(computerController, n);
        CompletableFuture<float[][]> b2 = CompletableFuture.supplyAsync(compute::calculateB2);
        CompletableFuture<float[][]> a2 = CompletableFuture.supplyAsync(compute::calculateA2);
        CompletableFuture<float[]> b1 = CompletableFuture.supplyAsync(compute::calculateB1);
        CompletableFuture<float[]> c1 = CompletableFuture.supplyAsync(compute::calculateC1);
        CompletableFuture<float[][]> a1 = CompletableFuture.supplyAsync(compute::calculateA1);
        CompletableFuture<float[][]> a = CompletableFuture.supplyAsync(compute::calculateA);
        CompletableFuture<float[]> b = CompletableFuture.supplyAsync(compute::calculateB);
        CompletableFuture<float[][]> c2 = CompletableFuture.supplyAsync(compute::calculateC2);
        CompletableFuture<float[]> y1 = a.thenCombine(b, Compute::calculateMatrixMultVector);
        CompletableFuture<float[]> y2 = b1.thenCombine(c1, Compute::staticB124C1).thenCombine(a1, (bk, ak) -> Compute.calculateMatrixMultVector(ak, bk));
        CompletableFuture<float[][]> y3 = b2.thenCombine(c2, Compute::staticB224c2).thenCombine(a2, Compute::staticMatrxMultMatrx);
        CompletableFuture<float[]> left = y2.thenCombine(y3, Compute::calculateVectorMultMatrix)
                .thenCombine(y3, Compute::calculateVectorMultMatrix)
                .thenCombine(y1, Compute::multTwoVectors)
                .thenCombine(y1, Compute::vectorAddNumber);
        CompletableFuture<float[]> right = y3.thenCombine(y1, Compute::calculateMatrixMultVector)
                .thenCombine(y2, Compute::vectorAddVector);
        CompletableFuture<Float> x = left.thenCombine(right, Compute::multTwoVectors);
        return x.get();
    }
}
