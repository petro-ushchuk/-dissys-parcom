package lab4.service;

import lab4.controller.ComputerController;
import lab4.model.Compute;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ThreadBasedComputing {


    public static float calculate(ComputerController computerController, int n) throws InterruptedException, ExecutionException {
        Compute compute = new Compute(computerController, n);
        CompletableFuture<float[][]> c2 = CompletableFuture.supplyAsync(compute::calculateC2);
        CompletableFuture<float[][]> b2 = CompletableFuture.supplyAsync(compute::calculateB2);
        CompletableFuture<float[][]> a2 = CompletableFuture.supplyAsync(compute::calculateA2);
        CompletableFuture<float[]> b1 = CompletableFuture.supplyAsync(compute::calculateB1);
        CompletableFuture<float[]> c1 = CompletableFuture.supplyAsync(compute::calculateC1);
        CompletableFuture<float[][]> a1 = CompletableFuture.supplyAsync(compute::calculateA1);
        CompletableFuture<float[]> b = CompletableFuture.supplyAsync(compute::calculateB);
        CompletableFuture<float[][]> a = CompletableFuture.supplyAsync(compute::calculateA);

        CompletableFuture<float[][]> b2plusC2 = b2.thenCombine(c2, (b2s, c2s) -> compute.B2plus24C2T());
        CompletableFuture<float[]> b1minusC1 = b1.thenCombine(c1, (b2s, c2s) -> compute.b1Minus24C1());
        CompletableFuture<float[]> y1 = b.thenCombine(a, (b2s, c2s) -> compute.calculateY1());
        CompletableFuture<float[]> y2 = a1.thenCombine(b1minusC1, (b2s, c2s) -> compute.calculateY2());
        CompletableFuture<float[][]> y3 = b2plusC2.thenCombine(a2, (b2s, c2s) -> compute.calculateY3());
        CompletableFuture<float[]> y2y3plusY1 = y2.thenCombine(y3, (b2s, c2s) -> compute.calculateY2Y3Y3y1PlusY1());
        CompletableFuture<float[]> y3y1plusY2 = y2.thenCombine(y3, (b2s, c2s) -> compute.calculateY3Y1plusY2());

        CompletableFuture<Float> x = y2y3plusY1.thenCombine(y3y1plusY2, (b2s, c2s) -> compute.getX());
        return x.get();
    }
}
