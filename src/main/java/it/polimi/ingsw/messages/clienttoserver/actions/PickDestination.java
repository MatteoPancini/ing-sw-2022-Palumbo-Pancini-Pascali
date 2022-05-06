package it.polimi.ingsw.messages.clienttoserver.actions;

import it.polimi.ingsw.model.board.Island;
import it.polimi.ingsw.model.enumerations.Action;
import it.polimi.ingsw.model.player.DiningRoom;

public class PickDestination implements UserAction {
    private Action action;
    private int chosenIsland;
    private DiningRoom diningRoom;

    public PickDestination() {
        this.action = Action.PICK_DESTINATION;
    }

    public PickDestination(int island) {
        this.chosenIsland = island;
    }

    public PickDestination(DiningRoom dR) {
        this.diningRoom = dR;
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
