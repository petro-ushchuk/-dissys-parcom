package lab2.service;

import lab2.controller.ComputerController;
import lab2.model.Compute;

public class OneThreadBasedComputing {
    public static Float calculate(ComputerController computerController, int n){
        Compute compute = new Compute(computerController, n);
        compute.calculateC2();
        compute.calculateB2();
        compute.calculateA2();
        compute.calculateB();
        compute.calculateB1();
        compute.calculateC1();
        compute.calculateA1();
        compute.calculateB();
        compute.calculateA();
        compute.B2plus24C2T();
        compute.calculateY3();
        compute.b1Minus24C1();
        compute.calculateY2();
        compute.calculateY1();
        compute.calculateY2Y3Y3y1PlusY1();
        compute.calculateY3Y1plusY2();
        return compute.getX();
    }
}
