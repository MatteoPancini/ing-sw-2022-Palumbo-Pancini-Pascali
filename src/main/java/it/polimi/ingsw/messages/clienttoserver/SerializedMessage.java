package it.polimi.ingsw.messages.clienttoserver;

import java.io.Serializable;

public class SerializedMessage implements Serializable {
    public final Message message;

    public SerializedMessage(Message message) {
        this.message = message;
    }
}
