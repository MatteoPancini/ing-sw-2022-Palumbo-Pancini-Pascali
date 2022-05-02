package it.polimi.ingsw.messages.clienttoserver.actions;

import it.polimi.ingsw.model.enumerations.Action;

public class PickCloud implements UserAction {
    private Action action;
    private int cloudID;
    public PickCloud() {
        this.action = Action.PICK_CLOUD;
    }

    public PickCloud(int ID) {
        this.action = Action.PICK_CLOUD;
        this.cloudID = ID;
    }

    public Action getAction() {
        return action;
    }

    public int getCloudID() {
        return cloudID;
    }
}
