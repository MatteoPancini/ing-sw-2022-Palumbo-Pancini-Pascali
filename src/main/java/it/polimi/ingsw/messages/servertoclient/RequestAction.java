package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.messages.clienttoserver.actions.Action;

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
