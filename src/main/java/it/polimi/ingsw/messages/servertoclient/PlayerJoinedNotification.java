package it.polimi.ingsw.messages.servertoclient;

/**
 * Message sent in broadcast to the lobby to notify a new client join
 */
public class PlayerJoinedNotification implements Answer {
    private String joiningNotification;


    public PlayerJoinedNotification(String joiningNotification) {
        this.joiningNotification = joiningNotification;
    }

    @Override
    public Object getMessage() {
        return joiningNotification;
    }
}
