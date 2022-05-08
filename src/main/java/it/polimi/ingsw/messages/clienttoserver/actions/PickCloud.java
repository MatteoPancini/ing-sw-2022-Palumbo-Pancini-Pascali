package it.polimi.ingsw.messages.clienttoserver.actions;

import it.polimi.ingsw.model.board.CloudTile;
import it.polimi.ingsw.model.enumerations.Action;

public class PickCloud implements UserAction {
    private Action action;
    private int chosenCloud;
    public PickCloud() {
        this.action = Action.PICK_CLOUD;
    }

    public PickCloud(int cloudID) {
        this.action = Action.PICK_CLOUD;
        this.chosenCloud = cloudID;
    }

    public Action getAction() {
        return action;
    }

    public int getChosenCloud() {
        return chosenCloud;
    }
}
