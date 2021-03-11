package lab2.service;

import lab2.controller.ComputerController;
import lab2.model.Compute;


public class OneThreadBasedComputing {
    public static Float calculate(ComputerController computerController, int n){
        Compute compute = new Compute(computerController, n);
        float[][] b2 = compute.calculateB2();
        float[][] a2 = compute.calculateA2();
        float[] b1 = compute.calculateB1();
        float[] c1 = compute.calculateC1();
        float[][] a1 = compute.calculateA1();
        float[][] a = compute.calculateA();
        float[] b = compute.calculateB();
        float[][] c2 = compute.calculateC2();
        float[] y1 = Compute.calculateMatrixMultVector(a, b);
        float[] y2 = Compute.calculateMatrixMultVector(a1, Compute.staticB124C1(b1, c1));
        float[][] y3 =Compute.staticMatrxMultMatrx(Compute.staticB224c2(b2, c2), a2);
        float[] left =  Compute.vectorAddNumber(Compute.multTwoVectors(Compute.calculateVectorMultMatrix(Compute.calculateVectorMultMatrix(y2,y3),y3), y1),y1);
        float[] right = Compute.vectorAddVector(Compute.calculateMatrixMultVector(y3, y1), y2);
        float x = Compute.multTwoVectors(left, right);
        return x;
    }
}
