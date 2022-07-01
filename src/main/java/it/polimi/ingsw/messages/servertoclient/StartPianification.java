package it.polimi.ingsw.messages.servertoclient;

/**
 * Message used to notify a player that their pianification phase has started
 */
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
