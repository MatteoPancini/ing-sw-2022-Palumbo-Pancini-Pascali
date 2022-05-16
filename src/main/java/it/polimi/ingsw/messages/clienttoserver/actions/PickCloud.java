package it.polimi.ingsw.messages.clienttoserver.actions;

import it.polimi.ingsw.model.board.CloudTile;

public class PickCloud implements UserAction {
    private Action action;
    private CloudTile chosenCloud;
    public PickCloud() {
        this.action = Action.PICK_CLOUD;
    }

    public PickCloud(CloudTile cloudID) {
        this.action = Action.PICK_CLOUD;
        this.chosenCloud = cloudID;
    }

    public Action getAction() {
        return action;
    }

    public CloudTile getChosenCloud() {
        return chosenCloud;
    }
}
