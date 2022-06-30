package it.polimi.ingsw.messages.servertoclient;

/**
 * Message used for generic informations about game situation
 */
public class DynamicAnswer implements Answer {
    private final String serverAnswer;
    private boolean activateUserInput;

    public DynamicAnswer(String serverAnswer, boolean activateUserInput) {
        this.serverAnswer = serverAnswer;
        this.activateUserInput = activateUserInput;
    }

    public boolean isActivateUserInput() {
        return activateUserInput;
    }

    @Override
    public String getMessage() {
        return serverAnswer;
    }
}
