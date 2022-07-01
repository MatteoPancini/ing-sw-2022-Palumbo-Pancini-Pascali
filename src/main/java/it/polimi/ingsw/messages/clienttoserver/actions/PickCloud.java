package it.polimi.ingsw.messages.clienttoserver.actions;

import it.polimi.ingsw.model.board.CloudTile;

/**
 * Class PickCloud is a user action used to send the cloud chosen by the user
 */
public class PickCloud implements UserAction {
    private Action action;
    private CloudTile chosenCloud;

    /**
     * Constructor of the class
     * @param cloudID chosen cloud
     */
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
