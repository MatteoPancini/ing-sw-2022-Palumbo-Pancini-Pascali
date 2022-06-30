package it.polimi.ingsw.messages.servertoclient;

/**
 * Message used to notify the client about the connection creation
 */
public class ConnectionResult implements Answer {

    private final String connectionAnswer;
    private final boolean connectionCompleted;

    public ConnectionResult(String connectionAnswer, boolean connectionCompleted) {
        this.connectionAnswer = connectionAnswer;
        this.connectionCompleted = connectionCompleted;
    }

    public boolean isConnectionCompleted() {
        return connectionCompleted;
    }

    @Override
    public Object getMessage() {
        return connectionAnswer;
    }
}
