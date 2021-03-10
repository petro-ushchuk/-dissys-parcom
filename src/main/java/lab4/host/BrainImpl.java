package lab4.host;

import lab4.Brain;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static lab4.host.ComputeHost.*;


public class BrainImpl extends UnicastRemoteObject implements Brain {

    private float [][] y3;
    private float [] y1;

    public BrainImpl() throws RemoteException {
    }

    @Override
    public float[] calculateB(int n) throws RemoteException {
        return getB(n);
    }

    @Override
    public float[] calculateY1(float[][] a) throws RemoteException {
        int n = a.length;
        float[] result = new float[n];
        float[] b = getB(n);
        for (int i = 0; i < n; i++) {
            result[i] = multTwoVectors(a[i], b);
        }
        y1 = result;
        return result;
    }

    @Override
    public float[] calculateY2(float[] b1, float[] c1, float[][] a1) throws RemoteException {
        return calculateMatrixMultVector(a1, staticB124C1(b1, c1));
    }

    @Override
    public float[][] calculateY3(float[][] b2, float[][] a2) throws RemoteException {
        int n = b2.length;
        y3 = new float[n][n];
        return calculateMatrixMultMatrix(a2, staticB224c2(b2, calculateC2(n)), y3);
    }

    @Override
    public float[][] calculateC2(int n) throws RemoteException {
        return getC2(n);
    }

    @Override
    public float[] calculateRight(float[] y2) throws RemoteException {
        float[] vector = new float[y1.length];
        float[] y3y1 = calculateMatrixMultVector(y3, y1);
        for (int i = 0; i < y1.length; i++) {
            vector[i] = y3y1[i] + y2[i];
        }
        return vector;
    }

}
