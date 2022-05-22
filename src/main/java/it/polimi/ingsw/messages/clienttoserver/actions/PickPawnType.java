package it.polimi.ingsw.messages.clienttoserver.actions;

import it.polimi.ingsw.model.enumerations.PawnType;

public class PickPawnType implements UserAction {
    private Action action;
    private PawnType type;


    public PickPawnType(PawnType type) {
        this.action = Action.PICK_PAWN_TYPE;
        this.type = type;
    }

    public PawnType getType() {
        return type;
    }
}
