package lab2.model;

import javafx.scene.control.TextArea;
import lab2.controller.ComputerController;

import java.io.Serializable;

//import static lab2.Main.log;

public class Compute implements Serializable {
    private int n;

    private transient volatile ComputerController controller;

    public Compute(ComputerController computerController, int n) {
        this.n = n;
        controller = computerController;
    }

    public float[] calculateB() {
        return getB(n);
    }

    public static float[] getB(int n) {
        float[] b = new float[n];
        for (int i = 0; i < n; i += 2) {
            b[i] = 24.0F / ((i + 1) * (i + 1) + 4);
        }
        for (int i = 1; i < n; i += 2) {
            b[i] = 24.0F;
        }
        return b;
    }

    public float[][] calculateC2() {
        return getC2(n);
    }

    public static float[][] getC2(int n) {
        float[][] c2 = new float[n][n];
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
        return textAreaToMatrix(controller.getTextAreaB2(), n);
    }

    public float[][] calculateA2() {
        return textAreaToMatrix(controller.getTextAreaA2(), n);
    }

    public float[] calculateB1() {
        return textAreaToVector(controller.getTextAreaB1(), n );
    }

    public float[] calculateC1() {
        return textAreaToVector(controller.getTextAreaC1(), n);
    }

    public float[][] calculateA1() {
        return textAreaToMatrix(controller.getTextAreaA1(), n);
    }

    public float[][] calculateA() {
        return textAreaToMatrix(controller.getTextAreaA(), n);
    }

    private static float[][] textAreaToMatrix(TextArea textAreaB2, int n) {
        float[][] matrix = new float[n][n];
        String text = textAreaB2.getText();
        String[] numbers = text.split("[\\s\n]+");
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Float.parseFloat(numbers[k++]);
            }
        }
        return matrix;
    }

    private static float[] textAreaToVector(TextArea textAreaB2, int n) {
        float[] vector = new float[n];
        String text = textAreaB2.getText();
        String[] numbers = text.split("[\\s\n]+");
        for (int i = 0; i < n; i++) {
            vector[i] = Float.parseFloat(numbers[i]);
        }
        return vector;
    }
}
