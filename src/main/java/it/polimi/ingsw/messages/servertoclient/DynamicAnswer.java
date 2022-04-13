package it.polimi.ingsw.messages.servertoclient;

public class DynamicAnswer implements Answer {
    private final String serverAnswer;

    public DynamicAnswer(String serverAnswer){
        this.serverAnswer = serverAnswer;
    }

    public String getServerAnswer() {
        return serverAnswer;
    }
}
