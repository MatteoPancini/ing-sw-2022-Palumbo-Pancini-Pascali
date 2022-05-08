package it.polimi.ingsw.messages.clienttoserver.actions;

import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.enumerations.Action;
import it.polimi.ingsw.model.player.DiningRoom;

public class PickDestination implements UserAction {
    private Action action;
    private Object destination;
    private int chosenIsland;
    private DiningRoom diningRoom;

    public PickDestination() {
        this.action = Action.PICK_DESTINATION;
    }

    public PickDestination(int island) {
        this.chosenIsland = island;
        this.diningRoom = null;
    }

    public PickDestination(DiningRoom dR) {
        this.chosenIsland = -1;
        this.diningRoom = dR;
    }

    public Object getDestination() {
        if(chosenIsland != -1) {
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

    public int getChosenIsland() {
        return chosenIsland;
    }

    public DiningRoom getDiningRoom() {
        return diningRoom;
    }
}
