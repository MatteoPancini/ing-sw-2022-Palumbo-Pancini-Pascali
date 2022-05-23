package it.polimi.ingsw.messages.clienttoserver.actions;

import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.enumerations.Characters;


public class PickCharacter implements UserAction {
    private Action action;
    private Characters chosenCharacter;

    public PickCharacter() {
        this.action = Action.PICK_CHARACTER;
    }

    public PickCharacter(Characters character) {
        this.action = Action.PICK_CHARACTER;
        this.chosenCharacter = character;
    }

    public Action getAction() {
        return action;
    }

    public Characters getChosenCharacter() {
        return chosenCharacter;
    }

    public void setChosenCharacter(Characters character) {
        this.chosenCharacter = character;
    }
}
