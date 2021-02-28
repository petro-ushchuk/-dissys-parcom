package lab2.model;

import javafx.scene.control.TextArea;
import lab2.controller.ComputerController;
import lab2.service.LoadTextService;

import java.util.Arrays;

public class Compute {
    private final Integer n;
    private final Double[][] A;
    private final Double[] b;
    //   -----   y1=A*b  -----
    private final Double[] y1;

    private final Double[][] A1;
    private final Double[] b1;
    private final Double[] c1;
    //   -----   y2=A1(b1-24c1)  -----
    private final Double[] y2;

    private final Double[][] A2;
    private final Double[][] B2;
    private final Double[][] C2;
    //  -----   Y3=A2(B2+24C2)  -----
    private final Double[][] Y3;
    // ------ x divided into two parts: LEFT (y2'Y3y1 + y1'), RIGHT(Y3y1+y2)
    // vector-row
    private final Double[] LEFT;
    // vector-column
    private final Double[] RIGHT;
    //x - number
    private Double x;

    private ComputerController controller;


    public Compute(ComputerController computerController, Integer n) {
        this.n = n;
        A = new Double[n][n];
        b = new Double[n];
        y1 = new Double[n];
        A1 = new Double[n][n];
        b1 = new Double[n];
        c1 = new Double[n];
        y2 = new Double[n];
        A2 = new Double[n][n];
        B2 = new Double[n][n];
        C2 = new Double[n][n];
        Y3 = new Double[n][n];
        LEFT = new Double[n];
        RIGHT = new Double[n];
        controller = computerController;
    }

    public Double[] calculateB() {
        for (int i = 0; i < n; i += 2) {
            b[i] = 24.0 / (i * i + 4);
        }
        for (int i = 1; i < n; i += 2) {
            b[i] = 24.0;
        }
        controller.log("b calculated ... " + Arrays.deepToString(b));
        return b;
    }

    public Double[][] calculateC2() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C2[i][j] = 24.0 * (i + 3 * j * j);
            }
        }
        controller.log("C2 calculated ... " + Arrays.deepToString(C2));
        return C2;
    }

    public Double[] calculateY1() {
        calculateMatrixMultVector(A, b1, y1);
        controller.log("Y1 calculated ... " + Arrays.deepToString(y1));
        return y1;
    }

    private Double[] calculateMatrixMultVector(Double[][] matrix, Double[] vector, Double[] result) {
        for (int i = 0; i < n; i++) {
            result[i] = multTwoVectors(matrix[i], vector);
        }
        return result;
    }

    public Double multTwoVectors(Double[] matrixRow, Double[] vector) {
        Double sum = 0.0;
        for (int i = 0; i < n; i++) {
            sum += matrixRow[i] * vector[i];
        }
        return sum;
    }


    public Double[] calculateY2() {
        calculateMatrixMultVector(A1, b1Minus24C1(), y2);
        controller.log("y2 calculated ... " + Arrays.deepToString(y2));
        return y2;
    }

    public Double[][] B2plus24C2T() {
        Double[][] matrix = new Double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[j][i] = B2[i][j] - 24 * C2[i][j];
            }
        }
        controller.log("B2+24C2' calculated ... " + Arrays.deepToString(matrix));
        return matrix;
    }

    public Double[] b1Minus24C1() {
        Double[] vector = new Double[n];
        for (int i = 0; i < n; i++) {
            vector[i] = b1[i] - 24 * c1[i];
        }
        controller.log("b1-24c1 calculated ... " + Arrays.deepToString(vector));
        return vector;
    }

    public Double[][] calculateY3() {
        calculateMatrixMultMatrix(A2, B2plus24C2T(), Y3);
        controller.log("y2Y3^2y1+y1 calculated ... " + Arrays.deepToString(Y3));
        return Y3;
    }

    private Double[][] calculateMatrixMultMatrix(Double[][] left, Double[][] right, Double[][] result) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = multTwoVectors(left[i], right[j]);
            }
        }
        return result;
    }


    public Double[] calculateY2Y3Y3y1PlusY1() {
        Double[] y2y3 = calculateVectorMultMatrix(y2, Y3);
        Double[] y2y3y3 = calculateVectorMultMatrix(y2y3, Y3);
        Double y2y3y3y1 = multTwoVectors(y2y3y3, y1);
        for (int i = 0; i < n; i++) {
            LEFT[i] = y2y3y3y1 + y1[i];
        }
        controller.log("y2Y3^2y1+y1 calculated ... " + Arrays.deepToString(LEFT));
        return LEFT;
    }

    private Double[] calculateVectorMultMatrix(Double[] vector, Double[][] matrix) {
        Double[] result = new Double[n];
        for (int i = 0; i < n; i++) {
            Double sum = 0.0;
            for (int j = 0; j < n; j++) {
                sum += vector[j] * matrix[j][i];
            }
            result[i] = sum;
        }
        return result;
    }

    public Double[] calculateY3Y1plusY2() {
        Double[] y3y1 = calculateMatrixMultVector(Y3, y1, new Double[n]);
        for (int i = 0; i < n; i++) {
            RIGHT[i] = y3y1[i] + y2[i];
        }
        controller.log("Y3y1+y2 calculated ... " + Arrays.deepToString(RIGHT));
        return RIGHT;
    }

    public Double getX() {
        return x = multTwoVectors(LEFT, RIGHT);
    }

    public void calculateB2() {
        textAreaToMatrix(controller.getTextAreaB2(), B2);
        controller.log("B2 calculated ... " + Arrays.deepToString(B2));
    }

    private Double[][] textAreaToMatrix(TextArea textAreaB2, Double[][] matrix) {
        LoadTextService textService = new LoadTextService();
        String text = textService.read(textAreaB2);
        String[] numbers = text.split("[\\s\n]+");
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Double.parseDouble(numbers[k++]);
            }
        }
        return matrix;
    }

    private Double[] textAreaToVector(TextArea textAreaB2, Double[] vector) {
        LoadTextService textService = new LoadTextService();
        String text = textService.read(textAreaB2);
        String[] numbers = text.split("[\\s\n]+");
        int k = 0;
        for (int i = 0; i < n; i++) {
            vector[i] = Double.parseDouble(numbers[i]);
        }
        return vector;
    }


    public void calculateA2() {
        textAreaToMatrix(controller.getTextAreaA2(), A2);
        controller.log("A2 calculated ... " + Arrays.deepToString(A2));
    }

    public void calculateB1() {
        textAreaToVector(controller.getTextAreaB1(), b1);
        controller.log("b1 calculated ... " + Arrays.deepToString(b1));
    }

    public void calculateC1() {
        textAreaToVector(controller.getTextAreaC1(), c1);
        controller.log("c1 calculated ... " + Arrays.deepToString(c1));
    }

    public void calculateA1() {
        textAreaToMatrix(controller.getTextAreaA1(), A1);
        controller.log("A1 calculated ... " + Arrays.deepToString(A1));
    }

    public void calculateA() {
        textAreaToMatrix(controller.getTextAreaA(), A);
        controller.log("A calculated ... " + Arrays.deepToString(A));
    }
}
