package it.polimi.ingsw.messages.servertoclient;

public class DynamicAnswer implements Answer {
    private final String serverAnswer;
    private boolean activateUserInput;

    public DynamicAnswer(String serverAnswer, boolean activateUserInput) {
        this.serverAnswer = serverAnswer;
        this.activateUserInput = activateUserInput;
    }

    public String getServerAnswer() {
        return serverAnswer;
    }

    public boolean isActivateUserInput() {
        return activateUserInput;
    }

    @Override
    public String getMessage() {
        return serverAnswer;
    }
}
