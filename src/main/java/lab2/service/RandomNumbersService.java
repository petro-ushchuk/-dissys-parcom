package lab2.service;

import java.security.SecureRandom;

public class RandomNumbersService {

    private static SecureRandom rand = new SecureRandom();
    final String str = System.lineSeparator();

    public String generateMatrix(int n) {
        float[][] matrix = new float[n][n];
        SecureRandom rand = new SecureRandom();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = rand.nextInt(10);
            }

        }
        return asString(matrix);
    }

    public String generateVector(int n) {
        float[] vector = new float[n];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = rand.nextInt(10);
        }
        return asString(vector);
    }

    public String asString(float[] vector) {
        StringBuilder stringBuffer = new StringBuilder();
        for (float v : vector) {
            stringBuffer.append(v).append(str);
        }
        return stringBuffer.toString();
    }

    public String asString(float[][] matrix) {
        StringBuilder stringBuffer = new StringBuilder();
        for (float[] floats : matrix) {
            for (int j = 0; j < matrix.length; j++) {
                stringBuffer.append(floats[j]).append('\t');
            }
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }


}
