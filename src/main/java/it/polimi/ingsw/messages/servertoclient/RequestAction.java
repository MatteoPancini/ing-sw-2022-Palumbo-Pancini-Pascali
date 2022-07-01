package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.messages.clienttoserver.actions.Action;

/**
 * Message used to ask a player for a particular action of the turn
 */
public class RequestAction implements Answer {
    private Action actionRequested;

    public RequestAction(Action actionRequested) {
        this.actionRequested = actionRequested;
    }

    @Override
    public Action getMessage() {
        return actionRequested;
    }
}
