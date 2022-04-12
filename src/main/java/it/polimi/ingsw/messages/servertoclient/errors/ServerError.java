package it.polimi.ingsw.messages.servertoclient.errors;

import it.polimi.ingsw.messages.servertoclient.Answer;

public class ServerError implements Answer {
    private final ServerErrorTypes serverError;

    public ServerError(ServerErrorTypes serverError) {
        this.serverError = serverError;
    }

    public ServerErrorTypes getError() {
        return serverError;
    }

}
