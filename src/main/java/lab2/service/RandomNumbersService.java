package lab2.service;

import javafx.scene.control.TextField;

import java.security.SecureRandom;

public class RandomNumbersService {

    private static SecureRandom rand = new SecureRandom();
    final String str = System.lineSeparator();

    public String generateMatrix(int n) {
        double[][] matrix = new double[n][n];
        SecureRandom rand = new SecureRandom();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = rand.nextInt(10);
            }

        }
        return asString(matrix);
    }



    public String generateVector(int n) {
        double[] vector = new double[n];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = rand.nextInt(10);
        }
        return asString(vector);
    }

    public String asString(double[] vector) {
        StringBuilder stringBuffer = new StringBuilder();
        for (double v : vector) {
            stringBuffer.append(v).append(str);
        }
        return stringBuffer.toString();
    }

    public String asString(double[][] matrix) {
        StringBuilder stringBuffer = new StringBuilder();
        for (double[] doubles : matrix) {
            for (int j = 0; j < matrix.length; j++) {
                stringBuffer.append(doubles[j]).append('\t');
            }
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }


}
