package lab4;

import lab2.model.Compute;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class BrainImpl extends UnicastRemoteObject implements Brain {
    protected BrainImpl() throws RemoteException {
    }

    @Override
    public float[] calculateB(int n) throws RemoteException {
        float [] b = new float[n];
        for (int i = 0; i < n; i += 2) {
            b[i] = 24.0F / ((i + 1) * (i + 1) + 4);
        }
        for (int i = 1; i < n; i += 2) {
            b[i] = 24.0F;
        }
//        log.info("b: " + Arrays.toString(b));
        return b;
    }

    @Override
    public float[] calculateY1(float [][] a, float [] b) throws RemoteException {
        return Compute.calculateMatrixMultVector(a, b);
    }

    @Override
    public float[][] calculateC2(int n) throws RemoteException {
        float [][] c2 = new float[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c2[i][j] = 24.0F / ((i + 1) + 3 * (j + 1) * (j + 1));
            }
        }
        return c2;
    }

    @Override
    public float[] calculateRight(float[] y1, float[] y2, float[][] y3) throws RemoteException {
        float [] vector = new float[y1.length];
        float[] y3y1 = Compute.calculateMatrixMultVector(y3, y1);
        for (int i = 0; i < y1.length; i++) {
            vector[i] = y3y1[i] + y2[i];
        }
        return vector;
    }
}
