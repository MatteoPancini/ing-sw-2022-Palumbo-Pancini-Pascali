package it.polimi.ingsw.messages.clienttoserver.actions;

import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.enumerations.Action;
import it.polimi.ingsw.model.enumerations.Characters;

public class PickCharacter implements UserAction {
    private Action action;
    private Characters character;

    public PickCharacter() {
        this.action = Action.PICK_CHARACTER;
    }

    public PickCharacter(Characters character) {
        this.action = action;
        this.character = character;
    }

    public Action getAction() {
        return action;
    }
}
