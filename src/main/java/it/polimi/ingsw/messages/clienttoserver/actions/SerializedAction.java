package it.polimi.ingsw.messages.clienttoserver.actions;


import java.io.Serializable;

public class SerializedAction implements Serializable {
    private UserAction userAction;

    public SerializedAction(UserAction userAction) {
        this.userAction = userAction;
    }

    public UserAction getUserAction() {
        return userAction;
    }
}
