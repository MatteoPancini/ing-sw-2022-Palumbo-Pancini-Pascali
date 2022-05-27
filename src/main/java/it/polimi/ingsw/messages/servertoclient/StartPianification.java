package it.polimi.ingsw.messages.servertoclient;

public class StartPianification implements Answer {
    private String startMessage;

    public StartPianification() {
        this.startMessage = "It's your turn :)";
    }
    @Override
    public String getMessage() {
        return startMessage;
    }
}
