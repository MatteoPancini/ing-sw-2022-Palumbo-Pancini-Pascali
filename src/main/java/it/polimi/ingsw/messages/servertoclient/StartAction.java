package it.polimi.ingsw.messages.servertoclient;

public class StartAction implements Answer {
    String startMessage;

    public StartAction() {
        this.startMessage = "It's your turn :)";

    }
    @Override
    public String getMessage() {
        return startMessage;
    }
}
