package lab4;

import lab2.model.Compute;

import java.rmi.Remote;
import java.util.concurrent.ExecutionException;

public interface Brain extends Remote {

    float[] calculateLeft(Compute compute);

}
