package lab2.service;

import javafx.scene.control.TextField;

import java.security.SecureRandom;

public class RandomNumbersService {

    public String generateMatrix(TextField textAreaN) {
        Integer n = getInteger(textAreaN);
        if (n == null) return "";
        Double[][] matrix = new Double[n][n];
        SecureRandom rand = new SecureRandom();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = (double) rand.nextInt(10);
            }

        }
        return asString(matrix);
    }

    private Integer getInteger(TextField textAreaN) {
        int n = 0;
        String textN = textAreaN.getText();
        if(textN.isEmpty()) {
            textAreaN.requestFocus();
            return null;
        }
        try{
            n = Integer.parseInt(textN);
        }catch (NumberFormatException e){
            textAreaN.requestFocus();
            return null;
        }
        return n;
    }

    public String generateVector(TextField textAreaN) {
        Integer n = getInteger(textAreaN);
        Double[] vector = new Double[n];
        SecureRandom rand = new SecureRandom();
        for (int i = 0; i < vector.length; i++) {
            vector[i] = (double) rand.nextInt(10);

        }
        return asString(vector);
    }

    public String asString(Double [] vector) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < vector.length; i++) {
            stringBuilder.append(vector[i]).append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    public String asString(Double [][] matrix) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                stringBuilder.append(matrix[i][j]).append(' ');
            }
            stringBuilder.replace(stringBuilder.length(), stringBuilder.length(), System.lineSeparator());
        }
        return stringBuilder.toString();
    }


}
