package lab2.model;

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


    public Compute(Integer n) {
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
    }

    public Double[] calculateB(int n) {
        synchronized (b) {
            for (int i = 0; i < n; i += 2) {
                b[i] = 24.0 / (i * i + 4);
            }
            for (int i = 1; i < n; i += 2) {
                b[i] = 24.0;
            }
            return b;
        }
    }

    public Double[][] calculateC2(int n) {
        synchronized (C2) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    C2[i][j] = 24.0 / (i + 3 * j * j);
                }
            }
            return C2;
        }
    }

    public Double[] calculateY1() {
        return calculateMatrixMultVector(A, b1, y1);
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
        return calculateMatrixMultVector(A1, b1Minus24C1(), y2);
    }

    private Double[][] B2plus24C2T() {
        Double[][] matrix = new Double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[j][i] = B2[i][j] - 24 * C2[i][j];
            }
        }
        return matrix;
    }

    private Double[] b1Minus24C1() {
        Double[] vector = new Double[n];
        for (int i = 0; i < n; i++) {
            vector[i] = b1[i] - 24 * c1[i];
        }
        return vector;
    }

    private Double[][] calculateY3() {
        return calculateMatrixMultMatrix(A2, B2plus24C2T(), Y3);
    }

    private Double[][] calculateMatrixMultMatrix(Double[][] left, Double[][] right, Double[][] result) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = multTwoVectors(left[i], right[j]);
            }
        }
        return result;
    }


    private Double[] calculateY2Y3Y3y1PlusY1() {
        Double[] y2y3 = calculateVectorMultMatrix(y2, Y3);
        Double[] y2y3y3 = calculateVectorMultMatrix(y2y3, Y3);
        Double y2y3y3y1 = multTwoVectors(y2y3y3, y1);
        for (int i = 0; i < n; i++) {
            LEFT[i] = y2y3y3y1 + y1[i];
        }
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

    private Double[] calculateY3Y1plusY2() {
        Double[] y3y1 = calculateMatrixMultVector(Y3, y1, new Double[n]);
        for (int i = 0; i < n; i++) {
            RIGHT[i] = y3y1[i] + y2[i];
        }
        return RIGHT;
    }

    public Double getXmatrix() {
        return x = multTwoVectors(LEFT, RIGHT);
    }

}
