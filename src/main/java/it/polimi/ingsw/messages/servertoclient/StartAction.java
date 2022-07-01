package it.polimi.ingsw.messages.servertoclient;

/**
 * Message used to notify a player that their action phase has started
 */
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
