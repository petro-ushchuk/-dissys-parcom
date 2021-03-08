package lab2.model;

import javafx.scene.control.TextArea;
import lab2.controller.ComputerController;
import lab2.service.LoadTextService;

import java.io.Serializable;
import java.util.Arrays;

//import static lab2.Main.log;

public class Compute implements Serializable {
    private int n;
    private float[][] a;
    private float[] b;
    //   -----   y1=A*b  -----
    private float[] y1;

    private float[][] a1;
    private float[] b1;
    private float[] c1;
    //   -----   y2=A1(b1-24c1)  -----
    private float[] b1minus24c1;
    private float[] y2;

    private float[][] a2;
    private float[][] b2;
    private float[][] c2;
    //  -----   Y3=A2(B2+24C2)  -----
    private float[][] b2plus24c2t;
    private float[][] y3;
    // ------ x divided into two parts: LEFT (y2'Y3y1 + y1'), RIGHT(Y3y1+y2)
    // vector-row
    private float[] left;
    // vector-column
    private float[] right;
    //x - number
    private float x;

    private transient volatile ComputerController controller;


    public Compute(ComputerController computerController, int n) {
        this.n = n;
        a = new float[n][n];
        b = new float[n];
        a1 = new float[n][n];
        b1 = new float[n];
        c1 = new float[n];
        a2 = new float[n][n];
        b2 = new float[n][n];
        c2 = new float[n][n];
        y3 = new float[n][n];
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
        y1 = calculateMatrixMultVector(a, b);
//        log.info("y1: " + Arrays.toString(y1));
        return y1;
    }

    public static float[] calculateMatrixMultVector(float[][] matrix, float[] vector) {
        float[] result = new float[matrix.length];
        for (int i = 0; i < vector.length; i++) {
            result[i] = multTwoVectors(matrix[i], vector);
        }
        return result;
    }

    public static float multTwoVectors(float[] matrixRow, float[] vector) {
        float sum = 0.0F;
        for (int i = 0; i < vector.length; i++) {
            sum += matrixRow[i] * vector[i];
        }
        return sum;
    }


    public float[] calculateY2() {
        y2 = calculateMatrixMultVector(a1, b1minus24c1);
//        log.info("y2: " + Arrays.toString(y2));
        return y2;
    }

    public float[][] B2plus24C2T() {
        float[][] matrix = staticB224c2(b2, c2);
        b2plus24c2t = matrix;
//        log.info("B2+24*C2: " + Arrays.deepToString(matrix));
        return matrix;
    }

    public static float[][] staticB224c2(float [][] b2, float [][] c2) {
        int n = b2.length;
        float[][] matrix = new float[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[j][i] = b2[i][j] + 24 * c2[i][j];
            }
        }
        return matrix;
    }

    public float[] b1Minus24C1() {
        b1minus24c1 = staticB124C1(b1, c1);
        //        log.info("b1-24*c1: " + Arrays.toString(vector));
        return b1minus24c1;
    }

    public static float[] staticB124C1(float [] b1, float[] c1) {
        float[] vector = new float[b1.length];
        for (int i = 0; i < b1.length; i++) {
            vector[i] = b1[i] - 24 * c1[i];
        }
        return vector;
    }

    public float[][] calculateY3() {
        calculateMatrixMultMatrix(a2, b2plus24c2t, y3);
//        log.info("Y3: " + Arrays.deepToString(y3));
        return y3;
    }

    public static float[][] staticMatrxMultMatrx(float[][] left, float[][] right) {
        return calculateMatrixMultMatrix(right, left, new float[left.length][left.length]);
    }

    public static float[][] calculateMatrixMultMatrix(float[][] left, float[][] right, float[][] result) {
        for (int i = 0; i < left.length; i++) {
            for (int j = 0; j < left.length; j++) {
                result[i][j] = multTwoVectors(left[i], right[j]);
            }
        }
        return result;
    }


    public float[] calculateY2Y3Y3y1PlusY1() {
        float[] y2y3 = calculateVectorMultMatrix(y2, y3);
        float[] y2y3y3 = calculateVectorMultMatrix(y2y3, y3);
        float y2y3y3y1 = multTwoVectors(y2y3y3, y1);
        left = vectorAddNumber(y2y3y3y1, y1);
//        log.info("y2Y3^2y1+y1': " + Arrays.toString(matrix));
        return left;
    }

    public static float[] vectorAddNumber(float y2y3y3y1, float[] matrix) {
        float [] result = new float[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            result[i] = y2y3y3y1 + matrix[i];
        }
        return result;
    }

    public static float[] calculateVectorMultMatrix(float[] vector, float[][] matrix) {
        int n = vector.length;
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
        float[] y3y1 = calculateMatrixMultVector(y3, y1);
        right = vectorAddVector(y2, y3y1);
//        log.info("Y3y1+y2': " + Arrays.toString(vector));
        return right;
    }

    public static float[] vectorAddVector(float[] y2, float[] y3y1) {
        float [] vector = new float[y2.length];
        for (int i = 0; i < y2.length; i++) {
            vector[i] = y3y1[i] + y2[i];
        }
        return vector;
    }

    public float getX() {
        x = multTwoVectors(left, right);
        return x;
    }

    public float[][] calculateB2() {
        return textAreaToMatrix(controller.getTextAreaB2(), b2);
    }

    private float[][] textAreaToMatrix(TextArea textAreaB2, float[][] matrix) {
        String text = textAreaB2.getText();
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

    private float[] textAreaToVector(TextArea textAreaB2, float[] vector) {
        String text = textAreaB2.getText();
        String[] numbers = text.split("[\\s\n]+");
        int k = 0;
        for (int i = 0; i < n; i++) {
            vector[i] = Float.parseFloat(numbers[i]);
        }
//        log.info(vectorName + Arrays.toString(vector));
        return vector;
    }


    public float[][] calculateA2() {
        return textAreaToMatrix(controller.getTextAreaA2(), a2);
    }

    public float[] calculateB1() {
        return textAreaToVector(controller.getTextAreaB1(), b1);
    }

    public float[] calculateC1() {
        return textAreaToVector(controller.getTextAreaC1(), c1);
    }

    public float[][] calculateA1() {
        return textAreaToMatrix(controller.getTextAreaA1(), a1);
    }

    public float[][] calculateA() {
        return textAreaToMatrix(controller.getTextAreaA(), a);
    }

    public int getN() {
        return n;
    }
}
