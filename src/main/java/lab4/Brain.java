package lab4;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Brain extends Remote {

    float[] calculateB(int n) throws RemoteException;

    float[] calculateY1(float[][] a, float[] b) throws RemoteException;

    float[][] calculateC2(int n) throws RemoteException;

    float[] calculateRight(float[] y1, float[] y2, float[][] y3) throws RemoteException;
}
