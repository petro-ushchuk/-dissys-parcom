package lab4.host;

import java.io.Serializable;

public class ComputeHost implements Serializable {

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

    public static float[][] calculateMatrixMultMatrix(float[][] left, float[][] right, float[][] result) {
        for (int i = 0; i < left.length; i++) {
            for (int j = 0; j < left.length; j++) {
                result[i][j] = multTwoVectors(left[i], right[j]);
            }
        }
        return result;
    }
}
