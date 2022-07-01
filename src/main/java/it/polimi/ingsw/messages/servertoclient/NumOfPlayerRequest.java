package it.polimi.ingsw.messages.servertoclient;

/**
 * Message used to ask the lobby host the number of player of the game
 */
public class NumOfPlayerRequest implements Answer {

    private String numOfPlayerRequest;

    public NumOfPlayerRequest(String numOfPlayerRequest) {
        this.numOfPlayerRequest = numOfPlayerRequest;
    }

    @Override
    public String getMessage() {
        return numOfPlayerRequest;
    }
}
