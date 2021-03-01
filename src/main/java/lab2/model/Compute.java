package lab2.model;

import javafx.scene.control.TextArea;
import lab2.controller.ComputerController;
import lab2.service.LoadTextService;

import java.util.Arrays;

import static lab2.Main.log;

public class Compute {
    private volatile int n;
    private volatile double[][] a;
    private volatile double[] b;
    //   -----   y1=A*b  -----
    private volatile double[] y1;

    private volatile double[][] a1;
    private volatile double[] b1;
    private volatile double[] c1;
    //   -----   y2=A1(b1-24c1)  -----
    private volatile double[] y2;

    private volatile double[][] a2;
    private volatile double[][] b2;
    private volatile double[][] c2;
    //  -----   Y3=A2(B2+24C2)  -----
    private volatile double[][] y3;
    // ------ x divided into two parts: LEFT (y2'Y3y1 + y1'), RIGHT(Y3y1+y2)
    // vector-row
    private volatile double[] left;
    // vector-column
    private volatile double[] right;
    //x - number
    private volatile double x;

    private volatile ComputerController controller;


    public Compute(ComputerController computerController, int n) {
        this.n = n;
        a = new double[n][n];
        b = new double[n];
        y1 = new double[n];
        a1 = new double[n][n];
        b1 = new double[n];
        c1 = new double[n];
        y2 = new double[n];
        a2 = new double[n][n];
        b2 = new double[n][n];
        c2 = new double[n][n];
        y3 = new double[n][n];
        left = new double[n];
        right = new double[n];
        controller = computerController;
    }

    public double[] calculateB() {
        for (int i = 0; i < n; i += 2) {
            b[i] = 24.0 / ((i + 1) * (i + 1) + 4);
        }
        for (int i = 1; i < n; i += 2) {
            b[i] = 24.0;
        }
        log.info("b: " + Arrays.toString(b));
        return b;
    }

    public double[][] calculateC2() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c2[i][j] = 24.0 / ((i + 1) + 3 * (j + 1) * (j + 1));
            }
        }
        log.info("C2: " + Arrays.deepToString(c2));
        return c2;
    }

    public double[] calculateY1() {
        calculateMatrixMultVector(a, b, y1);
        log.info("y1: " + Arrays.toString(y1));
        return y1;
    }

    private double[] calculateMatrixMultVector(double[][] matrix, double[] vector, double[] result) {
        for (int i = 0; i < n; i++) {
            result[i] = multTwoVectors(matrix[i], vector);
        }
        return result;
    }

    public double multTwoVectors(double[] matrixRow, double[] vector) {
        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            sum += matrixRow[i] * vector[i];
        }
        return sum;
    }


    public double[] calculateY2() {
        calculateMatrixMultVector(a1, b1Minus24C1(), y2);
        log.info("y2: " + Arrays.toString(y2));
        return y2;
    }

    public double[][] B2plus24C2T() {
        double[][] matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[j][i] = b2[i][j] + 24 * c2[i][j];
            }
        }
        log.info("B2+24*C2: " + Arrays.deepToString(matrix));
        return matrix;
    }

    public double[] b1Minus24C1() {
        double[] vector = new double[n];
        for (int i = 0; i < n; i++) {
            vector[i] = b1[i] - 24 * c1[i];
        }
        log.info("b1-24*c1: " + Arrays.toString(vector));
        return vector;
    }

    public double[][] calculateY3() {
        calculateMatrixMultMatrix(a2, B2plus24C2T(), y3);
        log.info("Y3: " + Arrays.deepToString(y3));
        return y3;
    }

    private double[][] calculateMatrixMultMatrix(double[][] left, double[][] right, double[][] result) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = multTwoVectors(left[i], right[j]);
            }
        }
        return result;
    }


    public double[] calculateY2Y3Y3y1PlusY1() {
        double[] matrix = left;
        double[] y2y3 = calculateVectorMultMatrix(y2, y3);
        double[] y2y3y3 = calculateVectorMultMatrix(y2y3, y3);
        double y2y3y3y1 = multTwoVectors(y2y3y3, y1);
        for (int i = 0; i < n; i++) {
            matrix[i] = y2y3y3y1 + y1[i];
        }
        log.info("y2Y3^2y1+y1': " + Arrays.toString(matrix));
        return matrix;
    }

    private double[] calculateVectorMultMatrix(double[] vector, double[][] matrix) {
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            double sum = 0.0;
            for (int j = 0; j < n; j++) {
                sum += vector[j] * matrix[j][i];
            }
            result[i] = sum;
        }
        return result;
    }

    public double[] calculateY3Y1plusY2() {
        double [] vector = right;
        double[] y3y1 = calculateMatrixMultVector(y3, y1, new double[n]);
        for (int i = 0; i < n; i++) {
            vector[i] = y3y1[i] + y2[i];
        }
        log.info("Y3y1+y2': " + Arrays.toString(vector));
        return vector;
    }

    public double getX() {
        x = multTwoVectors(left, right);
        return x;
    }

    public double[][] calculateB2() {
        return textAreaToMatrix(controller.getTextAreaB2(), b2, "B2");
    }

    private double[][] textAreaToMatrix(TextArea textAreaB2, double[][] matrix, String matrixName) {
        String text = LoadTextService.read(textAreaB2);
        String[] numbers = text.split("[\\s\n]+");
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Double.parseDouble(numbers[k++]);
            }
        }
        log.info(matrixName + Arrays.deepToString(matrix));
        return matrix;
    }

    private double[] textAreaToVector(TextArea textAreaB2, double[] vector, String vectorName) {
        String text = LoadTextService.read(textAreaB2);
        String[] numbers = text.split("[\\s\n]+");
        int k = 0;
        for (int i = 0; i < n; i++) {
            vector[i] = Double.parseDouble(numbers[i]);
        }
        log.info(vectorName + Arrays.toString(vector));
        return vector;
    }


    public double[][] calculateA2() {
        return textAreaToMatrix(controller.getTextAreaA2(), a2, "A2");
    }

    public double[] calculateB1() {
        return textAreaToVector(controller.getTextAreaB1(), b1, "b1");
    }

    public double[] calculateC1() {
        return textAreaToVector(controller.getTextAreaC1(), c1, "c1");
    }

    public double[][] calculateA1() {
        return textAreaToMatrix(controller.getTextAreaA1(), a1, "A1");
    }

    public double[][] calculateA() {
        return textAreaToMatrix(controller.getTextAreaA(), a, "A");
    }


}
