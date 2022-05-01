package it.polimi.ingsw.exceptions;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.Assistants;

public class AlreadyPlayedAssistantException extends Exception {
    @Override
    public String getMessage() {
        return "Error: you chose an assistant card already played by another player!";
    }
}
