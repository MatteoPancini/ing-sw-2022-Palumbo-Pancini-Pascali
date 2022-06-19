package it.polimi.ingsw.exceptions;


public class AlreadyPlayedAssistantException extends Exception {
    @Override
    public String getMessage() {
        return "Error: you chose an assistant card already played by another player!";
    }
}
