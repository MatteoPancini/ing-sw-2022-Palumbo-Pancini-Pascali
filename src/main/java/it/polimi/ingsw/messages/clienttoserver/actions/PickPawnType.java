package it.polimi.ingsw.messages.clienttoserver.actions;

import it.polimi.ingsw.model.enumerations.PawnType;

/**
 * Class PickPawnType is a user action used to send the pawn type chosen by the user
 */
public class PickPawnType implements UserAction {
    private Action action;
    private PawnType type;


    /**
     * Constructor of the class
     * @param type pawn type chosen
     */
    public PickPawnType(PawnType type) {
        this.action = Action.PICK_PAWN_TYPE;
        this.type = type;
    }

    public PawnType getType() {
        return type;
    }
}
