package lab4.host;

import lab4.Brain;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import static lab2.model.Compute.*;
public class BrainImpl extends UnicastRemoteObject implements Brain {
    protected BrainImpl() throws RemoteException {
    }

    @Override
    public float[] calculateB(int n) throws RemoteException {
        return getB(n, new float[n]);
    }

    @Override
    public float[] calculateY1(float [][] a, float [] b) throws RemoteException {
        float[] result = new float[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = multTwoVectors(a[i], b);
        }
        return result;
    }

    @Override
    public float[][] calculateC2(int n) throws RemoteException {
        float [][] c2 = new float[n][n];
        return getC2(n, c2);
    }

    @Override
    public float[] calculateRight(float[] y1, float[] y2, float[][] y3) throws RemoteException {
        float [] vector = new float[y1.length];
        float[] y3y1 = calculateMatrixMultVector(y3, y1);
        for (int i = 0; i < y1.length; i++) {
            vector[i] = y3y1[i] + y2[i];
        }
        return vector;
    }

}
