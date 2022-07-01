package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.messages.servertoclient.Answer;

/**
 * Class FourPModeNotification is an answer used to send the four player mode chosen by the user
 */
public class FourPModeNotification implements Answer {

    /**
     * Constructor of the class
     */
    public FourPModeNotification() {}


    @Override
    public Object getMessage() {
        return null;
    }
}
