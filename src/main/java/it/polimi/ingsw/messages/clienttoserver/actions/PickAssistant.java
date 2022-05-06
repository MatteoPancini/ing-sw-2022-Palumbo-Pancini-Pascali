package it.polimi.ingsw.messages.clienttoserver.actions;

import it.polimi.ingsw.model.enumerations.Assistants;

public class PickAssistant implements UserAction {
    private Action action;
    private Assistants chosenAssistant;

    public PickAssistant() {
        this.action = Action.PICK_ASSISTANT;
    }

    public PickAssistant(Action action, Assistants assistant) {
        this.action = action;
        this.chosenAssistant = assistant;
    }

    public Action getAction() {
        return action;
    }
}
