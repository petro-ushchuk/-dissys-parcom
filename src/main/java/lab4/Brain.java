package lab4;

import lab2.model.Compute;

import java.rmi.Remote;
import java.rmi.RemoteException;

@FunctionalInterface
public interface Brain extends Remote{

    float[] calculateLeft(Compute compute) throws RemoteException;
}
