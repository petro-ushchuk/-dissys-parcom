package lab3;

import java.security.SecureRandom;

public class Main {
    private static SecureRandom random = new SecureRandom();
    private static int n = 4;
    private static int[][] a = new int[n][n];
    private static int[][] b = new int[n][n];
    private static int operationCounter = 0;

    private static void setUp() {
        for (int i = 0; i < n; i++) {
            b[i][i] = random.nextInt(20);
            operationCounter++;
            for (int j = i; j < n - i; j++) {
                a[i][j] = 1;
                operationCounter++;
            }
            a[n - i - 1] = a[i];
        }
    }


    private static int[][] multiply(int [][] left, int [][] right){
        int[][] y = new int[n][n];
        for (int i = 0; i < y.length; i++) {
            for (int j = 0; j < y.length; j++) {
                y[i][j] = a[i][j] * b[i][j];
            }
        }
        return y;
    }

    public static void main(String[] args) {
        setUp();
        printMatrix(a);
        printMatrix(b);
        printMatrix(multiply(a,b));
    }

//    private static int [] wtf(){
//
//        for (int i=0; i<n;i++)
//        {
//            c[i]=0;
//            for (int j=0; j<n; j++)
//                c[i]=c[i]+a[i][j]*b[j];
//        }
//
//    }

    private static void printMatrix(int[][] m) {
        StringBuilder sb = new StringBuilder();
        for (int[] ints : m) {
            for (int j = 0; j < m.length; j++) {
                sb.append(ints[j]).append('\t');
            }
            sb.append(System.lineSeparator());
        }
        System.out.println(sb.toString());
    }
}
