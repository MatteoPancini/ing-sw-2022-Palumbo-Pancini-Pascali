package it.polimi.ingsw.messages.servertoclient;

/**
 * Message used to notify the client that the action phase is ended
 */
public class EndAction implements Answer {
    String endMessage;

    public EndAction() {
        this.endMessage = "Turn finished!";
    }
    @Override
    public String getMessage() {
        return endMessage;
    }
}
