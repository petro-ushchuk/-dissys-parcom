package lab2.service;

import lab2.controller.ComputerController;
import lab2.model.Compute;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ThreadBasedComputing {


    public static double calculate(ComputerController computerController, int n) throws InterruptedException, ExecutionException {
        Compute compute = new Compute(computerController, n);
        CompletableFuture<double[][]> c2 = CompletableFuture.supplyAsync(compute::calculateC2);
        CompletableFuture<double[][]> b2 = CompletableFuture.supplyAsync(compute::calculateB2);
        CompletableFuture<double[][]> a2 = CompletableFuture.supplyAsync(compute::calculateA2);
        CompletableFuture<double[]> b1 = CompletableFuture.supplyAsync(compute::calculateB1);
        CompletableFuture<double[]> c1 = CompletableFuture.supplyAsync(compute::calculateC1);
        CompletableFuture<double[][]> a1 = CompletableFuture.supplyAsync(compute::calculateA1);
        CompletableFuture<double[]> b = CompletableFuture.supplyAsync(compute::calculateB);
        CompletableFuture<double[][]> a = CompletableFuture.supplyAsync(compute::calculateA);

        CompletableFuture<double[][]> b2plusC2 = b2.thenCombine(c2, (b2s, c2s) -> compute.B2plus24C2T());
        CompletableFuture<double[]> b1minusC1 = b1.thenCombine(c1, (b2s, c2s) -> compute.b1Minus24C1());
        CompletableFuture<double[]> y1 = b.thenCombine(a, (b2s, c2s) -> compute.calculateY1());
        CompletableFuture<double[]> y2 = a1.thenCombine(b1minusC1, (b2s, c2s) -> compute.calculateY2());
        CompletableFuture<double[][]> y3 = b2plusC2.thenCombine(a2, (b2s, c2s) -> compute.calculateY3());
        CompletableFuture<double[]> y2y3plusY1 = y2.thenCombine(y3, (b2s, c2s) -> compute.calculateY2Y3Y3y1PlusY1());
        CompletableFuture<double[]> y3y1plusY2 = y2.thenCombine(y3, (b2s, c2s) -> compute.calculateY3Y1plusY2());

        CompletableFuture<Double> x = y2y3plusY1.thenCombine(y3y1plusY2, (b2s, c2s) -> compute.getX());
        return x.get();
    }
}
