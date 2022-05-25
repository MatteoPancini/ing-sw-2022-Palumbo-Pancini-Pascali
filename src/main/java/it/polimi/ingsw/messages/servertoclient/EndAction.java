package it.polimi.ingsw.messages.servertoclient;

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
