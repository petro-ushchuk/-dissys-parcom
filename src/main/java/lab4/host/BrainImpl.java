package lab4.host;

import lab4.Brain;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static lab2.model.Compute.*;
import static lab2.model.Compute.calculateMatrixMultVector;


public class BrainImpl extends UnicastRemoteObject implements Brain {

    public BrainImpl() throws RemoteException {
    }

    @Override
    public float[] calculateB(int n) throws RemoteException {
        return getB(n, new float[n]);
    }

    @Override
    public float[] calculateY1(float[][] a) throws RemoteException {
        int n = a.length;
        float[] result = new float[n];
        float[] b = getB(n, new float[n]);
        for (int i = 0; i < n; i++) {
            result[i] = multTwoVectors(a[i], b);
        }
        return result;
    }

    @Override
    public float[] calculateY2(float[] b1, float[] c1, float[][] a1) throws RemoteException {
        return calculateMatrixMultVector(a1, staticB124C1(b1, c1));
    }

    @Override
    public float[][] calculateY3(float[][] b2, float[][] a2) throws RemoteException {
        int n = b2.length;
        float[][] result = new float[n][n];
        return calculateMatrixMultMatrix(a2, staticB224c2(b2, calculateC2(n)), result);
    }

    @Override
    public float[][] calculateC2(int n) throws RemoteException {
        float[][] c2 = new float[n][n];
        return getC2(n, c2);
    }

    @Override
    public float[] calculateRight(float[] y1, float[] y2, float[][] y3) throws RemoteException {
        float[] vector = new float[y1.length];
        float[] y3y1 = calculateMatrixMultVector(y3, y1);
        for (int i = 0; i < y1.length; i++) {
            vector[i] = y3y1[i] + y2[i];
        }
        return vector;
    }

}
