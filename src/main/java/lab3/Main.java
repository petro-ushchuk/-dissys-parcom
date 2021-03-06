package lab3;

import java.security.SecureRandom;
import java.util.Arrays;

public class Main {
    private static SecureRandom random = new SecureRandom();
    private static int n = 2; //1018 max to 12 gb RAM
    private static long[][] a = new long[n][n];
    private static long[][] b = new long[n][n];
    private static long operationCounter = 0;

    private static void setUp() {
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n - i; j++) {
                a[i][j] = 1;
            }
            a[n - i - 1] = a[i];
            for (int j = 0; j < i + 1; j++) {
                b[i][j] = random.nextInt(20) + 1;
            }
        }
    }

    private static long[][][] multiplyMatrix(long[][] a, long[][] b) {
        operationCounter = 0;
        long[][][] c = new long[n][n][n + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    c[i][j][k + 1] = c[i][j][k] + a[i][k] * b[k][j];
                    operationCounter++;
                }
            }
        }
        return c;
    }

    private static long[][][] optimizedMultiply(final long[][] a, final long[][] b) {
        operationCounter = 0;
        long[][][] c = new long[n / 2 + n % 2][n][n + 1];
        for (int i = 0; i < n / 2 + n % 2; i++) {
            for (int j = 0; j < n - i; j++) {
                for (int k = j; k < n - i; k++) {
                    c[i][j][k + 1] = c[i][j][k] + a[i][k] * b[k][j];
                    operationCounter++;
                }
            }
        }

        return c;
    }

    private static long[][] normalize(long[][][] matrix) {
        long[][] normalized = new long[n][n];
        for (int i = 0; i < n / 2 + n % 2; i++) {
            for (int k = 0; k < n - i; k++) {
                normalized[i][k] = matrix[i][k][n - i];
                operationCounter++;
            }
            normalized[n - i - 1] = normalized[i];
            operationCounter++;
        }
        return normalized;
    }

    private static long[][] multiply(long[][] a, long[][] b) {
        operationCounter = 0;
        long[][] c = new long[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    c[i][j] = c[i][j] + a[i][k] * b[k][j];
                    operationCounter++;
                }
            }
        }
        return c;
    }

    public static void main(String[] args) {
        setUp();
        System.out.println("Matrix A:");
        printMatrix(a);
        System.out.println("Matrix B:");
        printMatrix(b);

        long[][] y = multiply(a, b);
        System.out.println("Matrix Y#Default algorithm\nOperations = " + operationCounter);
        printMatrix(y);
        System.gc();
        long[][][] z = multiplyMatrix(a, b);
        System.out.println("Matrix Y#Recursive algorithm\nOperations = " + operationCounter);
        long[][] l = normalize(z);
        System.out.println("After normalize operations = " + operationCounter);
        printMatrix(l);
        System.gc();
        long[][][] j = optimizedMultiply(a, b);
        System.out.println("Matrix Y#Optimized recursive algorithm\nOperations = " + operationCounter);
        long[][] k = normalize(j);
        System.out.println("After normalize operations = " + operationCounter);
        printMatrix(k);
        System.out.println("All matrix the same? " + (Arrays.deepEquals(y, l) && Arrays.deepEquals(y, k) ? "Yes" : "No"));
//        for (long[][]h : j) {
//            printMatrix(h);
//        }
    }

    private static void printMatrix(long[][] m) {
        StringBuilder sb = new StringBuilder();
        for (long[] ints : m) {
            sb.append(Arrays.toString(ints)).append(System.lineSeparator());
        }
        System.out.println(sb.toString());
    }
}
