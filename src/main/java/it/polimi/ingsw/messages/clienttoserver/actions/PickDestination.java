package it.polimi.ingsw.messages.clienttoserver.actions;

import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.messages.clienttoserver.actions.Action;
import it.polimi.ingsw.model.player.DiningRoom;

/**
 * Class PickDestination is a user action used to send the destination chosen by the user
 */
public class PickDestination implements UserAction {
    private Action action;
    private Object destination;
    private Island chosenIsland;
    private DiningRoom diningRoom;

    /**
     * Constructor of the class when the user chooses an island
     * @param island island
     */
    public PickDestination(Island island) {
        this.action = Action.PICK_ISLAND;
        this.chosenIsland = island;
        this.diningRoom = null;
    }

    /**
     * Constructor of the class when the user chooses the dining room
     * @param dR player's dining room
     */
    public PickDestination(DiningRoom dR) {
        this.chosenIsland = null;
        this.diningRoom = dR;
    }

    public Object getDestination() {
        if(chosenIsland != null) {
            return chosenIsland;
        }
        else if (diningRoom != null) {
            return diningRoom;
        }
        return null;
    }

    public Action getAction() {
        return action;
    }

    public Island getChosenIsland() {
        return chosenIsland;
    }

    public DiningRoom getDiningRoom() {
        return diningRoom;
    }
}
