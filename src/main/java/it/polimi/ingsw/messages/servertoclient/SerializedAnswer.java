package it.polimi.ingsw.messages.servertoclient;

import java.io.Serializable;

/**
 * Message used to serialize an answer to be sent
 */
public class SerializedAnswer implements Serializable {
    private Answer serverAnswer;

    public void setServerAnswer(Answer answer) {
        serverAnswer = answer;
    }

    public Answer getServerAnswer() {
        return serverAnswer;
    }
}