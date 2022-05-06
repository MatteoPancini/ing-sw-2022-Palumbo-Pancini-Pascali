package it.polimi.ingsw.messages.servertoclient.errors;

import it.polimi.ingsw.messages.servertoclient.Answer;


public class ServerError implements Answer {
    private final ServerErrorTypes serverError;
    private String errorMessage;

    public ServerError(ServerErrorTypes serverError) {
        this.serverError = serverError;
        this.errorMessage = null;
    }

    public ServerErrorTypes getError() {
        return serverError;
    }

    @Override
    public Object getMessage() {
        return errorMessage;
    }

    public ServerErrorTypes getServerError() {
        return serverError;
    }
}
