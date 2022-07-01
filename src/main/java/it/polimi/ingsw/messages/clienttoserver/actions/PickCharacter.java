package it.polimi.ingsw.messages.clienttoserver.actions;

import it.polimi.ingsw.model.cards.CharacterCard;
import it.polimi.ingsw.model.enumerations.Characters;

/**
 * Class PickCharacter is a user action used to send the character chosen by the user
 */
public class PickCharacter implements UserAction {
    private Action action;
    private Characters chosenCharacter;

    /**
     * Constructor of the class
     * @param character chosen character
     */
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

}
