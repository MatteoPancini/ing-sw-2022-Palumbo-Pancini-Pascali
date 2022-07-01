package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.messages.clienttoserver.actions.UserAction;

import java.io.Serializable;

/**
 * Class SerializedMessage is used to send either a message or a user action from client to server
 */
public class SerializedMessage implements Serializable {
    public final Message message;
    public final UserAction userAction;

    /**
     * Constructor of the class with a message
     * @param message message object
     */
    public SerializedMessage(Message message) {
        this.message = message;
        this.userAction = null;
    }

    /**
     * Constructor of the class with a user action
     * @param userAction user action
     */
    public SerializedMessage(UserAction userAction) {
        this.message = null;
        this.userAction = userAction;
    }
}
