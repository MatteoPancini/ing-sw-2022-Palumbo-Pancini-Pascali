package it.polimi.ingsw.messages.clienttoserver.actions;

import it.polimi.ingsw.model.enumerations.Assistants;

/**
 * Class PickAssistant is a user action used to send the assistant chosen by the user
 */
public class PickAssistant implements UserAction {
    private Action action;
    private Assistants chosenAssistant;

    /**
     * Constructor of the class
     * @param action PickAssistant action
     * @param assistant assistant chosen
     */
    public PickAssistant(Action action, Assistants assistant) {
        this.action = action;
        this.chosenAssistant = assistant;
    }

    public Action getAction() {
        return action;
    }

    public Assistants getChosenAssistant() {
        return chosenAssistant;
    }
}
