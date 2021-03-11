package lab5;


import com.aparapi.Kernel;
import com.aparapi.Range;
import lab2.model.Compute;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static lab2.model.Compute.getB;

public class GPUMultithreadComputing {


    public static void gpu(int n) throws InterruptedException {
//        final float[][] b2 = {{4, 8, 9}, {4, 0, 4}, {1, 1, 4}};
//        final float[][] a2 = {{8, 0, 9}, {4, 7, 3}, {0, 7, 3}};
//        final float[] b1 = {9, 8, 4};
//        final float[] c1 = {7, 9, 9};
//        final float[][] a1 = {{2, 9, 4}, {2, 3, 7}, {7, 1, 8}};
//        final float[][] a = {{9, 8, 3}, {9, 5, 6}, {3, 0, 9}};
//        final float[] b = {5, 24, 2};
//        final float[][] c2 = {{6, 2, 1}, {5, 2, 1}, {4, 2, 1}};
        final float[][] b2 = generateMatrix(n);
        final float[][] a2 = generateMatrix(n);
        final float[] b1 = generateVector(n);
        final float[] c1 = generateVector(n);
        final float[][] a1 = generateMatrix(n);
        final float[][] a = generateMatrix(n);
        final float[] b = generateVector(n);
        final float[][] c2 = generateMatrix(n);
        long start = System.currentTimeMillis();
        float[] y1 = new float[n];
        float[] y2= new float[n];
        float[][] y3= new float[n][n];
        Thread d = new Thread( () -> calculateMatrixMultVector(a, b, y1));
        d.start();
        Thread d1 = new Thread( () -> calculateMatrixMultVector(a1, staticB124C1(b1, c1), y2));
        d1.start();
        Thread d2 = new Thread( () -> calculateMatrixMultMatrix(staticB224c2(b2, c2), a2, y3));
        d2.start();
        d.join();
        d1.join();
        d2.join();
        System.out.println(System.currentTimeMillis() - start);

        System.out.println(Arrays.toString(y1));
        System.out.println(Arrays.toString(y2));
        System.out.println(Arrays.deepToString(y3));
    }




    public static void main(String[] args) throws InterruptedException {
        gpu(3);
    }


    public static float[][] generateMatrix(int n) {
        float[][] matrix = new float[n][n];
        SecureRandom rand = new SecureRandom();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = rand.nextInt(10);
            }
        }
        return matrix;
    }

    public static float[] generateVector(int n) {
        float[] vector = new float[n];
        SecureRandom rand = new SecureRandom();
        for (int i = 0; i < vector.length; i++) {
            vector[i] = rand.nextInt(10);
        }
        return vector;
    }

    public static float[] getC2(int n) {
        float[] c2 = new float[n];
        for (int i = 0, k = 0; k < n; i++) {
            for (int j = 0; k < n; j++, k++) {
                c2[k] = (24.0F / ((i + 1) + 3 * (j + 1) * (j + 1)));
            }
        }
        return c2;
    }

    public static float[] calculateMatrixMultVector(float[][] matrix, float[] vector, float [] result) {
        for (int i = 0; i < vector.length; i++) {
            result[i] = multTwoVectors(matrix[i], vector);
        }
        return result;
    }

    public static float multTwoVectors(float[] matrixRow, float[] vector) {
        final float[] sum = new float[matrixRow.length];
        float sum1 = 0;
        Kernel kernel = new Kernel() {
            @Override
            public void run() {
                int i = getGlobalId();
                sum[i] = matrixRow[i] * vector[i];
            }
        };
        Range range = Range.create(matrixRow.length);
        kernel.execute(range);

        for (float i : sum) {
            sum1 += i;
        }
        return sum1;
    }

    private static float[] staticB124C1(float[] b1, float[] c1) {
        float[] vector = new float[b1.length];
        Kernel kernel = new Kernel() {
            @Override
            public void run() {
                int i = getGlobalId();
                vector[i] = b1[i] - 24 * c1[i];
            }
        };
        Range range = Range.create(b1.length);
        kernel.execute(range);
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

    private static float[][] staticB224c2(float[][] b2, float[][] c2) {
        int n = b2.length;
        float[][] matrix = new float[n][n];
        for (int i = 0; i < n; i++) {
            int finalI = i;
            Kernel kernel = new Kernel() {
                @Override
                public void run() {
                    int j = getGlobalId();
                    matrix[j][finalI] = b2[finalI][j] + 24 * c2[finalI][j];
                }
            };
            Range range = Range.create(b2.length);
            kernel.execute(range);
        }
        return matrix;
    }
}
