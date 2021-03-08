package lab4;

import lab2.model.Compute;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class BrainImpl extends UnicastRemoteObject implements Brain {
    protected BrainImpl() throws RemoteException {
    }


}
