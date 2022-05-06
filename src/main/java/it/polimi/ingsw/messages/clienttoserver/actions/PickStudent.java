package it.polimi.ingsw.messages.clienttoserver.actions;

import it.polimi.ingsw.model.enumerations.Action;
import it.polimi.ingsw.model.enumerations.PawnType;

public class PickStudent implements UserAction {
    private Action action;
    private PawnType chosenStudent;

    public PickStudent() {
        this.action = Action.PICK_STUDENT;
    }

    public PickStudent(PawnType student) {
        this.action = Action.PICK_STUDENT;
        this.chosenStudent = student;
    }

    public Action getAction() {
        return action;
    }

    public PawnType getChosenStudent() {
        return chosenStudent;
    }
}

