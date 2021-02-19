package pl.isbrandt.server.model;

public class GameLimitException extends Exception {

    public GameLimitException() {}

    public GameLimitException(String errorMessage) {
        super(errorMessage);
    }
}
