package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.messages.clienttoserver.Message;
import it.polimi.ingsw.model.enumerations.Wizards;

public class WizardChoice implements Message {
    //Message by the client to the server containg the wizard chosen by the player.

    private final Wizards wizard;

    public WizardChoice(Wizards wizard) {
        this.wizard = wizard;
    }

    public Wizards getWizard() {
        return wizard;
    }
}
