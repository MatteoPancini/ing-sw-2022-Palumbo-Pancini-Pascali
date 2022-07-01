package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.messages.clienttoserver.Message;
import it.polimi.ingsw.model.enumerations.Wizards;

/**
 * Class WizardChoice is used to send the wizard chosen by the user from client to server
 */
public class WizardChoice implements Message{
    private Wizards wizardChosen;

    /**
     * Constructor of the class
     * @param wizardChosen chosen wizard
     */
    public WizardChoice(Wizards wizardChosen) {
        this.wizardChosen = wizardChosen;
    }

    public Wizards getWizardChosen() {
        return wizardChosen;
    }
}
