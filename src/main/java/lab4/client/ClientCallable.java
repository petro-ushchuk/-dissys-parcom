package lab4.client;

import lab4.exception.BadConnectionException;

import java.util.concurrent.Callable;

public interface ClientCallable extends Callable<Client> {
    @Override
    Client call() throws BadConnectionException;
}
