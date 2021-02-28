package lab2.service;

import lab2.controller.ComputerController;
import lab2.model.Compute;

public class ThreadBasedComputing {


    public static Double calculate(ComputerController computerController, int n) throws InterruptedException {
        Compute compute = new Compute(computerController, n);
        Thread c2 = new Thread(compute::calculateC2);
        Thread b2 = new Thread(compute::calculateB2);
        Thread a2 = new Thread(compute::calculateA2);
        Thread b1 = new Thread(compute::calculateB1);
        Thread c1 = new Thread(compute::calculateC1);
        Thread a1 = new Thread(compute::calculateA1);
        Thread b = new Thread(compute::calculateB);
        Thread a = new Thread(compute::calculateA);

        Thread b2plusC2 = new Thread(() -> {
            try {
                c2.join();
                b2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            compute.B2plus24C2T();
        });

        Thread y3 = new Thread(() -> {
            try {
                b2plusC2.join();
                a2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            compute.calculateY3();
        });

        Thread b1minusC1 = new Thread(() -> {
            try {
                b1.join();
                c1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            compute.b1Minus24C1();
        });

        Thread y2 = new Thread(() -> {
            try {
                a1.join();
                b1minusC1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            compute.calculateY2();

        });
        Thread y1 = new Thread(() -> {
            try {
                b.join();
                a.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            compute.calculateY1();
        });

        Thread y2y3plusY1 = new Thread(() -> {
            try {
                y1.join();
                y2.join();
                y3.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            compute.calculateY2Y3Y3y1PlusY1();
        });

        Thread y3y1plusY2 = new Thread(() -> {
            try {
                y1.join();
                y2.join();
                y3.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            compute.calculateY3Y1plusY2();
        });

        a.start();
        a1.start();
        a2.start();
        b.start();
        b1.start();
        b2.start();
        c1.start();
        c2.start();

        b2plusC2.start();
        b1minusC1.start();

        y1.start();
        y2.start();
        y3.start();

        y2y3plusY1.start();
        y3y1plusY2.start();

        y3y1plusY2.join();
        y2y3plusY1.join();

        return compute.getX();
    }
}
