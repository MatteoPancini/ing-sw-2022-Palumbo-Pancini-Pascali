package it.polimi.ingsw.messages.clienttoserver.actions;

import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.messages.clienttoserver.actions.Action;
import it.polimi.ingsw.model.player.DiningRoom;

public class PickDestination implements UserAction {
    private Action action;
    private Object destination;
    private Island chosenIsland;
    private DiningRoom diningRoom;

    public PickDestination() {
        this.action = Action.PICK_DESTINATION;
    }

    public PickDestination(Island island) {
        this.chosenIsland = island;
        this.diningRoom = null;
    }

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

    public void setDestination(Object destination) {
        this.destination = destination;
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
