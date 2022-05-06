package it.polimi.ingsw.messages.servertoclient;

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

    public String getConnectionAnswer() {
        return connectionAnswer;
    }

    @Override
    public Object getMessage() {
        return connectionAnswer;
    }
}
