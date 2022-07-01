package it.polimi.ingsw.messages.servertoclient;

/**
 * Message used to notify the client that MagicPostman's action is active
 */
public class MagicPostmanAction implements Answer {

    public MagicPostmanAction(){}

    @Override
    public Object getMessage() {
        return null;
    }
}
