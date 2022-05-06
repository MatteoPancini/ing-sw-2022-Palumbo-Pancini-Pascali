package it.polimi.ingsw.messages.servertoclient;

public class PlayerJoinedNotification implements Answer {
    private String joiningNotification;


    public PlayerJoinedNotification(String joiningNotification) {
        this.joiningNotification = joiningNotification;
    }

    public String getJoiningNotification() {
        return joiningNotification;
    }

    @Override
    public Object getMessage() {
        return joiningNotification;
    }
}
