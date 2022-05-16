package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.messages.clienttoserver.actions.UserAction;

import java.io.Serializable;

public class SerializedMessage implements Serializable {
    public final Message message;
    public final UserAction userAction;

    public SerializedMessage(Message message) {
        this.message = message;
        this.userAction = null;
    }

    public SerializedMessage(UserAction userAction) {
        this.message = null;
        this.userAction = userAction;
    }
}
