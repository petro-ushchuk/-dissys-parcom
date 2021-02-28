package lab2.model;

public class Matrix {
    private final String name;
    private Double[][] matrix;

    public Matrix(Double[][] matrix, String name) {
        this.matrix = matrix;
        this.name = name;
    }

    public Double[][] getMatrix() {
        return matrix;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                stringBuilder.append(' ').append(matrix[i][j]);
            }
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}
