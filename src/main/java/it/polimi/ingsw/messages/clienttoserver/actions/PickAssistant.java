package it.polimi.ingsw.messages.clienttoserver.actions;

import it.polimi.ingsw.model.cards.AssistantCard;
import it.polimi.ingsw.model.enumerations.Action;
import it.polimi.ingsw.model.enumerations.Assistants;

public class PickAssistant implements UserAction {
    private Action action;
    private AssistantCard chosenAssistant;

    public PickAssistant() {
        this.action = Action.PICK_ASSISTANT;
    }

    public PickAssistant(Action action, AssistantCard assistant) {
        this.action = action;
        this.chosenAssistant = assistant;
    }

    public Action getAction() {
        return action;
    }

    public AssistantCard getChosenAssistant() {
        return chosenAssistant;
    }
}
