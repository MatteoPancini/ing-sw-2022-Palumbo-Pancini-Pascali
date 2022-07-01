package it.polimi.ingsw.exceptions;

/**
 * Class DuplicateNicknameException is used to catch the exception trhown when a nickname is already set for other players
 */
public class DuplicateNicknameException extends Exception {
    @Override
    public String getMessage() {
        return "Error: this nickname has already been chosen!";
    }
}
