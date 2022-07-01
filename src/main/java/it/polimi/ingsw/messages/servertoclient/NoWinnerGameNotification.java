package it.polimi.ingsw.messages.servertoclient;

/**
 * Message used to notify the client that game is ended without a winner (someone leaves the game or server goes down)
 */
public class NoWinnerGameNotification implements Answer {

    public NoWinnerGameNotification() {}

    @Override
    public Object getMessage() {
        return null;
    }
}
