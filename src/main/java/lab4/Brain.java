package lab4;

import lab2.model.Compute;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Brain extends Remote{

    float[] calculateLeft(Compute compute) throws RemoteException;
    float[] calculateY1(Compute compute) throws RemoteException;
    float[] calculateB(Compute compute) throws RemoteException;
    float[] calculateC2(Compute compute) throws RemoteException;
    float[] calculateY2(Compute compute) throws RemoteException;
    float [] calculateB1C1(Compute compute) throws RemoteException;
}
