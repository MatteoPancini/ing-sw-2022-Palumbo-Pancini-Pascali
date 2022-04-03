package it.polimi.ingsw.exceptions;

public class NotValidNicknameException extends Exception {
    @Override
    public String getMessage() {
        return "Error: nickname can't contain - special character";
    }
}
