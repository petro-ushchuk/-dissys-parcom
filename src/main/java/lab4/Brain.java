package lab4;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Brain extends Remote {

    float[] calculateB(int n) throws RemoteException;

    float[] calculateY1(float[][] a) throws RemoteException;

    float[] calculateY2(float[] b1, float[] c1, float[][]a1) throws RemoteException;

    float[][] calculateC2(int n) throws RemoteException;

    float[] calculateRight(float[] y2) throws RemoteException;

    float[][] calculateY3(float[][] b2, float[][] a2) throws RemoteException;
}
