package lab2.model;

import javafx.scene.control.TextArea;
import lab2.controller.ComputerController;
import lab2.service.LoadTextService;

import java.io.Serializable;
import java.util.Arrays;

import static lab2.Main.log;

public class Compute implements Serializable {
    private volatile int n;
    private volatile float[][] a;
    private volatile float[] b;
    //   -----   y1=A*b  -----
    private volatile float[] y1;

    private volatile float[][] a1;
    private volatile float[] b1;
    private volatile float[] c1;
    //   -----   y2=A1(b1-24c1)  -----
    private volatile float[] y2;

    private volatile float[][] a2;
    private volatile float[][] b2;
    private volatile float[][] c2;
    //  -----   Y3=A2(B2+24C2)  -----
    private volatile float[][] y3;
    // ------ x divided into two parts: LEFT (y2'Y3y1 + y1'), RIGHT(Y3y1+y2)
    // vector-row
    private volatile float[] left;
    // vector-column
    private volatile float[] right;
    //x - number
    private volatile float x;

    private volatile ComputerController controller;


    public Compute(ComputerController computerController, int n) {
        this.n = n;
        a = new float[n][n];
        b = new float[n];
        y1 = new float[n];
        a1 = new float[n][n];
        b1 = new float[n];
        c1 = new float[n];
        y2 = new float[n];
        a2 = new float[n][n];
        b2 = new float[n][n];
        c2 = new float[n][n];
        y3 = new float[n][n];
        left = new float[n];
        right = new float[n];
        controller = computerController;
    }

    public float[] calculateB() {
        for (int i = 0; i < n; i += 2) {
            b[i] = 24.0F / ((i + 1) * (i + 1) + 4);
        }
        for (int i = 1; i < n; i += 2) {
            b[i] = 24.0F;
        }
//        log.info("b: " + Arrays.toString(b));
        return b;
    }

    public float[][] calculateC2() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c2[i][j] = 24.0F / ((i + 1) + 3 * (j + 1) * (j + 1));
            }
        }
//        log.info("C2: " + Arrays.deepToString(c2));
        return c2;
    }

    public float[] calculateY1() {
        calculateMatrixMultVector(a, b, y1);
//        log.info("y1: " + Arrays.toString(y1));
        return y1;
    }

    private float[] calculateMatrixMultVector(float[][] matrix, float[] vector, float[] result) {
        for (int i = 0; i < n; i++) {
            result[i] = multTwoVectors(matrix[i], vector);
        }
        return result;
    }

    public float multTwoVectors(float[] matrixRow, float[] vector) {
        float sum = 0.0F;
        for (int i = 0; i < n; i++) {
            sum += matrixRow[i] * vector[i];
        }
        return sum;
    }


    public float[] calculateY2() {
        calculateMatrixMultVector(a1, b1Minus24C1(), y2);
//        log.info("y2: " + Arrays.toString(y2));
        return y2;
    }

    public float[][] B2plus24C2T() {
        float[][] matrix = new float[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[j][i] = b2[i][j] + 24 * c2[i][j];
            }
        }
//        log.info("B2+24*C2: " + Arrays.deepToString(matrix));
        return matrix;
    }

    public float[] b1Minus24C1() {
        float[] vector = new float[n];
        for (int i = 0; i < n; i++) {
            vector[i] = b1[i] - 24 * c1[i];
        }
//        log.info("b1-24*c1: " + Arrays.toString(vector));
        return vector;
    }

    public float[][] calculateY3() {
        calculateMatrixMultMatrix(a2, B2plus24C2T(), y3);
//        log.info("Y3: " + Arrays.deepToString(y3));
        return y3;
    }

    private float[][] calculateMatrixMultMatrix(float[][] left, float[][] right, float[][] result) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = multTwoVectors(left[i], right[j]);
            }
        }
        return result;
    }


    public float[] calculateY2Y3Y3y1PlusY1() {
        float[] matrix = left;
        float[] y2y3 = calculateVectorMultMatrix(y2, y3);
        float[] y2y3y3 = calculateVectorMultMatrix(y2y3, y3);
        float y2y3y3y1 = multTwoVectors(y2y3y3, y1);
        for (int i = 0; i < n; i++) {
            matrix[i] = y2y3y3y1 + y1[i];
        }
//        log.info("y2Y3^2y1+y1': " + Arrays.toString(matrix));
        return matrix;
    }

    private float[] calculateVectorMultMatrix(float[] vector, float[][] matrix) {
        float[] result = new float[n];
        for (int i = 0; i < n; i++) {
            float sum = 0.0F;
            for (int j = 0; j < n; j++) {
                sum += vector[j] * matrix[j][i];
            }
            result[i] = sum;
        }
        return result;
    }

    public float[] calculateY3Y1plusY2() {
        float [] vector = right;
        float[] y3y1 = calculateMatrixMultVector(y3, y1, new float[n]);
        for (int i = 0; i < n; i++) {
            vector[i] = y3y1[i] + y2[i];
        }
//        log.info("Y3y1+y2': " + Arrays.toString(vector));
        return vector;
    }

    public float getX() {
        x = multTwoVectors(left, right);
        return x;
    }

    public float[][] calculateB2() {
        return textAreaToMatrix(controller.getTextAreaB2(), b2, "B2");
    }

    private float[][] textAreaToMatrix(TextArea textAreaB2, float[][] matrix, String matrixName) {
        String text = LoadTextService.read(textAreaB2);
        String[] numbers = text.split("[\\s\n]+");
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Float.parseFloat(numbers[k++]);
            }
        }
//        log.info(matrixName + Arrays.deepToString(matrix));
        return matrix;
    }

    private float[] textAreaToVector(TextArea textAreaB2, float[] vector, String vectorName) {
        String text = LoadTextService.read(textAreaB2);
        String[] numbers = text.split("[\\s\n]+");
        int k = 0;
        for (int i = 0; i < n; i++) {
            vector[i] = Float.parseFloat(numbers[i]);
        }
//        log.info(vectorName + Arrays.toString(vector));
        return vector;
    }


    public float[][] calculateA2() {
        return textAreaToMatrix(controller.getTextAreaA2(), a2, "A2");
    }

    public float[] calculateB1() {
        return textAreaToVector(controller.getTextAreaB1(), b1, "b1");
    }

    public float[] calculateC1() {
        return textAreaToVector(controller.getTextAreaC1(), c1, "c1");
    }

    public float[][] calculateA1() {
        return textAreaToMatrix(controller.getTextAreaA1(), a1, "A1");
    }

    public float[][] calculateA() {
        return textAreaToMatrix(controller.getTextAreaA(), a, "A");
    }


}
