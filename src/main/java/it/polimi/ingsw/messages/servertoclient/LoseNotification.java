package it.polimi.ingsw.messages.servertoclient;

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
