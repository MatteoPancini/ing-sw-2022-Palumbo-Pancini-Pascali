package it.polimi.ingsw.messages.clienttoserver.actions;

import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.enumerations.Characters;


public class PickCharacter implements UserAction {
    private Action action;
    private CharacterCard chosenCharacter;

    public PickCharacter() {
        this.action = Action.PICK_CHARACTER;
    }

    public PickCharacter(CharacterCard character) {
        this.action = action;
        this.chosenCharacter = character;
    }

    public Action getAction() {
        return action;
    }

    public CharacterCard getChosenCharacter() {
        return chosenCharacter;
    }

    public void setChosenCharacter(CharacterCard character) {
        this.chosenCharacter = character;
    }
}
