package lab2.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Reader {
    private static Scanner inputReader = new Scanner(System.in);
    private static Scanner fileReader;

    public static Matrix readMatrix() {
        try {
            System.out.println("\tInput n: ");
            int n = inputReader.nextInt();
            while (n < 0) {
                System.err.println("n must be > 0");
                n = inputReader.nextInt();
            }
            System.out.printf("\tInput %d number for matrix[%d][%d]: ", n * n, n, n);
            Integer[][] matrix = new Integer[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; i++) {
                    matrix[i][j] = inputReader.nextInt();
                }
            }
            return null;//new Matrix(matrix);
        } catch (InputMismatchException e) {
            return readMatrix();
        }

    }

    public static Matrix readMatrixFromFile() {
        System.out.println("Input path to file [leave empty to back]: ");
        try {
            String input = inputReader.nextLine();
            if (input.length() == 0) {
                return null;
            }
            File file = new File(input);
            if (file.length() == 0) {
                System.err.println("File can't be empty");
                return readMatrixFromFile();
            }
            fileReader = new Scanner(file);
            Integer[][] matrix;

            int rows = 0;
            int columns = 0;
            while (fileReader.hasNextLine()) {
                ++rows;
                Scanner colReader = new Scanner(fileReader.nextLine());
                while (colReader.hasNextInt()) {
                    colReader.nextInt();
                    ++columns;
                }
            }
            if (rows*rows != columns) {
                System.err.println("It must be square matrix!");
                return readMatrixFromFile();
            }
            matrix = new Integer[rows][rows];

            fileReader.close();
            fileReader = new Scanner(file);
            for (int i = 0; i < matrix.length; ++i) {
                for (int j = 0; j < matrix.length; ++j) {
                    if (fileReader.hasNextInt()) {
                        matrix[i][j] = fileReader.nextInt();
                    }
                }
            }
            return null;// new Matrix(matrix);
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
            return readMatrixFromFile();
        } finally {
            if (fileReader != null)
                fileReader.close();
        }
    }
}
