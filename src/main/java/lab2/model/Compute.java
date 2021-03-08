package lab2.model;

import javafx.scene.control.TextArea;
import lab2.controller.ComputerController;

import java.io.Serializable;

//import static lab2.Main.log;

public class Compute implements Serializable {
    private int n;
    private float[][] a;
    private float[] b;
    private float[][] a1;
    private float[] b1;
    private float[] c1;
    private float[][] a2;
    private float[][] b2;
    private float[][] c2;

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
        controller = computerController;
    }

    public float[] calculateB() {
        return getB(n, b);
    }

    public static float[] getB(int n, float[] b) {
        for (int i = 0; i < n; i += 2) {
            b[i] = 24.0F / ((i + 1) * (i + 1) + 4);
        }
        for (int i = 1; i < n; i += 2) {
            b[i] = 24.0F;
        }
        return b;
    }

    public float[][] calculateC2() {
        return getC2(n, c2);
    }

    public static float[][] getC2(int n, float[][] c2) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c2[i][j] = 24.0F / ((i + 1) + 3 * (j + 1) * (j + 1));
            }
        }
        return c2;
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

    public static float[] staticB124C1(float [] b1, float[] c1) {
        float[] vector = new float[b1.length];
        for (int i = 0; i < b1.length; i++) {
            vector[i] = b1[i] - 24 * c1[i];
        }
        return vector;
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

    public static float[] vectorAddVector(float[] y2, float[] y3y1) {
        float [] vector = new float[y2.length];
        for (int i = 0; i < y2.length; i++) {
            vector[i] = y3y1[i] + y2[i];
        }
        return vector;
    }

    public float[][] calculateB2() {
        return textAreaToMatrix(controller.getTextAreaB2(), b2);
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

    private static float[][] textAreaToMatrix(TextArea textAreaB2, float[][] matrix) {
        String text = textAreaB2.getText();
        String[] numbers = text.split("[\\s\n]+");
        int k = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = Float.parseFloat(numbers[k++]);
            }
        }
        return matrix;
    }

    private static float[] textAreaToVector(TextArea textAreaB2, float[] vector) {
        String text = textAreaB2.getText();
        String[] numbers = text.split("[\\s\n]+");
        for (int i = 0; i < vector.length; i++) {
            vector[i] = Float.parseFloat(numbers[i]);
        }
        return vector;
    }

    public int getN() {
        return n;
    }
}
