package it.polimi.ingsw.messages.servertoclient;

public class NumOfPlayerRequest implements Answer {

    private String numOfPlayerRequest;

    public NumOfPlayerRequest(String numOfPlayerRequest) {
        this.numOfPlayerRequest = numOfPlayerRequest;
    }

    public String getNumOfPlayerRequest() {
        return numOfPlayerRequest;
    }

    @Override
    public Object getMessage() {
        return numOfPlayerRequest;
    }
}
