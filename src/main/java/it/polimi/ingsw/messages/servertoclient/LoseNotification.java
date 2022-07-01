package it.polimi.ingsw.messages.servertoclient;

/**
 * Message used to notify a client that has lost
 */
public class LoseNotification implements Answer {
    String winnerNickname;

    public LoseNotification(String winnerNickname) {
        this.winnerNickname = winnerNickname;
    }

    @Override
    public String getMessage() {
        return winnerNickname;
    }
}
