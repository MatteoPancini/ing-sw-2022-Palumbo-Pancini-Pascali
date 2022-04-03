package it.polimi.ingsw.exceptions;

 //Class OutOfBoundException is thrown when the limits of an object are trying being exceeded.

public class OutOfBoundException extends Exception {
    //Method getMessage returns the message of this OutOfBoundException object.


    @Override
    public String getMessage() {
        return ("Error: ");
    }
}