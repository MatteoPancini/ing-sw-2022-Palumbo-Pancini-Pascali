package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.messages.clienttoserver.Message;
import it.polimi.ingsw.model.enumerations.Wizards;

public class WizardChoice implements Message{
    private Wizards wizardChosen;

    public WizardChoice(Wizards wizardChosen) {
        this.wizardChosen = wizardChosen;
    }

    public Wizards getWizardChosen() {
        return wizardChosen;
    }
}
